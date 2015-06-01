package at.uibk.los.model.interfaces;

import java.util.LinkedList;
import java.util.List;

public interface IDataStorage
{
	void addLecture(ILecture lecture);
	
	void removeLecture(String lectureId);
	
	List<ILecture> getLectures();
	
	ILecture getLecture(String id);
	
	List<ILecture> getLecturesForUser(String userId);
	
	void saveUser(IUser user);
	
	IQuiz getQuiz(String quizId);
	
	ILecture createLecture();

	public abstract List<IApproach> getApproaches(String userId);
}
