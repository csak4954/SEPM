package at.uibk.los.model;

import java.util.Random;

public class DataManipulation implements IDataManipulation 
{
	private IDataStorage dataStorage;
	
	public DataManipulation(IDataStorage storage)
	{
		dataStorage = storage;
	}

	@Override
	public void addLecture(ILecture lecture) 
	{
		dataStorage.addLecture(lecture);	
	}

	@Override
	public void removeLecture(int lectureId) 
	{
		dataStorage.removeLecture(lectureId);
	}

	@Override
	public void addQuiz(int lectureId, IQuiz quiz) throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException("lecture not found");
		
		lec.addQuiz(quiz);
	}

	@Override
	public void removeQuiz(int lectureId, int quizId) throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException("lecture not found");
		
		lec.removeQuiz(quizId);
	}

	@Override
	public void endAttendanceVerification(int lectureId)  throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException("lecture not found");

		lec.setVerificationKey(null);
	}


	@Override
	public void confirmAttendance(int userId, int lectureId, String key) throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new EntityNotFoundException("lecture not found");

		if(lec.getVerificationKey().isEmpty())
			throw new IllegalArgumentException("Verification is not active");
		
		if(!lec.getVerificationKey().equals(key))
			throw new IllegalArgumentException("Invalid verification key");
		
		lec.addAttendee(userId);
	}

	@Override
	public void submitAnswer(int userId, int lectureId, int quizId, int questionId,  int[] answers)  throws EntityNotFoundException
	{
		ILecture lecture = dataStorage.getLecture(lectureId);
		if(lecture == null)
			throw new EntityNotFoundException("lecture not found");
		
		IQuiz quiz = lecture.getQuiz(quizId);
		if(quiz == null)
			throw new EntityNotFoundException("quiz not found");
			
		quiz.addApproach(userId, questionId, answers);
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
	public String renewAttendanceVerification(int lectureId)  throws EntityNotFoundException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new IllegalArgumentException("Invalid lecture id");
		
		lec.setVerificationKey(generateVerificationKey());
		
		return lec.getVerificationKey();
	}
}
