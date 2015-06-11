package at.uibk.los.model.storage;
import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import at.uibk.los.model.interfaces.IAttendance;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IUser;

class Lecture implements ILecture {

	@Id
	ObjectId id;
	
	private String title;
	private String description;
	private String verificationKey;
		
	@PersistenceConstructor
	public Lecture() {
		this.id = ObjectId.get();
	}
	
	public Lecture(ILecture lecture) {
		this.id = new ObjectId(lecture.getId());
		this.title = lecture.getTitle();
		this.description = lecture.getDescription();
		this.verificationKey = lecture.getVerificationKey();
	}
	
	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		save();
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
		save();
	}

	@Override
	public String getVerificationKey() {
		return verificationKey;
	}

	@Override
	public void setVerificationKey(String key) {
		this.verificationKey = key;
		save();
	}

	@Override
	public void registerUser(String userId)
	{
		User user = User.Repo.findByMatId(userId);
		
		Registration registration = Registration.Repo.findByUserAndLecture(user, this);
		
		if(registration == null) {
			Registration.Repo.save(new Registration(user, this));	
		}
	}
	
	public boolean unregisterUser(String userId) {
		User user = User.Repo.findByMatId(userId);
		
		Registration registration = Registration.Repo.findByUserAndLecture(user, this);
		
		if(registration != null) {
			Registration.Repo.delete(new Registration(user, this));	
		}
		
		return registration != null;
	}
	
	@Override
	public void addAttendance(String userId) {
		User user = User.Repo.findByMatId(userId);		
		Attendance.Repo.save(new Attendance(user, this));	
	}

	@Override
	public boolean removeAttendance(String userId) {
		User user = User.Repo.findByMatId(userId);
		Attendance attendee = Attendance.Repo.findByUserAndLecture(user, this);
		Attendance.Repo.delete(attendee);
		
		if(attendee != null) { 
			Attendance.Repo.delete(attendee);
		}
		
		return attendee != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IAttendance> getAttendance() {
		return (List<IAttendance>)(List<?>)Attendance.Repo.findByLecture(this);
	}

	@Override
	public void addAdmin(String userId) {
		User user = User.Repo.findByMatId(userId);
		Administration.Repo.save(new Administration(user, this));
	}

	@Override
	public boolean removeAdmin(String userId) {
		User user = User.Repo.findByMatId(userId);
		Administration admin = Administration.Repo.findByUserAndLecture(user, this);
		
		if(admin != null) { 
			Administration.Repo.delete(admin);
		}
		
		return admin != null;
	}

	@Override
	public List<IUser> getAdmins() {
		
		List<IUser> users = new LinkedList<IUser>();
		List<Administration> admins = Administration.Repo.findByLecture(this);
		
		for(Administration admin : admins) {
			users.add(admin.getUser());
		}
		
		return users;
	}

	@Override
	public IQuiz addQuiz(String title) 
	{
		Quiz quiz = new Quiz(this);
		quiz.setTitle(title);
		
		Quiz.Repo.save(quiz);	
		
		return quiz;
	}

	@Override
	public boolean removeQuiz(String quizId) {
		
		Quiz quiz = Quiz.Repo.findOne(quizId);
		
		if(quiz != null) 
		{
			List<IQuestion> questions = quiz.getQuestions();
			if(questions != null) 
			{
				for(IQuestion question : questions) {
					quiz.RemoveQuestion(question.getId());
				}
			}
			
			List<Approach> approaches = Approach.Repo.findByQuiz(quiz);		
			if(approaches != null) {
				Approach.Repo.delete(approaches);
			}
			
			Quiz.Repo.delete(quiz);
		}
		
		return quiz != null;
	}

	@Override
	public IQuiz getQuiz(String quizId) {
		return Quiz.Repo.findOne(quizId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IQuiz> getQuiz() {
		return (List<IQuiz>)(List<?>)Quiz.Repo.findByLecture(this);
	}
	
	@Override
	public void submitFeedback(int rating, String text) {
		Feedback.Repo.save(new Feedback(this, rating, text));
	}
	
	private void save() {
		Repo.save(this);
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(this.getClass() != o.getClass()) return false;
		
		Lecture other = (Lecture)o;
		
		return this.id.equals(other.id);
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	static LectureRepository Repo;

	@SuppressWarnings("unchecked")
	@Override
	public List<IQuizView> getQuizView() {
		return (List<IQuizView>)(List<?>)getQuiz();
	}

	@Override
	public IQuizView getQuizView(String quizId) {
		return getQuiz(quizId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IFeedback> getFeedback() {
		return (List<IFeedback>)(List<?>)Feedback.Repo.findByLecture(this);
	}
	
	@Override
	public List<IUser> getRegisteredUsers() {
			
		List<Registration> registrations = Registration.Repo.findByLecture(this);
		
		List<IUser> users = new LinkedList<IUser>();
		
		for(Registration registration : registrations) {
			users.add(registration.getUser());
		}
		
		return users;
	}
}
