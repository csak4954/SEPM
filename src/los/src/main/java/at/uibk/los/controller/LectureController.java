package at.uibk.los.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.uibk.los.AppDomain;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.viewmodel.LectureViewModel;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import static org.mockito.Mockito.*;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/lecture")
public class LectureController
{
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ArrayList<LectureViewModel> returnLectureAll() 
	{
		try
		{
			List<ILectureView> lectures = AppDomain.get().getAvailableLectures();	
			ArrayList<LectureViewModel> lecturesVM = new ArrayList<LectureViewModel>();
		
			for(ILectureView lecture : lectures) {
				lecturesVM.add(new LectureViewModel(lecture));
			}
			
			return lecturesVM;
		}
		catch (LOSAccessDeniedException e) { }
		
		return null;
	}

	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public @ResponseBody LectureViewModel returnLectureById(@PathVariable int id){
		ILecture lecture = mock(ILecture.class);
		when(lecture.getId()).thenReturn(id + "");
        when(lecture.getTitle()).thenReturn("LectureTitle - version "+id);
        when(lecture.getDescription()).thenReturn("This is the description of this lecture - version "+id);
		return new LectureViewModel(lecture);
	}
}
