package at.uibk.los.model.interfaces;

public interface IDataStorage
{
	void addLecture(ILecture lecture);
	void removeLecture(int lectureId);
	Iterable<ILecture> getLectures();
	ILecture getLecture(int id);
	Iterable<ILecture> getLecturesForUser(int userId);
	IQuiz createQuiz();
	ILecture createLecture();
}
