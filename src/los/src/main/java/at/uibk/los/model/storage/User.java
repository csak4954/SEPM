package at.uibk.los.model.storage;

import org.springframework.data.annotation.Id;

import at.uibk.los.model.interfaces.IUser;

class User implements IUser {

	@Id
	private String id;
	
	private String name;
	private String surname;
	private String email;
	private String affilation;
	private int groupPolicy;
		
	public User() {
		
	}
		
	public void copy(IUser user) {
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
		this.affilation = user.getAffilation();
		this.groupPolicy = user.getGroupPolicy();
	}
		
	@Override
	public int getGroupPolicy() {
		return groupPolicy;
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

	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(this.getClass() != o.getClass()) return false;
		
		User other = (User)o;
		
		return this.id.equals(other.id);
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	static UserRepository Repo;
}
