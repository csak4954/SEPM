package at.uibk.los.model.storage;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import at.uibk.los.model.interfaces.IFeedback;

class Feedback implements IFeedback {

	@Id
	private ObjectId id;
	
	@DBRef
	private Lecture lecture;
	
	private int rating;
	private String message;
	
	public Feedback(Lecture lecture, int rating, String message) {
		this.rating = rating;
		this.message = message;
		this.id = ObjectId.get();
		this.lecture = lecture;
	}

	@Override
	public int getRating() {
		return rating;
	}

	@Override
	public String getMessage() {
		return message;
	}

	
	public void setRating(int rating) {
		this.rating = rating;
		Repo.save(this);
	}


	public void setMessage(String message) {
		this.message = message;
		Repo.save(this);
	}


	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(this.getClass() != o.getClass()) return false;
		
		Feedback other = (Feedback)o;
		
		return this.id.equals(other.id);
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	static FeedbackRepository Repo;

	@Override
	public String getId() {
		return id.toString();
	}
}
