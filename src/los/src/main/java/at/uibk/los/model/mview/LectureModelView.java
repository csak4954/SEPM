package at.uibk.los.model.mview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonIgnore;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuiz;

public class LectureModelView implements ILecture
{
	private int id;
	private String title;
	private String description;
	private String verificationKey;
	@JsonIgnore
	private Map<Integer, IQuiz> quiz;
	@JsonIgnore
	private Set<Integer> attendees;
	@JsonIgnore
	private Set<Integer> admins;
	
	public LectureModelView() {
		quiz = new HashMap<Integer, IQuiz>();
		attendees = new HashSet<Integer>();
		admins = new HashSet<Integer>();
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
	public void addAttendee(int userId) {
		attendees.add(id);
	}
	
	@Override
	public void removeAttendee(int userId) {
		attendees.remove(userId);
	}
	
	@Override
	public Iterable<Integer> getAttendees() {
		return attendees;
	}
	
	@Override
	public void addAdmin(int userId) {
		attendees.add(id);
	}
	
	@Override
	public void removeAdmin(int userId) {
		attendees.remove(userId);
	}
	
	@Override
	public Iterable<Integer> getAdmins() {
		return admins;
	}
	
	
	@Override
	public void addQuiz(IQuiz quiz) {
		this.quiz.put(quiz.getId(), quiz);		
	}
	
	@Override
	public void removeQuiz(int quizId) {
		this.quiz.remove(quizId);		
	}
	
	@Override
	public Iterable<IQuiz> getQuiz() {
		return this.quiz.values();
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
	public IQuiz getQuiz(int quizId) {
		return quiz.get(quizId);
	}
}
