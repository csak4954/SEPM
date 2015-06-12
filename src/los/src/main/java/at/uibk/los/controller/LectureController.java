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
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.viewmodel.LectureViewModel;
import at.uibk.los.viewmodel.StatusViewModel;

@Controller
@RequestMapping("/lecture")
public class LectureController
{
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody Object getAllLectures(HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			List<ILectureView> lectures = model.getAvailableLectures();	
			return StatusViewModel.onSuccess(response, LectureViewModel.convert(lectures));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		}
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public @ResponseBody Object getMyLectures(HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			List<ILectureView> lectures = model.getAssociatedLectures();	
			return StatusViewModel.onSuccess(response, LectureViewModel.convert(lectures));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Object addLecture(@RequestParam("title") String title,
										   @RequestParam("description") String description, 
										   HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{		
			ILectureView view = model.addLecture(title, description);
			model.addAdmin(view.getId());
			return StatusViewModel.onSuccessCreated(response, new LectureViewModel(view));
		}
		catch (LOSAccessDeniedException e) { 
			return StatusViewModel.onException(e, response);
		}
		catch (EntityNotFoundException e) {
			return StatusViewModel.onException(e, response);
		}
	}
	
	@RequestMapping(value = "/{lectureId}", method = RequestMethod.DELETE)
	public @ResponseBody Object removeLecture(@PathVariable String lectureId, 
									   		  HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			model.removeLecture(lectureId);
			return StatusViewModel.onSuccessNoContent(response);
		}
		catch (LOSAccessDeniedException e) 
		{ 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new StatusViewModel(e);
		}
		catch (EntityNotFoundException e)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new StatusViewModel(e);
		}
	}
}