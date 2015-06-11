package at.uibk.los.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import at.uibk.los.model.interfaces.IDataManipulation;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuestion;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizView;

public class DataManipulation implements IDataManipulation 
{
	private IDataStorage dataStorage;
	
	public DataManipulation(IDataStorage storage)
	{
		dataStorage = storage;
	}

	@Override
	public ILecture addLecture(String title, String description) 
	{
		ILecture lecture = dataStorage.createLecture();
		lecture.setTitle(title);
		lecture.setDescription(description);
		
		dataStorage.addLecture(lecture);	
		
		return lecture;
	}

	@Override
	public void removeLecture(String lectureId) throws EntityNotFoundException 
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		dataStorage.removeLecture(lectureId);
	}

	@Override
	public IQuiz addQuiz(String lectureId, String title) throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		return lec.addQuiz(title);
	}

	@Override
	public void removeQuiz(String lectureId, String quizId) throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		if(!lec.removeQuiz(quizId)) {
			throw new EntityNotFoundException(quizId);
		}
	}

	@Override
	public void endAttendanceVerification(String lectureId)  throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);

		lec.setVerificationKey(null);
	}

	@Override
	public void confirmAttendance(String userId, String lectureId, String key) throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);

		if(lec.getVerificationKey().isEmpty())
			throw new IllegalArgumentException("Verification is not active");
		
		if(!lec.getVerificationKey().equals(key))
			throw new IllegalArgumentException("Invalid verification key");
		
		lec.registerUser(userId);
		lec.addAttendance(userId);
	}

	@Override
	public void submitAnswer(String userId, String lectureId, String quizId, String questionId,  List<String> answers)  throws EntityNotFoundException
	{
		ILecture lecture = dataStorage.getLecture(lectureId);
		if(lecture == null)
			throw new EntityNotFoundException(lectureId);
		
		IQuiz quiz = lecture.getQuiz(quizId);
		if(quiz == null)
			throw new EntityNotFoundException(quizId);
		
		if(!quiz.isActive())
			throw new IllegalStateException(quizId);
			
		IQuestion question = quiz.getQuestion(questionId);
		if(question == null)
			throw new EntityNotFoundException(questionId);
		
		question.addApproach(userId, answers);
	}

	private String generateVerificationKey()
	{
		Random r = new Random();
		String key = "";
		while (key.length() < 4) {
			key += r.nextInt(9);
		}
		
		return key;
	}
	
	@Override
	public String renewAttendanceVerification(String lectureId)  throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		lec.setVerificationKey(generateVerificationKey());
		
		return lec.getVerificationKey();
	}

	
	@Override
	public void endQuiz(String quizId) throws EntityNotFoundException {
		IQuiz quiz = dataStorage.getQuiz(quizId);
		if(quiz == null)
			throw new EntityNotFoundException(quizId);
		
		quiz.stop();
	}

	
	@Override
	public void startQuiz(String quizId) throws EntityNotFoundException {
		IQuiz quiz = dataStorage.getQuiz(quizId);
		if(quiz == null)
			throw new EntityNotFoundException(quizId);
		
		quiz.start();
	}

	
	@Override
	public void submitFeedback(String lectureId, int rating, String text) throws EntityNotFoundException {
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		lec.submitFeedback(rating, text);
	}

	
	@Override
	public List<IQuiz> getActiveQuiz(String userId) {
		List<ILecture> lectures = dataStorage.getLecturesForUser(userId);
		
		List<IQuiz> quiz = new LinkedList<IQuiz>();
		if(lectures != null)
		{
			for(ILecture lecture : lectures)
			{
				List<IQuiz> tmp = lecture.getQuiz();
				
				if(tmp != null)
				{
					for(IQuiz q : tmp) {
						if(q.isActive()) quiz.add(q);
					}
				}
			}
		}
		
		return quiz;
	}

	@Override
	public void addAdmin(String lectureId, String userId) throws EntityNotFoundException 
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		lec.addAdmin(userId);
	}

	@Override
	public void removeAdmin(String lectureId, String userId) throws EntityNotFoundException 
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		lec.removeAdmin(userId);
	}

	@Override
	public void unregisterFromLecture(String lectureId, String userId) throws EntityNotFoundException {
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException(lectureId);
		
		lec.unregisterUser(userId);
	}
}
