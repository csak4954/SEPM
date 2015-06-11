package at.uibk.los.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.EntityNotFoundException;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.viewmodel.ErrorViewModel;
import at.uibk.los.viewmodel.FeedbackViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/lecture/{lectureId}/feedback")
public class FeedbackController{

	@RequestMapping(value="/", method = RequestMethod.GET)
	public @ResponseBody Object getFeedback(@PathVariable String lectureId, HttpServletResponse response){

		IModel model = AppDomain.get();

		try 
		{
			List<IFeedback> feedback = model.getFeedback(lectureId);
			List<FeedbackViewModel> feedbackVM = new LinkedList<FeedbackViewModel>();
			
			for(IFeedback fb : feedback) {
				feedbackVM.add(new FeedbackViewModel(fb));
			}
			
			return feedbackVM;
			
		} catch (LOSAccessDeniedException e) {
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
			
		} catch (EntityNotFoundException e) {
			
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	public @ResponseBody Object addFeedback(@PathVariable String lectureId,
											@RequestParam("rating") int rating, 
		  									@RequestParam("message") String message, 
  											HttpServletResponse response){

		IModel model = AppDomain.get();

		try 
		{
			model.submitFeedback(lectureId, rating, message);
			response.setStatus(HttpServletResponse.SC_CREATED);

		} catch (LOSAccessDeniedException e) {
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
			
		} catch (EntityNotFoundException e) {
			
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
		
		return null;
	}
	
}
