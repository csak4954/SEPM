package los;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import los.config.TestAppConfig;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import at.uibk.los.viewmodel.LectureViewModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class)
@WebAppConfiguration
public class RESTLectureTest
{
	@Ignore
	@Test
	public void testLectureById(){
		RestTemplate restTemplate = new RestTemplate();
		String request_url = "http://localhost:8080/los/lecture/246"; // id does not count so far
		LectureViewModel lvm = restTemplate.getForObject(request_url, LectureViewModel.class);  // rebuilds model from json (got from url)		
		Assert.assertEquals("246", lvm.getId());
		Assert.assertEquals("LectureTitle - version 246", lvm.getTitle());
		Assert.assertEquals("This is the description of this lecture - version 246", lvm.getDescription());
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
