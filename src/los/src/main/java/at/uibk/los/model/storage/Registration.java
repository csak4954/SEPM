package at.uibk.los.model.storage;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

class Registration {

	@Id
	private ObjectId id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Lecture lecture;
	
	public Registration(User user, Lecture lecture) {
		this.id = ObjectId.get();
		this.user = user;
		this.lecture = lecture;
	}
	
	public Lecture getLecture() {
		return lecture;
	}

	public User getUser() {
		return user;
	}

	static RegistrationRepository Repo;
}
