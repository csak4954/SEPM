package at.uibk.los.model.interfaces;

public interface ILoginProvider
{
	void login();
	void logout();
	IUser getUser();
	boolean isNew();
}
