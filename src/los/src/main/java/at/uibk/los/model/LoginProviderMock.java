package at.uibk.los.model;

public class LoginProviderMock implements ILoginProvider
{
	private UserMock user;
	
	@Override
	public boolean login()
	{
		user = new UserMock("florian", "tischler", "florian.tischler@outlook.com", 123456789, "student");
		return true;
	}

	@Override
	public void logout()
	{
		user = null;
	}

	@Override
	public IUser getUser()
	{
		// TODO Auto-generated method stub
		return user;
	}
	
}
