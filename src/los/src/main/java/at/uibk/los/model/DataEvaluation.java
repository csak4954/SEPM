package at.uibk.los.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import at.uibk.los.model.interfaces.IApproach;
import at.uibk.los.model.interfaces.IAttendance;
import at.uibk.los.model.interfaces.IDataEvaluation;
import at.uibk.los.model.interfaces.IDataStorage;
import at.uibk.los.model.interfaces.IFeedback;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.IQuizResult;
import at.uibk.los.model.interfaces.IScore;
import at.uibk.los.model.interfaces.IUser;

public class DataEvaluation implements IDataEvaluation {

	private IDataStorage dataStorage;
	
	public DataEvaluation(IDataStorage dataStorage) {
		this.dataStorage = dataStorage;
	}
	
	@Override
	public List<IFeedback> getFeedback(String lectureId) throws EntityNotFoundException {
		ILecture lecture = dataStorage.getLecture(lectureId);
		if(lecture == null) {
			throw new EntityNotFoundException("lecture not found");
		}
		
		return lecture.getFeedback();
	}

	@Override
	public boolean isUserVerified(String userId, String lectureId) throws EntityNotFoundException {
		
		ILecture lecture = dataStorage.getLecture(lectureId);
		if(lecture == null) {
			throw new EntityNotFoundException("lecture not found");
		}
		
		List<IAttendance> attendances = lecture.getAttendance();
		
		Calendar calendar = Calendar.getInstance();
		
		int currYear = calendar.get(Calendar.YEAR);
		int currDay = calendar.get(Calendar.DAY_OF_YEAR);
		
		boolean verified = false;
		
		for(IAttendance attendance : attendances) {
			
			if(attendance.getUser().getId().equals(userId))
			{
				calendar.setTime(attendance.getTime());
			
				int year = calendar.get(Calendar.YEAR);
				int day = calendar.get(Calendar.DAY_OF_YEAR);
				
				if(year == currYear && day == currDay)
				{
					verified = true;
					break;
				}
			}
		}
		
		return verified;		
	}

	@Override
	public boolean isUserAdmin(String userId, String lectureId) throws EntityNotFoundException {
		ILecture lecture = dataStorage.getLecture(lectureId);
		if(lecture == null) {
			throw new EntityNotFoundException("lecture not found");
		}
		
		List<IUser> admins = lecture.getAdmins();
		
		boolean isAdmin = false;
		
		for(IUser admin : admins) {
		
			if(admin.getId().equals(userId))
			{
				isAdmin = true;
				break;
			}				
		}
		
		return isAdmin;		
	}

	@Override
	public List<IScore> getScores(String userId) {
		
		List<IApproach> approaches = dataStorage.getApproaches(userId);
		
		List<IScore> score = new LinkedList<IScore>();
		for(IApproach approach : approaches)
		{
			if(!approach.getQuestion().getQuizView().isActive())
				score.add(approach.getScore());
		}
	
		return score;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IQuizResult> getQuizResults(String id) {
		
		List<IScore> scores = getScores(id);
		
		Map<String, QuizResult> results = new HashMap<String, QuizResult>();
		for(IScore score : scores)
		{
			String quizId = score.getApproach().getQuestion().getQuizView().getId();
			if(!results.containsKey(quizId)) {
				results.put(quizId, new QuizResult());
			}
			
			results.get(quizId).addScore(score);
		}
		
		return (List<IQuizResult>)(List<?>)results.values();
		
	}
}
