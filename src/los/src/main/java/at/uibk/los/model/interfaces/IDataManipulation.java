package at.uibk.los.model.interfaces;

import at.uibk.los.model.EntityNotFoundException;

public interface IDataManipulation
{
	void addLecture(ILecture lecture);
	void removeLecture(int lectureId);
	void addQuiz(int lectureId, IQuiz quiz) throws EntityNotFoundException;
	void removeQuiz(int lectureId, int quizId) throws EntityNotFoundException;
	String renewAttendanceVerification(int lectureId) throws EntityNotFoundException;
	void endAttendanceVerification(int lectureId) throws EntityNotFoundException;
	void confirmAttendance(int userId, int lectureId, String key) throws EntityNotFoundException;
	void submitAnswer(int userId, int lecture, int quizId,  int questionId, int[] answers) throws EntityNotFoundException;
}
