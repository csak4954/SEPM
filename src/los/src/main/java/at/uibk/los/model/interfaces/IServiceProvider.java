package at.uibk.los.model.interfaces;

public interface IServiceProvider 
{
	IDataEvaluation getEvaluation();
	IDataManipulation getManipulation();
	IDataStorage getStorage();
	ILoginProvider getLoginProvider();
	IPolicyManager getPolicyManager();
}
