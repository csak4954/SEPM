package at.uibk.los.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.viewmodel.LectureViewModel;
import static org.mockito.Mockito.*;

/**
 * Responds with a ViewModel as JSON.
 */
@Controller
@RequestMapping("/lecture")
public class LectureController
{
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ArrayList<LectureViewModel> returnLectureAll() {
		ArrayList<LectureViewModel> lectures = new ArrayList<LectureViewModel>();
		for(int i = 0; i <= 19; i++){
			ILecture lecture = mock(ILecture.class);
			when(lecture.getId()).thenReturn(i + "");
	        when(lecture.getTitle()).thenReturn("LectureTitle - version "+i);
			LectureViewModel lvm = new LectureViewModel(lecture);
			lectures.add(lvm);
		}
		return lectures;
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
