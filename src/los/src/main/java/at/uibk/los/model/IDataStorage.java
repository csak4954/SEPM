package at.uibk.los.model;

public interface IDataStorage
{
	void addLecture(ILecture lecture);
	void removeLecture(int lectureId);
	Iterable<ILecture> getLectures();
	ILecture getLecture(int id);
	Iterable<ILecture> getLecturesForUser(int userId);
	IQuiz createQuiz();
	void addQuiz(IQuiz quiz);
	void removeQuiz(int quizId);
	IQuiz getQuiz(int quizId);
	ILecture createLecture();
}
