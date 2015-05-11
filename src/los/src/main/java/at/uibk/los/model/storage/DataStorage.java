package at.uibk.los.model.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuiz;

@Component
public class DataStorage implements IDataStorage {

	public DataStorage() {
				
	}
	
	@Override
	public void addLecture(ILecture lecture) {
		lectures.insert(new Lecture(lecture));
	}

	@Override
	public void removeLecture(int lectureId) {
		lectures.delete(lectureId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ILecture> getLectures() {
		return (Iterable<ILecture>)(Iterable<?>)lectures.findAll();
	}

	@Override
	public ILecture getLecture(int id) {
		return lectures.findOne(id);
	}

	@Override
	public Iterable<ILecture> getLecturesForUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQuiz createQuiz() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILecture createLecture() {
		return new Lecture();
	}

	@Autowired
	private LectureRepository lectures;
	
	public static DataStorage loadFromContext(ApplicationContext ctx) {
		return ctx.getBean(DataStorage.class);
	}
}
