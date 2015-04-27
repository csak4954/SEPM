package at.uibk.los;


import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.mview.LectureModelView;

/**
 * Responds with a Model as JSON.
 */
@Controller
@RequestMapping("/lecture")
public class LectureController
{
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ArrayList<ILecture> returnLectureAll() {
		ArrayList<ILecture> lectures = new ArrayList<ILecture>();
		for(int i = 0; i <= 19; i++){
			ILecture lecture = new LectureModelView();
			lecture.setId(i);
			lecture.setTitle("LectureTitle - version "+i);
			lecture.setDescription("This is the description of this lecture - version"+i);
			lecture.setVerificationKey("LecturesVeryKey"+i);
			lectures.add(lecture);
		}
		return lectures;
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public @ResponseBody ILecture returnLectureById(@PathVariable int id) {
		ILecture lecture = new LectureModelView();
		lecture.setId(id);
		lecture.setTitle("LectureTitle - version "+id);
		lecture.setDescription("This is the description of this lecture - version "+id);
		lecture.setVerificationKey("LecturesVeryKey"+id);
		return lecture;
	}
}
