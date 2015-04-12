package at.uibk.los.model;

public interface IDataManipulation
{
	void addLecture(ILecture lecture);
	void removeLecture(ILecture lecture);
	void addQuiz(IQuiz quiz);
	void removeQuiz(IQuiz quiz);
	void startAttendanceVerification(ILecture lecture);
	void endAttendanceVerification(ILecture lecture);
	String renewVerificationKey(ILecture lecture) throws LOSAccessDeniedException;
	void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException, IllegalArgumentException;
	void submitAnswer(int userId, int quizId, int[] answers) throws LOSAccessDeniedException;
}
