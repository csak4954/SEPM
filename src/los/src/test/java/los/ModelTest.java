package los;

import junit.framework.Assert;

import org.junit.Test;

import at.uibk.los.model.ILoginProvider;
import at.uibk.los.model.IModel;
import at.uibk.los.model.IUser;
import at.uibk.los.model.LOSAccessDeniedException;
import at.uibk.los.model.Model;
import static org.mockito.Mockito.*;

public class ModelTest
{
	@Test
	public void testAddLecture() 
	{
		IUser user = mock(IUser.class);
		when(user.getName()).thenReturn("florian");
		when(user.getSurname()).thenReturn("tischler");
		when(user.getEmail()).thenReturn("florian.tischler@outlook.com");
		when(user.getId()).thenReturn(123456789);
		when(user.getAffilation()).thenReturn("student");
		when(user.getGroupPolicy()).thenReturn(0);
		
		ILoginProvider provider = mock(ILoginProvider.class);
		when(provider.login()).thenReturn(true);
		when(provider.getUser()).thenReturn(user);
		
		IModel model = new Model(provider);

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
