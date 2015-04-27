package los;

import java.util.ArrayList;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.mview.LectureModelView;
import static org.junit.Assert.assertTrue;

public class RESTLectureTest{
	
	@Test
	public void testLectureById(){
		RestTemplate restTemplate = new RestTemplate();
		String request_url = "http://localhost:8080/los/lecture/246"; // id does not count so far
		ILecture lecture = restTemplate.getForObject(request_url, LectureModelView.class);  // rebuilds model from json (got from url)		
		assertTrue(lecture.getId() == 246);
		assertTrue(lecture.getTitle().equals("LectureTitle - version 246"));
		assertTrue(lecture.getDescription().equals("This is the description of this lecture - version 246"));
		assertTrue(lecture.getVerificationKey().equals("LecturesVeryKey246"));
	}
	
	@Test
	public void testLectureAll(){
		RestTemplate restTemplate = new RestTemplate();
		String request_url = "http://localhost:8080/los/lecture"; 
		@SuppressWarnings("unchecked")
		ArrayList<LectureModelView> lectures = restTemplate.getForObject(request_url, ArrayList.class);
		assertTrue(lectures.size() == 20);
	}
		
}
