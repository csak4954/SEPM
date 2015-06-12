package at.uibk.los.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IQuizResult;
import at.uibk.los.viewmodel.QuizResultViewModel;
import at.uibk.los.viewmodel.StatusViewModel;
import at.uibk.los.viewmodel.UserViewModel;

@Controller
public class ResultsController {

	@RequestMapping(value = "/results/my", method = RequestMethod.GET)
	public @ResponseBody Object getAllResults(HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			List<IQuizResult> results = model.getQuizResults();
			return StatusViewModel.onSuccess(response, QuizResultViewModel.get(results));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		}
	}
		
	@RequestMapping(value = "/lecture/{lectureId}/results/my", method = RequestMethod.GET)
	public @ResponseBody Object getLectureResults(@PathVariable String lectureId,
												  HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			List<IQuizResult> results = model.getQuizResults();
			return StatusViewModel.onSuccess(response, QuizResultViewModel.getForLecture(results, lectureId));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(value = "/lecture/{lectureId}/user/{userId}/results", method = RequestMethod.GET)
	public @ResponseBody Object getQuizResults(@PathVariable String lectureId,
											   @PathVariable String userId,
											   HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			List<IQuizResult> results = model.getQuizResults(userId);
			return StatusViewModel.onSuccess(response, QuizResultViewModel.getForLecture(results, lectureId));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(value = "/lecture/{lectureId}/user", method = RequestMethod.GET)
	public @ResponseBody Object getUsers(@PathVariable String lectureId,
									     HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			ILectureView lecture = model.getLecture(lectureId);
			return StatusViewModel.onSuccess(response, UserViewModel.get(lecture.getRegisteredUsers()));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		} catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
}
