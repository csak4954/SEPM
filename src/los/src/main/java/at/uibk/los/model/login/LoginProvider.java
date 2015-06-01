package at.uibk.los.model.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IUser;

@Service
@Scope("session")
public class LoginProvider implements ILoginProvider {
	
	@Autowired 
	ExternalUserRepository users;
	
	private static boolean createUsers = true;
	public static LoginProvider LoadFromContext(ApplicationContext ctx) {
		
		LoginProvider instance = ctx.getBean(LoginProvider.class);
		
		if(createUsers) {
			
			instance.users.save(new ExternalUser("Florian", "Tischler", "123456", "secret", "example@test.com", "student"));
			instance.users.save(new ExternalUser("Mathias", "Hölzl", "234567", "secret", "example@test.com", "student"));
			instance.users.save(new ExternalUser("Felix", "Putz", "345678", "234567", "example@test.com", "student"));
			
			instance.users.save(new ExternalUser("Mr", "Prof", "456789", "secret", "example@test.com", "staff"));
			instance.users.save(new ExternalUser("Ms", "Prof", "567891", "secret", "example@test.com", "staff"));
		
			createUsers = false;
		}
		
		return instance;
	}
	
	private ExternalUser user = new ExternalUser(); 
	private boolean isNew = false;
	
	@Override
	public IUser getUser() {
		return user;
	}
	
	
	public boolean login(String username, String password)
	{
		user = users.findByMatIdAndPassword(username, password);
		return user != null;
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
