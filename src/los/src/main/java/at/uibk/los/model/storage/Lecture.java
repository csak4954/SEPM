package at.uibk.los.model.storage;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuiz;

public class Lecture implements ILecture {

	@Id
	private int id;
	
	private String title;
	private String description;
	private String verificationKey;
	
	@PersistenceConstructor
	public Lecture() {
		
	}
	
	public Lecture(ILecture lecture) {
		this.id = lecture.getId();
		this.title = lecture.getTitle();
		this.description = lecture.getDescription();
		this.verificationKey = lecture.getVerificationKey();
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getVerificationKey() {
		return verificationKey;
	}

	@Override
	public void setVerificationKey(String key) {
			this.verificationKey = key;
	}

	@Override
	public void addAttendee(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttendee(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Integer> getAttendees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAdmin(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAdmin(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Integer> getAdmins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addQuiz(IQuiz quiz) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeQuiz(int quizId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IQuiz getQuiz(int quizId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<IQuiz> getQuiz() {
		// TODO Auto-generated method stub
		return null;
	}
}
