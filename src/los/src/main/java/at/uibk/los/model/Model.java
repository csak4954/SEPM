package at.uibk.los.model;

import java.util.List;

import at.uibk.los.model.authorization.AdminGroupPolicy;
import at.uibk.los.model.authorization.LOSAccessDeniedException;
import at.uibk.los.model.authorization.permissions.AttendanceVerificationPermission;
import at.uibk.los.model.authorization.permissions.ControlQuizPermission;
import at.uibk.los.model.authorization.permissions.LectureAdminPermission;
import at.uibk.los.model.authorization.permissions.ModifyAdminCollectionPermission;
import at.uibk.los.model.authorization.permissions.ModifyLectureCollectionPermission;
import at.uibk.los.model.authorization.permissions.ModifyQuizCollectionPermission;
import at.uibk.los.model.authorization.permissions.VerifiedPermission;
import at.uibk.los.model.authorization.permissions.ViewFeedbackPermission;
import at.uibk.los.model.authorization.permissions.ViewStatisticsPermission;
import at.uibk.los.model.authorization.permissions.ViewUserScoresPermission;
import at.uibk.los.model.exceptions.EntityNotFoundException;
import at.uibk.los.model.exceptions.InvalidVerificationKeyException;
import at.uibk.los.model.exceptions.QuizInactiveException;
import at.uibk.los.model.exceptions.VerificationInactiveException;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IModel;
import at.uibk.los.model.interfaces.IPermission;
import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuizResult;
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
		checkPermissions();
		return (List<ILectureView>)(List<?>)provider.getStorage().getLecturesForUser(getUser().getId());
	}

	@Override
	public ILectureView addLecture(String title, String description)
			throws LOSAccessDeniedException
	{
		checkPermissions(ModifyLectureCollectionPermission.instance);
		return provider.getManipulation().addLecture(title, description);
	}

	@Override
	public void removeLecture(String id) throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(ModifyLectureCollectionPermission.instance);
		checkIsAdmin(id);
		provider.getManipulation().removeLecture(id);
	}

	@Override
	public String renewAttendanceVerification(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(AttendanceVerificationPermission.instance);
		checkIsAdmin(lectureId);
		return provider.getManipulation().renewAttendanceVerification(lectureId);
	}
	
	@Override
	public void endAttendanceVerification(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(AttendanceVerificationPermission.instance);
		provider.getManipulation().endAttendanceVerification(lectureId);
	}

	@Override
	public void startQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(ControlQuizPermission.instance);
		checkIsAdmin(lectureId);
		provider.getManipulation().startQuiz(quizId);
	}

	@Override
	public void endQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(ControlQuizPermission.instance);
		checkIsAdmin(lectureId);
		provider.getManipulation().endQuiz(quizId);
	}

	@Override
	public IStatistics getStatistics(String lectureId)
			throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(ViewStatisticsPermission.instance);
		checkIsAdmin(lectureId);
		return provider.getEvaluation().getStatistics(lectureId);
	}
	

	@Override
	public List<IFeedback> getFeedback(String lectureId)
			throws LOSAccessDeniedException, EntityNotFoundException
	{
		checkPermissions(ViewFeedbackPermission.instance);
		checkIsAdmin(lectureId);
		return provider.getEvaluation().getFeedback(lectureId);
	}

	@Override
	public void submitAnswer(String lectureId, String quizId, String questionId, List<String> answers) throws LOSAccessDeniedException, EntityNotFoundException, QuizInactiveException
	{
		checkPermissions();
		checkIsVerified(lectureId);
		provider.getManipulation().submitAnswer(getUser().getId(), lectureId, quizId, questionId, answers);
	}

	@Override
	public void addAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		checkPermissions(ModifyAdminCollectionPermission.instance);
		provider.getManipulation().addAdmin(lectureId, getUser().getId());
	}
	

	@Override
	public void removeAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		checkPermissions(ModifyAdminCollectionPermission.instance);
		provider.getManipulation().removeAdmin(lectureId, getUser().getId());	
	}

	@Override
	public IUser getUser() {
		
		if(provider.getLoginProvider().isNew()) {
			provider.getStorage().saveUser(provider.getLoginProvider().getUser());
		}
		
		return provider.getLoginProvider().getUser();
	}

	@Override
	public void confirmAttendance(String lectureId, String key) throws LOSAccessDeniedException, EntityNotFoundException, VerificationInactiveException, InvalidVerificationKeyException
	{
		checkPermissions();
		provider.getManipulation().confirmAttendance(getUser().getId(), lectureId, key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ILectureView> getAvailableLectures() throws LOSAccessDeniedException
	{
		checkPermissions();
		return (List<ILectureView>)(List<?>)provider.getStorage().getLectures();
	}

	@Override
	public IQuiz createQuiz(String lectureId, String title) throws EntityNotFoundException, LOSAccessDeniedException {
		checkPermissions(ModifyQuizCollectionPermission.instance);
		checkIsAdmin(lectureId);
		return provider.getManipulation().addQuiz(lectureId, title);
	}
	
	@Override
	public void removeQuiz(String lectureId, String quizId) throws LOSAccessDeniedException, EntityNotFoundException {
		checkPermissions(ModifyQuizCollectionPermission.instance);
		checkIsAdmin(lectureId);
		provider.getManipulation().removeQuiz(lectureId, quizId);
	}

	
	@Override
	public void submitFeedback(String lectureId, int rating, String text) throws EntityNotFoundException, LOSAccessDeniedException 
	{
		checkPermissions();
		checkIsVerified(lectureId);
		provider.getManipulation().submitFeedback(lectureId, rating, text);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<IQuizView> getActiveQuiz() throws LOSAccessDeniedException {
		checkPermissions();
		return (List<IQuizView>)(List<?>)provider.getManipulation().getActiveQuiz(getUser().getId());
	}

	@Override
	public List<IScore> getScores() throws LOSAccessDeniedException{
		checkPermissions();
		return provider.getEvaluation().getScores(getUser().getId());
	}
	
	@Override
	public boolean isUserVerified(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		checkPermissions();
		return provider.getEvaluation().isUserVerified(getUser().getId(), lectureId);
	}
	

	@Override
	public boolean isUserAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		checkPermissions();
		return getUser().getGroupPolicy() == AdminGroupPolicy.id || provider.getEvaluation().isUserAdmin(getUser().getId(), lectureId);
	}
	
	@Override
	public List<IScore> getScores(String userId) throws LOSAccessDeniedException {
		checkPermissions(ViewUserScoresPermission.instance);
		return provider.getEvaluation().getScores(userId);
	}

	@Override
	public List<IQuizResult> getQuizResults() throws LOSAccessDeniedException {
		checkPermissions();
		return provider.getEvaluation().getQuizResults(getUser().getId());
	}

	@Override
	public List<IQuizResult> getQuizResults(String userId) throws LOSAccessDeniedException {
		checkPermissions(ViewUserScoresPermission.instance);
		return provider.getEvaluation().getQuizResults(userId);
	}
	
	private void checkPermissions(IPermission...permissions) throws LOSAccessDeniedException {
		provider.getPolicyManager().verify(getUser(), permissions);
	}

	private void checkIsAdmin(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException{
		if(!isUserAdmin(lectureId)) {
			throw new LOSAccessDeniedException(LectureAdminPermission.instance);
		}
	}
	
	private void checkIsVerified(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException{
		if(!isUserVerified(lectureId)) {
			throw new LOSAccessDeniedException(VerifiedPermission.instance);
		}
	}

	@Override
	public void unregisterFromLecture(String lectureId) throws LOSAccessDeniedException, EntityNotFoundException {
		checkPermissions();
		provider.getManipulation().unregisterFromLecture(lectureId, getUser().getId());
	}

	@Override
	public ILectureView getLecture(String lectureId) throws LOSAccessDeniedException,
			EntityNotFoundException
	{
		checkPermissions();
		return provider.getEvaluation().getLecture(lectureId);
	}

	@Override
	public IServiceProvider getServiceProvider() {
		return provider;
	}
}
