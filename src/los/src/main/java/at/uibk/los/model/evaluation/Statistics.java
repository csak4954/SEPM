package at.uibk.los.model.evaluation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import at.uibk.los.model.interfaces.IAttendance;
import at.uibk.los.model.interfaces.IDataEvaluation;
import at.uibk.los.model.interfaces.IDay;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IQuizResult;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IStatistics;
import at.uibk.los.model.interfaces.IUser;

class Statistics implements IStatistics {

	private ILecture lecture;
	
	private int registeredUsers;
	private Map<IDay, Double> attendancesPerDay;
	private Map<IQuizView, Double> averageQuizPoints;

	private IDataEvaluation evaluation;
	
	private void generate() 
	{
		List<IAttendance> attendances = lecture.getAttendance();
		List<IUser> users = lecture.getRegisteredUsers();
		
		registeredUsers = users.size();
		
		for(IAttendance att : attendances) {
					
			Day day = new Day(att.getTime());
			
			Double val = attendancesPerDay.get(day);
			
			if(val == null) {
				val = 0.0;
			}

			attendancesPerDay.put(day, ++val);
		}
		
		for(Entry<IDay, Double> entry : attendancesPerDay.entrySet()){
			attendancesPerDay.put(entry.getKey(), entry.getValue() / this.registeredUsers * 100);
		}
		
		List<IQuizView> quizList = lecture.getQuizView();
		
		for(IQuizView quiz : quizList)
		{
			int quizAttempts = 0;
			
			for(IUser user : users)
			{
				List<IQuizResult> results = evaluation.getQuizResults(user.getId());
				
				for (IQuizResult result : results) {
					
					if(result.getQuizView().equals(quiz)) {
						
						Double val = averageQuizPoints.get(quiz);
						
						if(val == null) {
							val = 0.0;
						}
						
						val += result.getScore();

						averageQuizPoints.put(quiz, val);
						
						quizAttempts++;
					}
				}
			}	
			
			if(quizAttempts > 0)
			{
				Double val = averageQuizPoints.get(quiz);
				averageQuizPoints.put(quiz, val / quizAttempts);
			}
		}	
	}
	
	public Statistics(ILecture lecture, IDataEvaluation evaluation) {
		this.lecture = lecture;
		this.evaluation = evaluation;
		this.attendancesPerDay = new HashMap<IDay, Double>();
		this.averageQuizPoints = new HashMap<IQuizView, Double>();
		generate();
	}
		
	@Override
	public Map<IDay, Double> getAttendancePerDay() {
		return attendancesPerDay;
	}

	@Override
	public Map<IQuizView, Double> getQuizAverageScore() {
		return averageQuizPoints;
	}

	@Override
	public int getNumRegistratrions() {

		return registeredUsers;
	}

	@Override
	public ILectureView getLectureView() {
		return lecture;
	}

}
