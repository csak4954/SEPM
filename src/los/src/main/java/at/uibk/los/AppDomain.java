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

			loginProvider.addUser(new ExternalUser("Admin", "Admin", "admin", "c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec", "admin@uibk.ac.at", "admin"));
			
		}
		
		return instance;
	}
}