package at.uibk.los.viewmodel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import at.uibk.los.model.interfaces.IDay;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IStatistics;

public class StatisticsViewModel {

	private int numberOfRegistrations;
	private Map<String, Double> attendancesPerDay;
	private Map<String, Double> quizAverageScore;
	private Map<String, DayViewModel> dayMap;
	private Map<String, QuizViewModel> quizMap;
	
	public StatisticsViewModel(IStatistics statistics) 
	{
		this.numberOfRegistrations = statistics.getNumRegistratrions();
		
		this.dayMap = new HashMap<String, DayViewModel>();
		this.attendancesPerDay = new HashMap<String, Double>();
		for(Entry<IDay, Double> entry : statistics.getAttendancePerDay().entrySet())
		{
			DayViewModel dvm = new DayViewModel(entry.getKey());
			
			attendancesPerDay.put(dvm.toString(), entry.getValue());
			dayMap.put(dvm.toString(), dvm);
		}		
		
		this.quizMap = new HashMap<String, QuizViewModel>();
		this.quizAverageScore = new HashMap<String, Double>();
		for(Entry<IQuizView, Double> entry : statistics.getQuizAverageScore().entrySet())
		{
			QuizViewModel qvm = new QuizViewModel(entry.getKey());
			
			quizAverageScore.put(qvm.toString(), entry.getValue());
			quizMap.put(qvm.toString(), qvm);
		}		
	}
	
	public int getNumberOfRegistrations() { 
		return numberOfRegistrations;
	}
	
	public Map<String, Double> getAttendancesPerDay() {
		return attendancesPerDay;
	}
	
	public Map<String, DayViewModel> getDayMap() {
		return dayMap;
	}
	
	public Map<String, Double> getQuizAverageScore() {
		return quizAverageScore;
	}
	
	public Map<String, QuizViewModel> getQuizMap() {
		return quizMap;
	}
}
