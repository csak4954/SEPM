package at.uibk.los.model;

public interface IDataManipulation
{
	void addLecture(ILecture lecture);
	void removeLecture(int lectureId);
	void addQuiz(IQuiz quiz);
	void removeQuiz(IQuiz quiz);
	void startAttendanceVerification(int lectureId);
	void endAttendanceVerification(int lectureId);
	void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException, IllegalArgumentException;
	void submitAnswer(int userId, int quizId, int[] answers) throws LOSAccessDeniedException, IllegalArgumentException;
}
