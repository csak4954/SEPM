package at.uibk.los.login;

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
	
	// only stored in db 
	// pw check with query
	@SuppressWarnings("unused")
	private String password;
	
	public final String studentAffiliation = "student";
	public final String staffAffiliation = "staff";
	
	public ExternalUser(String name, String surname, String matId, String password, String email, String affilation) {
		this.name = name;
		this.surname = surname;
		this.matId = matId;
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
		return matId;
	}

	@Override
	public String getAffilation() {
		return affilation;
	}

}
