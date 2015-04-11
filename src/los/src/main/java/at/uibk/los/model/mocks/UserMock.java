package at.uibk.los.model.mocks;

import at.uibk.los.model.IUser;

public class UserMock implements IUser
{
	private String name;
	private String surname;
	private String email;
	private int id;
	private String affilation;
	
	public UserMock(String name, String surname, String email, int id, String affilation) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.id = id;
		this.affilation = affilation;
	}
	
	@Override
	public int getGroupPolicy()
	{
		return 0;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getSurname()
	{
		return surname;
	}
	
	@Override
	public String getEmail()
	{
		return email;
	}
	
	@Override
	public int getId()
	{
		return id;
	}
	
	
	@Override
	public String getAffilation()
	{
		return affilation;
	}
}
