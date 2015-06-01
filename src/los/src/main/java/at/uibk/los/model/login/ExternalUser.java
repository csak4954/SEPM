package at.uibk.los.model.login;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import at.uibk.los.model.authorization.StaffGroupPolicy;
import at.uibk.los.model.authorization.StudentGroupPolicy;
import at.uibk.los.model.interfaces.IUser;

public class ExternalUser implements IUser {

	@Id
	private ObjectId id;
	
	private String name;
	private String surname;
	private String matId;
	private String email;
	private String affilation;
	
	public final String studentAffiliation = "student";
	public final String staffAffiliation = "staff";
	
	public ExternalUser(String name, String surname, String matId, String email, String affilation) {
		this.name = name;
		this.surname = surname;
		this.matId = matId;
		this.email = email;
		this.affilation = affilation;
	}
	
	public ExternalUser() {
		this.name = "";
		this.surname = "";
		this.matId = "";
		this.email = "";
		this.affilation = "";
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
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getSurname() {
		// TODO Auto-generated method stub
		return surname;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return matId;
	}

	@Override
	public String getAffilation() {
		return affilation;
	}

}
