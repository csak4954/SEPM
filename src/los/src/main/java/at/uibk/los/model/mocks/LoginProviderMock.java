package at.uibk.los.model.mocks;

import at.uibk.los.model.ILoginProvider;
import at.uibk.los.model.IUser;

public abstract class LoginProviderMock implements ILoginProvider
{
	private IUser user;
	
	void setUser(IUser user){
		this.user = user;
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
