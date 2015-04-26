package at.uibk.los.model.interfaces;

public interface IUser extends IObject
{
	String getName();
	String getSurname();
	String getEmail();
	int getId();
	String getAffilation();	
}
