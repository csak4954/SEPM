package at.uibk.los.model;

import java.util.List;

import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.Permission;
import at.uibk.los.model.interfaces.IDataEvaluation;
import at.uibk.los.model.interfaces.IDataManipulation;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.ILoginProvider;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IPerformance;
import at.uibk.los.model.interfaces.IPolicyManager;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IScore;
import at.uibk.los.model.interfaces.IServiceProvider;
import at.uibk.los.model.interfaces.IStatistics;
import at.uibk.los.model.interfaces.IUser;

public class Model implements IModel
{	
	private IServiceProvider provider;
	
	public Model(IServiceProvider provider)
	{
		if(provider == null) 
		{
			throw new IllegalArgumentException("login provider must not be null");
		}
		
		this.provider = provider;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ILectureView> getAssociatedLectures() throws LOSAccessDeniedException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		return (List<ILectureView>)(List<?>)provider.getStorage().getLecturesForUser(getUser().getId());
	}

	@Override
	public ILectureView addLecture(String title, String description)
			throws LOSAccessDeniedException
	{
		provider.getPolicyManager().verify(getUser(), ModifyLectureCollectionPermission.instance);
		return provider.getManipulation().addLecture(title, description);
	}

	@Override
	public void removeLecture(String id) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), ModifyLectureCollectionPermission.instance);
		checkIsAdmin(id);
		provider.getManipulation().removeLecture(id);
	}

	@Override
	public String renewAttendanceVerification(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		return provider.getManipulation().renewAttendanceVerification(lectureId);
	}
	
	@Override
	public void endAttendanceVerification(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		provider.getManipulation().endAttendanceVerification(lectureId);
	}

	@Override
	public void startQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		provider.getManipulation().startQuiz(quizId);
	}

	@Override
	public void endQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		provider.getManipulation().endQuiz(quizId);
	}

	@Override
	public IStatistics getStatistics(String lectureId)
			throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		throw new NotImplementedException();
	}
	
	@Override
	public IScore getStudentResults(String lectureId)
			throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		throw new NotImplementedException();
	}

	@Override
	public List<IFeedback> getFeedback(String lectureId)
			throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		return provider.getEvaluation().getFeedback(lectureId);
	}

	@Override
	public void submitAnswer(String lectureId, String quizId, String questionId, List<String> answers) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsVerified(lectureId);
		provider.getManipulation().submitAnswer(getUser().getId(), lectureId, quizId, questionId, answers);
	}

	@Override
	public IPerformance getPerformance(String quizId) throws LOSAccessDeniedException
	{
		throw new NotImplementedException();
	}

	@Override
	public void addAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		provider.getManipulation().addAdmin(lectureId, getUser().getId());
	}

	@Override
	public IUser getUser() {
		
		if(provider.getLoginProvider().isNew()) {
			provider.getStorage().saveUser(provider.getLoginProvider().getUser());
		}
		
		return provider.getLoginProvider().getUser();
	}

	@Override
	public void confirmAttendance(String lectureId, String key) throws LOSAccessDeniedException, EntityNotFoundException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		provider.getManipulation().confirmAttendance(getUser().getId(), lectureId, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ILectureView> getAvailableLectures() throws LOSAccessDeniedException
	{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		return (List<ILectureView>)(List<?>)provider.getStorage().getLectures();
	}

	@Override
	public IQuiz createQuiz(String lectureId) throws EntityNotFoundException, LOSAccessDeniedException {
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		checkIsAdmin(lectureId);
		return provider.getManipulation().addQuiz(lectureId);
	}

	
	@Override
	public void submitFeedback(String lectureId, int rating, String text) throws EntityNotFoundException {
		provider.getManipulation().submitFeedback(lectureId, rating, text);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<IQuizView> getActiveQuiz() {
		return (List<IQuizView>)(List<?>)provider.getManipulation().getActiveQuiz(getUser().getId());
	}

	@Override
	public List<IScore> getScores() throws LOSAccessDeniedException{
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		return provider.getEvaluation().getScores(getUser().getId());
	}
	
	@Override
	public boolean isUserVerified(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		return provider.getEvaluation().isUserVerified(getUser().getId(), lectureId);
	}
	

	@Override
	public boolean isUserAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		return provider.getEvaluation().isUserAdmin(getUser().getId(), lectureId);
	}
	
	private void checkIsAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException{
		if(!isUserAdmin(lectureId)) {
			throw new LOSAccessDeniedException();
		}
	}
	
	private void checkIsVerified(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException{
		if(!isUserVerified(lectureId)) {
			throw new LOSAccessDeniedException();
		}
	}

	@Override
	public List<IScore> getScores(String userId) throws LOSAccessDeniedException {
		provider.getPolicyManager().verify(getUser(), Permission.instance);
		return provider.getEvaluation().getScores(userId);
	}
}
