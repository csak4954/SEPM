package at.uibk.los.model;

import at.uibk.los.model.mocks.DataStorageMock;

public class Model implements IModel
{
	private ILoginProvider loginProvider;
	private IDataStorage dataStorage;
	private IDataManipulation dataManipulation;
	private IDataEvaluation dataEvaluation;
	
	public Model(ILoginProvider provider)
	{
		if(provider == null) 
		{
			throw new IllegalArgumentException("login provider must not be null");
		}
		
		loginProvider = provider;
		
		dataStorage = new DataStorageMock();
		
		dataManipulation = new DataManipulation(dataStorage);
		dataEvaluation = null;
	}
	
	@Override
	public boolean loginUser()
	{
		return loginProvider.login();		
	}

	@Override
	public void logoutUser()
	{
		if(getUser() == null) 
			throw new IllegalStateException();
		
		loginProvider.logout();	
	}

	@Override
	public Iterable<ILecture> getAssociatedLectures() throws LOSAccessDeniedException
	{
		return dataStorage.getLectures();
	}

	@Override
	public void addLecture(int lectureId, String title, String description)
			throws LOSAccessDeniedException
	{
		ILecture lecture = dataStorage.createLecture();
		lecture.setId(lectureId);
		lecture.setTitle(title);
		lecture.setDescription(description);
		dataManipulation.addLecture(lecture);
	}

	@Override
	public void removeLecture(int id) throws LOSAccessDeniedException
	{
		dataManipulation.removeLecture(id);
	}

	@Override
	public String renewAttendanceVerification(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		return dataManipulation.renewAttendanceVerification(lectureId);
	}
	
	@Override
	public void endAttendanceVerification(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		dataManipulation.endAttendanceVerification(lectureId);
	}

	@Override
	public void startQuiz(int lectureId, IQuiz quiz) throws LOSAccessDeniedException, EntityNotFoundException
	{
		throw new NotImplementedException();
	}

	@Override
	public void endQuiz(int lectureId, int quizId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		throw new NotImplementedException();
	}

	@Override
	public IStatistics getStatistics(int lectureId)
			throws LOSAccessDeniedException
	{
		throw new NotImplementedException();
	}
	
	@Override
	public IResults getStudentResults(int lectureId)
			throws LOSAccessDeniedException
	{
		throw new NotImplementedException();
	}

	@Override
	public IFeedBack getFeedBack(int lectureId)
			throws LOSAccessDeniedException
	{
		throw new NotImplementedException();
	}

	@Override
	public void submitAnswer(int lectureId, int quizId, int questionId, int[] answers) throws LOSAccessDeniedException, EntityNotFoundException
	{
		dataManipulation.submitAnswer(loginProvider.getUser().getId(), lectureId, quizId, questionId, answers);
	}

	@Override
	public IPerformance getPerformance(int quizId)
			throws LOSAccessDeniedException
	{
		throw new NotImplementedException();
	}

	@Override
	public void addLectureAdmin(int lectureId) throws LOSAccessDeniedException {
		
	}

	@Override
	public IQuiz createQuiz() throws LOSAccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILecture createLecture() throws LOSAccessDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUser getUser() {
		return loginProvider.getUser();
	}

	@Override
	public void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException, EntityNotFoundException
	{
		dataManipulation.confirmAttendance(userId, lectureId, key);
	}


}
