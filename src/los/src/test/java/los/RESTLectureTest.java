package los;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import at.uibk.los.viewmodel.LectureViewModel;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

public class RESTLectureTest{
	@Ignore
	@Test
	public void testLectureById(){
		RestTemplate restTemplate = new RestTemplate();
		String request_url = "http://localhost:8080/los/lecture/246"; // id does not count so far
		LectureViewModel lvm = restTemplate.getForObject(request_url, LectureViewModel.class);  // rebuilds model from json (got from url)		
		assertTrue(lvm.getId() == 246);
		assertTrue(lvm.getTitle().equals("LectureTitle - version 246"));
		assertTrue(lvm.getDescription().equals("This is the description of this lecture - version 246"));
	}

	@Ignore
	@Test
	public void testLectureAll(){
		RestTemplate restTemplate = new RestTemplate();
		String request_url = "http://localhost:8080/los/lecture"; 
		@SuppressWarnings("unchecked")
		ArrayList<LectureViewModel> lectures = restTemplate.getForObject(request_url, ArrayList.class);
		assertTrue(lectures.size() == 20);
	}
}
