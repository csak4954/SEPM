package at.uibk.los.model;

public interface IDataStorage
{
	void addLecture(ILecture lecture);
	void removeLecture(ILecture lecture);
	Iterable<ILecture> getLectures();
	void addQuiz(IQuiz quiz);
	void removeQuiz(IQuiz quiz);
	Iterable<IQuiz> getQuiz();
	IQuizBuilder getQuizBuilder();
	ILectureBuilder getLectureBuilder();
	ILectureBuilder getLectureBuilder(ILecture lecture);
}
