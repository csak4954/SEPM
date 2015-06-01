package at.uibk.los.model.interfaces;

public interface ILoginProvider
{
	IUser getUser();
	boolean isNew();
}
