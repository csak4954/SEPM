package at.uibk.los.model.interfaces;

import java.util.List;

public interface ILecture extends ILectureView
{
	void setTitle(String title);
	
	void setDescription(String description);
	
	void setVerificationKey(String key);
	String getVerificationKey();
	
	void registerUser(String userId);
	boolean unregisterUser(String userId);
	
	void addAttendance(String userId);
	boolean removeAttendance(String userId);
	
	void addAdmin(String userId);
	boolean removeAdmin(String userId);
	
	IQuiz addQuiz(String title);
	boolean removeQuiz(String quizId);
	
	IQuiz getQuiz(String quizId);
	List<IQuiz> getQuiz();
	
	void submitFeedback(int rating, String text);
	List<IFeedback> getFeedback();
}
