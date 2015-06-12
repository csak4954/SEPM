package at.uibk.los.login;

import org.springframework.data.annotation.Id;

import at.uibk.los.model.authorization.StaffGroupPolicy;
import at.uibk.los.model.authorization.StudentGroupPolicy;
import at.uibk.los.model.interfaces.IUser;

public class ExternalUser implements IUser {

	@Id
	private String id;
	
	private String name;
	private String surname;
	private String email;
	private String affilation;
	private String password;
	
	public final String studentAffiliation = "student";
	public final String staffAffiliation = "staff";
	
	public ExternalUser() {

	}
	
	public ExternalUser(String name, String surname, String id, String password, String email, String affilation) {
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.email = email;
		this.affilation = affilation;
		this.password = password;
	}

	@Override
	public int getGroupPolicy() 
	{
		if(getAffilation().equals(studentAffiliation)) {
			return StudentGroupPolicy.id;
		}
		else if(getAffilation().equals(staffAffiliation)) {
			return StaffGroupPolicy.id;
		}
		else {
			return -1;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAffilation() {
		return affilation;
	}

	public void copy(ExternalUser user) {
		this.name = user.name;
		this.surname = user.surname;
		this.id = user.id;
		this.email = user.email;
		this.affilation = user.affilation;
		this.password = user.password;
	}
}
