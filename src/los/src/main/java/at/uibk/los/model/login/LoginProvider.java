package at.uibk.los.model.login;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IUser;

@Service
@Scope("session")
public class LoginProvider implements ILoginProvider {
	
	public static ILoginProvider LoadFromContext(ApplicationContext ctx) {
		return ctx.getBean(LoginProvider.class);
	}
	
	private ExternalUser user = new ExternalUser(); 
	private boolean isNew = false;
	
	@Override
	public void login() {

	}

	@Override
	public void logout() {

	}

	@Override
	public IUser getUser() {
		return user;
	}
	
	@Override
	public boolean isNew()
	{
		if(isNew)
		{
			isNew = false;
			return true;
		}
		
		return false;
	}
}
