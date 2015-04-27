package at.uibk.los.model;

import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.Permission;
import at.uibk.los.model.authorization.PolicyManager;
import at.uibk.los.model.interfaces.IDataEvaluation;
import at.uibk.los.model.interfaces.IDataManipulation;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.IFeedBack;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IPerformance;
import at.uibk.los.model.interfaces.IPolicyManager;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IResults;
import at.uibk.los.model.interfaces.IStatistics;
import at.uibk.los.model.interfaces.IUser;
import at.uibk.los.model.mocks.DataStorageMock;
import at.uibk.los.model.storage.DataStorage;

public class Model implements IModel
{
	private ILoginProvider loginProvider;
	private IDataStorage dataStorage;
	private IDataManipulation dataManipulation;
	private IDataEvaluation dataEvaluation;
	private IPolicyManager policyManager;
	
	public Model(ILoginProvider provider)
	{
		if(provider == null) 
		{
			throw new IllegalArgumentException("login provider must not be null");
		}
		
		loginProvider = provider;
		
		dataStorage = new DataStorage();
		dataManipulation = new DataManipulation(dataStorage);
		dataEvaluation = null;
		
		policyManager = new PolicyManager();
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
		policyManager.verify(getUser(), Permission.instance);
		return dataStorage.getLectures();
	}

	@Override
	public void addLecture(int lectureId, String title, String description)
			throws LOSAccessDeniedException
	{
		policyManager.verify(getUser(), ModifyLectureCollectionPermission.instance);
		
		ILecture lecture = dataStorage.createLecture();
		lecture.setId(lectureId);
		lecture.setTitle(title);
		lecture.setDescription(description);
		dataManipulation.addLecture(lecture);
	}

	@Override
	public void removeLecture(int id) throws LOSAccessDeniedException
	{
		policyManager.verify(getUser(), ModifyLectureCollectionPermission.instance);
		dataManipulation.removeLecture(id);
	}

	@Override
	public String renewAttendanceVerification(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		policyManager.verify(getUser(), Permission.instance);
		return dataManipulation.renewAttendanceVerification(lectureId);
	}
	
	@Override
	public void endAttendanceVerification(int lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		policyManager.verify(getUser(), Permission.instance);
		dataManipulation.endAttendanceVerification(lectureId);
	}

	@Override
	public void startQuiz(int lectureId, IQuiz quiz) throws LOSAccessDeniedException, EntityNotFoundException
	{
		policyManager.verify(getUser(), Permission.instance);
		throw new NotImplementedException();
	}

	@Override
	public void endQuiz(int lectureId, int quizId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		policyManager.verify(getUser(), Permission.instance);
		throw new NotImplementedException();
	}

	@Override
	public IStatistics getStatistics(int lectureId)
			throws LOSAccessDeniedException
	{
		policyManager.verify(getUser(), Permission.instance);
		throw new NotImplementedException();
	}
	
	@Override
	public IResults getStudentResults(int lectureId)
			throws LOSAccessDeniedException
	{
		policyManager.verify(getUser(), Permission.instance);
		throw new NotImplementedException();
	}

	@Override
	public IFeedBack getFeedBack(int lectureId)
			throws LOSAccessDeniedException
	{
		policyManager.verify(getUser(), Permission.instance);
		throw new NotImplementedException();
	}

	@Override
	public void submitAnswer(int lectureId, int quizId, int questionId, int[] answers) throws LOSAccessDeniedException, EntityNotFoundException
	{
		policyManager.verify(getUser(), Permission.instance);
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
		policyManager.verify(getUser(), Permission.instance);
	}

	@Override
	public IQuiz createQuiz() throws LOSAccessDeniedException {
		policyManager.verify(getUser(), Permission.instance);
		return null;
	}

	@Override
	public ILecture createLecture() throws LOSAccessDeniedException {
		policyManager.verify(getUser(), Permission.instance);
		return null;
	}

	@Override
	public IUser getUser() {
		return loginProvider.getUser();
	}

	@Override
	public void confirmAttendance(int userId, int lectureId, String key) throws LOSAccessDeniedException, EntityNotFoundException
	{
		policyManager.verify(getUser(), Permission.instance);
		dataManipulation.confirmAttendance(userId, lectureId, key);
	}


}
