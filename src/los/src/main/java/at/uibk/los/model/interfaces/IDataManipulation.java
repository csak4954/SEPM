package at.uibk.los.model.interfaces;

import java.util.List;

import at.uibk.los.model.EntityNotFoundException;

public interface IDataManipulation
{
	ILecture addLecture(String title, String description);
	
	void removeLecture(String lectureId) throws EntityNotFoundException;
	
	IQuiz addQuiz(String lectureId) throws EntityNotFoundException;
	
	void removeQuiz(String lectureId, String quizId) throws EntityNotFoundException;
	
	String renewAttendanceVerification(String lectureId) throws EntityNotFoundException;
	
	void endAttendanceVerification(String lectureId) throws EntityNotFoundException;
	
	void confirmAttendance(String userId, String lectureId, String key) throws EntityNotFoundException;
	
	void submitAnswer(String userId, String lecture, String quizId,  String questionId, List<String> answers) throws EntityNotFoundException;

	void endQuiz(String quizId) throws EntityNotFoundException;

	void startQuiz(String quizId) throws EntityNotFoundException;;

	void submitFeedback(String lectureId, int rating, String text) throws EntityNotFoundException;

	List<IQuiz> getActiveQuiz(String userId);

	void addAdmin(String lectureId, String userId) throws EntityNotFoundException;
}
