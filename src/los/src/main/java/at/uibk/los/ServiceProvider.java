package at.uibk.los;

import at.uibk.los.login.LoginProvider;
import at.uibk.los.model.DataManipulation;
import at.uibk.los.model.authorization.PolicyManager;
import at.uibk.los.model.evaluation.DataEvaluation;
import at.uibk.los.model.interfaces.IServiceProvider;
import at.uibk.los.model.storage.DataStorage;

public class ServiceProvider implements IServiceProvider
{
	public DataManipulation manipulation = new DataManipulation(DataStorage.loadFromContext(ApplicationContextProvider.getContext()));
	public DataEvaluation evaluation = new DataEvaluation(DataStorage.loadFromContext(ApplicationContextProvider.getContext()));
	public PolicyManager policyManager = new PolicyManager();
	
	@Override
	public DataEvaluation getEvaluation() {
		return evaluation;
	}

	@Override
	public DataManipulation getManipulation() {
		return manipulation;
	}

	@Override
	public DataStorage getStorage() {
		return DataStorage.loadFromContext(ApplicationContextProvider.getContext());
	}

	@Override
	public LoginProvider getLoginProvider() {
		return LoginProvider.LoadFromContext(ApplicationContextProvider.getContext());
	}

	@Override
	public PolicyManager getPolicyManager() {
		return policyManager;
	}
}