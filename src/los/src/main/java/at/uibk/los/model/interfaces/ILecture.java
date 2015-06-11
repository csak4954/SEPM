package at.uibk.los.model.interfaces;

import java.util.List;

public interface ILecture extends ILectureView
{
	void setTitle(String title);
	
	void setDescription(String description);
	
	void setVerificationKey(String key);
	
	void registerUser(String userId);
	void unregisterUser(String userId);
	
	void addAttendance(String userId);
	void removeAttendance(String userId);
	
	void addAdmin(String userId);
	void removeAdmin(String userId);
	
	IQuiz addQuiz();
	void removeQuiz(String quizId);
	
	IQuiz getQuiz(String quizId);
	List<IQuiz> getQuiz();
	
	void submitFeedback(int rating, String text);
	List<IFeedback> getFeedback();
}
