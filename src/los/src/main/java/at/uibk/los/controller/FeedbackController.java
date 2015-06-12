package at.uibk.los.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.viewmodel.FeedbackViewModel;
import at.uibk.los.viewmodel.StatusViewModel;

@Controller
@RequestMapping("/lecture/{lectureId}/feedback")
public class FeedbackController{

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Object getFeedback(@PathVariable String lectureId, HttpServletResponse response){

		IModel model = AppDomain.get();

		try 
		{
			List<IFeedback> feedback = model.getFeedback(lectureId);
			return StatusViewModel.onSuccess(response, FeedbackViewModel.get(feedback));
		}
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Object addFeedback(@PathVariable String lectureId,
											@RequestParam("rating") int rating, 
		  									@RequestParam("message") String message, 
  											HttpServletResponse response) {

		IModel model = AppDomain.get();

		if(rating < 0 || rating > 100) {
			return StatusViewModel.onParamInvalid(response, "Rating must be in range [0, 100]");
		}
				
		try 
		{
			model.submitFeedback(lectureId, rating, message);
			return StatusViewModel.onSuccessCreated(response, null);
		}
		catch (LOSAccessDeniedException e) {
			return StatusViewModel.onException(e, response);
		} 
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
}
