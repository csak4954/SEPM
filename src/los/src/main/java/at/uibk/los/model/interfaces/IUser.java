package at.uibk.los.model.interfaces;

public interface IUser extends IObject, IIdentifiable
{
	String getName();
	String getSurname();
	String getEmail();
	String getAffilation();	
}
