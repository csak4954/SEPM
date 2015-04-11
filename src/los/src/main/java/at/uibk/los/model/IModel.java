package at.uibk.los.model;

public interface IModel
{
	boolean loginUser();
	void logoffUser();
	Iterable<ILecture> getLectures() throws LOSAccessDeniedException;
	void addLecture(String title, String description) throws LOSAccessDeniedException;
	void removeLecture(int id) throws LOSAccessDeniedException;
	void startAttendanceVerification(ILecture lecture) throws LOSAccessDeniedException;
	void endAttendanceVerification(ILecture lecture) throws LOSAccessDeniedException;
	String renewVerificationKey(ILecture lecture) throws LOSAccessDeniedException;
	void startQuiz(IQuiz quiz) throws LOSAccessDeniedException;
	void endQuiz(IQuiz quiz) throws LOSAccessDeniedException;
	IStatistics getStatistics(ILecture lecture) throws LOSAccessDeniedException;
	IResults getStudentResults(ILecture lecture) throws LOSAccessDeniedException;
	IFeedBack getFeedBack(ILecture lecture) throws LOSAccessDeniedException;
	void confirmAttendance(String key) throws LOSAccessDeniedException;
	IQuiz getQuiz() throws LOSAccessDeniedException;
	void submitAnswer(IQuiz quiz, int[] answers) throws LOSAccessDeniedException;
	ISolutions getSolutions(IQuiz quiz) throws LOSAccessDeniedException;
	IPerformance getPerformance(ILecture lecture) throws LOSAccessDeniedException;
	IQuizBuilder getQuizBuilder() throws LOSAccessDeniedException;
	ILectureBuilder getLectureBuilder() throws LOSAccessDeniedException;	
}
