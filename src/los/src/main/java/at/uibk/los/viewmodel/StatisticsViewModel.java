package at.uibk.los.viewmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import at.uibk.los.model.interfaces.IDay;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IStatistics;

public class StatisticsViewModel {

	private int numberOfRegistrations;
	private Map<IDay, Double> attendancesPerDay;
	private Map<QuizViewModel, Double> quizAverageScore;
	
	public StatisticsViewModel(IStatistics statistics) 
	{
		this.numberOfRegistrations = statistics.getNumRegistratrions();
		this.attendancesPerDay = statistics.getAttendancePerDay();
		
		this.quizAverageScore = new HashMap<QuizViewModel, Double>();
		for(Entry<IQuizView, Double> entry : statistics.getQuizAverageScore().entrySet())
		{
			quizAverageScore.put(new QuizViewModel(entry.getKey()), entry.getValue());
		}		
	}
	
	public int getNumberOfRegistrations() {
		return numberOfRegistrations;
	}
	
	public Map<IDay, Double> getAttendancesPerDay() {
		return attendancesPerDay;
	}
	
	public Map<QuizViewModel, Double> getQuizAverageScore() {
		return quizAverageScore;
	}
}
