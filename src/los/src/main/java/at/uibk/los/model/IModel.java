package at.uibk.los.model;

public interface IModel
{
	boolean loginUser();
	void logoutUser();
	IUser getUser();
	Iterable<ILecture> getAssociatedLectures() throws LOSAccessDeniedException;
	void addLecture(int lectureId, String title, String description) throws LOSAccessDeniedException;
	void removeLecture(int id) throws LOSAccessDeniedException;
	void addLectureAdmin(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	String renewAttendanceVerification(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	void endAttendanceVerification(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	void startQuiz(int lectureId, IQuiz quiz) throws LOSAccessDeniedException, EntityNotFoundException;
	void endQuiz(int lectureId, int quizId) throws LOSAccessDeniedException, EntityNotFoundException;
	IStatistics getStatistics(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	IResults getStudentResults(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	IFeedBack getFeedBack(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException, EntityNotFoundException;
	void submitAnswer(int userId, int quizId, int questionId, int[] answers) throws LOSAccessDeniedException, EntityNotFoundException;
	IPerformance getPerformance(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException;
	IQuiz createQuiz() throws LOSAccessDeniedException;
	ILecture createLecture() throws LOSAccessDeniedException;
}
