package at.uibk.los;

import at.uibk.los.login.ExternalUser;
import at.uibk.los.login.LoginProvider;
import at.uibk.los.model.Model;

public class AppDomain extends Model
{	
	private static ServiceProvider tmp = null;
	private AppDomain()
	{
		super(tmp = new ServiceProvider());
		provider = tmp;
	}
	
	private ServiceProvider provider;
	public ServiceProvider getServiceProvider() {
		return provider;
	}
	
	private static AppDomain instance = null;
	public static synchronized AppDomain get()
	{
		if(instance == null) 
		{	
			instance = new AppDomain();
			
			// add users 
			LoginProvider loginProvider = instance.getServiceProvider().getLoginProvider();
			
			loginProvider.addUser(new ExternalUser("Florian", "Tischler", "csaq5126", "secret", "csaq5126@uibk.ac.at", "student"));
			loginProvider.addUser(new ExternalUser("Mathias", "Hoelzl", "csaq5244", "secret", "csaq5244@uibk.ac.at", "student"));
			//loginProvider.addUser(new ExternalUser("Felix", "Putz", "345678", "234567", "example@test.com", "student"));
			
			loginProvider.addUser(new ExternalUser("Christian", "Sillaber", "12345", "secret", "christian.sillaber@uibk.ac.at", "staff"));
			loginProvider.addUser(new ExternalUser("Philipp", "Kalb", "23456", "secret", "philipp.kalb@uibk.ac.at", "staff"));
		}
		
		return instance;
	}
}