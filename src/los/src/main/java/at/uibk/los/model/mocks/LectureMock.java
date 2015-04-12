package at.uibk.los.model.mocks;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import at.uibk.los.model.ILecture;
import at.uibk.los.model.IQuiz;

public class LectureMock implements ILecture {
	
	private int id;
	private String title;
	private String description;
	private String verificationKey;
	private Date    verificationKeyDate;
	private boolean isVerificationActive;
	private long    verificationKeyTime;
	
	private Map<Integer, IQuiz> quiz;
	private Set<Integer> attendees;
	private Set<Integer> admins;
	
	public LectureMock() {
		quiz = new HashMap<Integer, IQuiz>();
		attendees = new HashSet<Integer>();
		admins = new HashSet<Integer>();
		isVerificationActive = false;
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
	public String getVerificationKey() 
	{
		if(new Date().getTime() - verificationKeyDate.getTime() > verificationKeyTime)
			verificationKey = generateVerificationKey();
		
		return verificationKey;
	}
	@Override
	public void startAttendanceVerification() 
	{
		this.verificationKey = generateVerificationKey();
		this.isVerificationActive = true;
		this.verificationKeyDate = new Date();
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
	
	private String generateVerificationKey()
	{
		Random r = new Random();
		String key = "";
		while (key.length() < 4) {
			key += r.nextInt(9);
		}
		
		return key;
	}

	@Override
	public void endAttendanceVerification() 
	{
		isVerificationActive = false;
	}

	@Override
	public boolean isAttendanceVerificationActive() 
	{
		return isVerificationActive;
	}
}
