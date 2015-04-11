package at.uibk.los.model;

public interface ILoginProvider
{
	boolean login();
	void logout();
	IUser getUser();
}
