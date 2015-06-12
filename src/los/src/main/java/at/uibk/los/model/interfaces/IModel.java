package at.uibk.los.model.interfaces;

import java.util.List;

import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.exceptions.InvalidVerificationKeyException;
import at.uibk.los.model.exceptions.QuizInactiveException;
import at.uibk.los.model.exceptions.VerificationInactiveException;

public interface IModel
{
	// service provider
	IServiceProvider getServiceProvider();
	
	// user-specific	
	IUser getUser() throws LOSAccessDeniedException;
	
	boolean isUserVerified(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	boolean isUserAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	// lecture
	List<ILectureView> getAssociatedLectures() throws LOSAccessDeniedException;
	
	List<ILectureView> getAvailableLectures() throws LOSAccessDeniedException;
	
	ILectureView getLecture(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	ILectureView addLecture(String title, String description) throws LOSAccessDeniedException;
	
	void removeLecture(String id) throws LOSAccessDeniedException, EntityNotFoundException;
	
	void addAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	void removeAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	// attendance
	String renewAttendanceVerification(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	void endAttendanceVerification(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	void confirmAttendance(String lectureId, String key) throws LOSAccessDeniedException, EntityNotFoundException, VerificationInactiveException, InvalidVerificationKeyException;
		
	void unregisterFromLecture(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException; 
	
	
	// quiz
	IQuiz createQuiz(String lectureId, String title) throws LOSAccessDeniedException, EntityNotFoundException;
	
	void removeQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException; 
	
	void startQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException;
	
	void endQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException;

	void submitAnswer(String lectureId, String quizId, String questionId, List<String> answers) throws LOSAccessDeniedException, EntityNotFoundException, QuizInactiveException;

	List<IQuizView> getActiveQuiz() throws LOSAccessDeniedException;
	
	
	// feed back
	void submitFeedback(String lectureId, int rating, String text) throws EntityNotFoundException, LOSAccessDeniedException;
	
	List<IFeedback> getFeedback(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	

	// statistics
	IStatistics getStatistics(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
		
	List<IScore> getScores() throws LOSAccessDeniedException;
	
	List<IScore> getScores(String userId) throws LOSAccessDeniedException;
	
	List<IQuizResult> getQuizResults() throws LOSAccessDeniedException;
	
	List<IQuizResult> getQuizResults(String userId) throws LOSAccessDeniedException;
}
