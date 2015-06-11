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
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.viewmodel.ErrorViewModel;
import at.uibk.los.viewmodel.LectureViewModel;

/**
 * Responds with a ViewModel as JSON.
 */
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
			List<LectureViewModel> lecturesVM = new LinkedList<LectureViewModel>();
		
			for(ILectureView lecture : lectures) {
				lecturesVM.add(new LectureViewModel(lecture));
			}
			
			return lecturesVM;
		}
		catch (LOSAccessDeniedException e) 
		{ 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
	}

	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public @ResponseBody Object getMyLectures(HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			List<ILectureView> lectures = model.getAssociatedLectures();	
			List<LectureViewModel> lecturesVM = new LinkedList<LectureViewModel>();
		
			for(ILectureView lecture : lectures) {
				lecturesVM.add(new LectureViewModel(lecture));
			}
			
			return lecturesVM;
		}
		catch (LOSAccessDeniedException e) 
		{ 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
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
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		catch (LOSAccessDeniedException e) 
		{ 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
		catch (EntityNotFoundException e)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Object removeLecture(@PathVariable String id, 
									   		  HttpServletResponse response) 
	{
		IModel model = AppDomain.get();
		
		try
		{
			model.removeLecture(id);
		}
		catch (LOSAccessDeniedException e) 
		{ 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return new ErrorViewModel(e);
		}
		catch (EntityNotFoundException e)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ErrorViewModel(e);
		}
		
		return null;
	}
}