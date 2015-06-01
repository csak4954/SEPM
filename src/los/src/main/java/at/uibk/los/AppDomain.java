package at.uibk.los;

import at.uibk.los.model.DataEvaluation;
import at.uibk.los.model.DataManipulation;
import at.uibk.los.model.Model;
import at.uibk.los.model.authorization.PolicyManager;
import at.uibk.los.model.interfaces.IDataEvaluation;
import at.uibk.los.model.interfaces.IDataManipulation;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IPolicyManager;
import at.uibk.los.model.interfaces.IServiceProvider;
import at.uibk.los.model.login.LoginProvider;
import at.uibk.los.model.storage.DataStorage;

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
	public static AppDomain get()
	{
		if(instance == null) {	
			instance = new AppDomain();
		}
		
		return instance;
	}
}