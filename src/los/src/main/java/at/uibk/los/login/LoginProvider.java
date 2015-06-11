package at.uibk.los.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IUser;

@Component
@Scope("session")
public class LoginProvider implements ILoginProvider {
	
	@Autowired 
	private ExternalUserRepository users;
	
	private ExternalUser user = new ExternalUser(); 
	
	private boolean isNew = false;
	
	public void addUser(ExternalUser user) {
		users.save(user);
	}
	
	@Override
	public IUser getUser() {
		return user;
	}
	
	public boolean login(String username, String password)
	{
		user = users.findByMatIdAndPassword(username, password);
		return (isNew = user != null);
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
	
	public static LoginProvider LoadFromContext(ApplicationContext ctx) {
		return ctx.getBean(LoginProvider.class);
	}
}
