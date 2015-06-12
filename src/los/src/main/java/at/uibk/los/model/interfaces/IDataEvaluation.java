package at.uibk.los.model.interfaces;

import java.util.List;

import at.uibk.los.model.exceptions.EntityNotFoundException;

public interface IDataEvaluation
{
	List<IFeedback> getFeedback(String lectureId) throws EntityNotFoundException;

	boolean isUserVerified(String userId, String lectureId) throws EntityNotFoundException;

	boolean isUserAdmin(String userId, String lectureId) throws EntityNotFoundException;

	public abstract List<IScore> getScores(String userId);

	List<IQuizResult> getQuizResults(String c);

	IStatistics getStatistics(String lectureId) throws EntityNotFoundException;

	ILectureView getLecture(String lectureId) throws EntityNotFoundException;
}
