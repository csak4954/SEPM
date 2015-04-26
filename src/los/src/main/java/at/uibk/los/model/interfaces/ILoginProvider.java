package at.uibk.los.model.interfaces;

public interface ILoginProvider
{
	boolean login();
	void logout();
	IUser getUser();
}
