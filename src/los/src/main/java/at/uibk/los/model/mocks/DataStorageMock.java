package at.uibk.los.model.mocks;

import java.util.HashMap;
import java.util.Map;

import at.uibk.los.model.IDataStorage;
import at.uibk.los.model.ILecture;
import at.uibk.los.model.IQuiz;
import at.uibk.los.model.NotImplementedException;

public class DataStorageMock implements IDataStorage
{
	private Map<Integer, ILecture> lectures;
	
	public DataStorageMock() {
		
		lectures = new HashMap<Integer, ILecture>();
		
		for(int i = 0; i < 50; i++)
		{
			ILecture lecture = createLecture();
			lecture.setTitle("Introduction to LOS");
			lecture.setDescription("A very interesting lecture");
			lecture.setId(i);
			lecture.setVerificationKey("");
			
			addLecture(lecture);
		}
		
	}
	
	@Override
	public void addLecture(ILecture lecture) {
		lectures.put(lecture.getId(), lecture);
	}

	@Override
	public void removeLecture(int lectureId) {
		lectures.remove(lectureId);
	}

	@Override
	public Iterable<ILecture> getLectures() {
		return lectures.values();
	}

	@Override
	public ILecture getLecture(int id) {
		return lectures.get(id);
	}

	@Override
	public Iterable<ILecture> getLecturesForUser(int userId) {
		throw new NotImplementedException();
	}

	@Override
	public IQuiz createQuiz() {
		return new QuizMock();
	}

	@Override
	public ILecture createLecture() {
		return new LectureMock();
	}
}
