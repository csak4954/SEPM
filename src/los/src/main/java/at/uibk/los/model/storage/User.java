package at.uibk.los.model.storage;

import at.uibk.los.model.interfaces.IUser;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

class User implements IUser {

	@Id
	ObjectId id;
	
	@Indexed(unique = true)
	private String matId;
	
	private String name;
	private String surname;
	private String email;
	private String affilation;
	private int groupPolicy;
		
	@PersistenceConstructor
	public User() {
		this.id = ObjectId.get();
	}
	
	
	public User(IUser user) {
		this.id = ObjectId.get();
		this.matId = user.getId();
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
		return matId;
	}

	@Override
	public String getAffilation() {
		return affilation;
	}

	static UserRepository Repo;
}
