package los;

import junit.framework.Assert;
import los.config.UnitTestAppConfig;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.uibk.los.model.Model;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.StaffGroupPolicy;
import at.uibk.los.model.authorization.StudentGroupPolicy;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IUser;
import at.uibk.los.model.storage.LectureRepository;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestAppConfig.class)
@org.springframework.test.context.web.WebAppConfiguration
public class ModelTest
{	
	
    @Autowired
    private LectureRepository repo;
    
	private ILoginProvider getStudentLoginProvider() {
		
		IUser user = mock(IUser.class);
		when(user.getName()).thenReturn("florian");
		when(user.getSurname()).thenReturn("tischler");
		when(user.getEmail()).thenReturn("florian.tischler@outlook.com");
		when(user.getId()).thenReturn(123456789);
		when(user.getAffilation()).thenReturn("student");
		when(user.getGroupPolicy()).thenReturn(StudentGroupPolicy.id);
		
		ILoginProvider provider = mock(ILoginProvider.class);
		when(provider.login()).thenReturn(true);
		when(provider.getUser()).thenReturn(user);
		
		return provider;
	}
	
	private ILoginProvider getStaffLoginProvider() {
		
		IUser user = mock(IUser.class);
		when(user.getName()).thenReturn("florian");
		when(user.getSurname()).thenReturn("tischler");
		when(user.getEmail()).thenReturn("florian.tischler@outlook.com");
		when(user.getId()).thenReturn(123456789);
		when(user.getAffilation()).thenReturn("staff");
		when(user.getGroupPolicy()).thenReturn(StaffGroupPolicy.id);
		
		ILoginProvider provider = mock(ILoginProvider.class);
		when(provider.login()).thenReturn(true);
		when(provider.getUser()).thenReturn(user);
		
		return provider;
	}
	
	@Test(expected = LOSAccessDeniedException.class)
	public void testAddLectureStudent() throws LOSAccessDeniedException 
	{
		IModel model = new Model(getStudentLoginProvider());

		model.loginUser();
		
		final int lectureId = 1;
		final String title = "title";
		final String description = "description";
		
		model.addLecture(lectureId, title, description);
	}
	
	@Test
	public void testAddLectureStaff() 
	{
		IModel model = new Model(getStaffLoginProvider());

		model.loginUser();
		
		final int lectureId = 1;
		final String title = "title";
		final String description = "description";
		
		try
		{
			model.addLecture(lectureId, title, description);
		}
		catch (LOSAccessDeniedException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testAddLecture()
	{
		IModel model = new Model(getStaffLoginProvider());

		model.loginUser();
		
		final int lectureId = 2;
		final String title = "title 2";
		final String description = "description 2";
		
		try
		{
			model.addLecture(lectureId, title, description);
			Iterable<ILecture> lectures = model.getAssociatedLectures();

			for(ILecture lecture : lectures) {
				System.out.println(lecture.getTitle());
			}
		}
		catch (LOSAccessDeniedException e) {
			System.out.println("access denied: " + e.getPermission().getDescription());
			Assert.fail();
		}		
	}
}
