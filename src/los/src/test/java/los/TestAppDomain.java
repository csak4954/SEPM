package los;

import at.uibk.los.ApplicationContextProvider;
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

class ServiceProvider implements IServiceProvider
{
	public IDataManipulation manipulation = new DataManipulation(DataStorage.loadFromContext(ApplicationContextProvider.getContext()));
	public IDataEvaluation evaluation = new DataEvaluation(DataStorage.loadFromContext(ApplicationContextProvider.getContext()));
	public IPolicyManager policyManager = new PolicyManager();
	
	@Override
	public IDataEvaluation getEvaluation() {
		return evaluation;
	}

	@Override
	public IDataManipulation getManipulation() {
		return manipulation;
	}

	@Override
	public IDataStorage getStorage() {
		return DataStorage.loadFromContext(ApplicationContextProvider.getContext());
	}

	@Override
	public ILoginProvider getLoginProvider() {
		return LoginProvider.LoadFromContext(ApplicationContextProvider.getContext());
	}

	@Override
	public IPolicyManager getPolicyManager() {
		return policyManager;
	}
}

public class TestAppDomain extends Model
{	
	public static IServiceProvider provider;
	
	private TestAppDomain()
	{
		super(provider);
	}
	
	private static TestAppDomain instance = null;
	public static TestAppDomain get()
	{
		if(instance == null) {	
			instance = new TestAppDomain();
		}
		
		return instance;
	}
}