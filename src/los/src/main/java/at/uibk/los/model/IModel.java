package at.uibk.los.model;

public interface IModel
{
	boolean loginUser();
	void logoutUser();
	IUser getUser();
	Iterable<ILecture> getAssociatedLectures() throws LOSAccessDeniedException;
	void addLecture(int lectureId, String title, String description) throws LOSAccessDeniedException;
	void removeLecture(int id) throws LOSAccessDeniedException;
	void addLectureAdmin(int lectureId) throws LOSAccessDeniedException;
	void startAttendanceVerification(int lectureId) throws LOSAccessDeniedException;
	void endAttendanceVerification(int lectureId) throws LOSAccessDeniedException;
	void startQuiz(IQuiz quiz) throws LOSAccessDeniedException;
	void endQuiz(int quizId) throws LOSAccessDeniedException;
	IStatistics getStatistics(int lectureId) throws LOSAccessDeniedException;
	IResults getStudentResults(int lectureId) throws LOSAccessDeniedException;
	IFeedBack getFeedBack(int lectureId) throws LOSAccessDeniedException;
	void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException;
	IQuiz getQuiz(int quizId) throws LOSAccessDeniedException;
	void submitAnswer(int userId, int quizId, int[] answers) throws LOSAccessDeniedException;
	IPerformance getPerformance(int lectureId) throws LOSAccessDeniedException;
	IQuiz createQuiz() throws LOSAccessDeniedException;
	ILecture createLecture() throws LOSAccessDeniedException;
}
