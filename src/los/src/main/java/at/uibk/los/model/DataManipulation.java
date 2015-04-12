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
	public void removeLecture(ILecture lecture) 
	{
		dataStorage.removeLecture(lecture.getId());
	}

	@Override
	public void addQuiz(IQuiz quiz) 
	{
		dataStorage.addQuiz(quiz);
	}

	@Override
	public void removeQuiz(IQuiz quiz) 
	{
		dataStorage.removeQuiz(quiz.getId());
	}

	@Override
	public void startAttendanceVerification(ILecture lecture) 
	{
		lecture.startAttendanceVerification();
	}

	@Override
	public void endAttendanceVerification(ILecture lecture) 
	{
		lecture.endAttendanceVerification();
	}


	@Override
	public void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException, IllegalArgumentException
	{
		ILecture lec = dataStorage.getLecture(lectureId);
		if(lec == null)
			throw new IllegalArgumentException("Invalid lecture id");

		if(lec.isAttendanceVerificationActive())
			throw new IllegalArgumentException("Verification is not active");
		
		if(!lec.getVerificationKey().equals(key))
			throw new IllegalArgumentException("Invalid verification key");
		
		lec.addAttendee(userId);
	}

	@Override
	public void submitAnswer(int userId, int quizId, int[] answers) throws LOSAccessDeniedException, IllegalArgumentException
	{
		IQuiz quiz = dataStorage.getQuiz(quizId);
		if(quiz == null)
			throw new IllegalArgumentException("Invalid quiz id");
			
			
		quiz.submitAnswer(userId, answers);
	}
}
