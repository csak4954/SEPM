package los;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import junit.framework.Assert;

import org.junit.Test;

import at.uibk.los.model.IModel;
import at.uibk.los.model.LOSAccessDeniedException;
import at.uibk.los.model.Model;
import at.uibk.los.model.mocks.StaffLoginProviderMock;
import at.uibk.los.model.mocks.StudentLoginProviderMock;

public class ModelTest
{
	@Test
	public void testLogin()
	{
		IModel model = new Model(new StudentLoginProviderMock());
		
		assertNull(model.getUser());
		
		model.loginUser();
		
		assertNotNull(model.getUser());
		
		model.logoutUser();
		
		assertNull(model.getUser());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLogout() 
	{
		
		IModel model = new Model(new StudentLoginProviderMock());
		model.logoutUser();
	}
	
	@Test
	public void testAddLecture() 
	{
		
		IModel model = new Model(new StaffLoginProviderMock());
		
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
}
