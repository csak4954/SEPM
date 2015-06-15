package at.uibk.los.viewmodel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import at.uibk.los.model.interfaces.IDay;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IStatistics;

public class StatisticsViewModel {

	private int numberOfRegistrations;
	private List<Pair<String, Double>> attendancesPerDay;
	private List<Pair<QuizViewModel, Double>> quizAverageScore;
	
	public StatisticsViewModel()
	{
		
	}
	
	public StatisticsViewModel(IStatistics statistics) 
	{
		this.setNumberOfRegistrations(statistics.getNumRegistratrions());
		this.setAttendancesPerDay(new ArrayList<Pair<String, Double>>());
		this.setQuizAverageScore(new ArrayList<Pair<QuizViewModel, Double>>());
		
		for(Entry<IDay, Double> entry : statistics.getAttendancePerDay().entrySet())
		{
			Format fmt = new SimpleDateFormat("dd-MM-yyyy");
			String dateStr = fmt.format(entry.getKey().getDate());			
			getAttendancesPerDay().add(new Pair(dateStr, entry.getValue()));
		}		
		
		for(Entry<IQuizView, Double> entry : statistics.getQuizAverageScore().entrySet())
		{
			QuizViewModel qvm = new QuizViewModel(entry.getKey());
			getQuizAverageScore().add(new Pair(qvm, entry.getValue()));
		}		
	}

	public int getNumberOfRegistrations() {
		return numberOfRegistrations;
	}

	public void setNumberOfRegistrations(int numberOfRegistrations) {
		this.numberOfRegistrations = numberOfRegistrations;
	}

	public List<Pair<String, Double>> getAttendancesPerDay() {
		return attendancesPerDay;
	}

	public void setAttendancesPerDay(List<Pair<String, Double>> attendancesPerDay) {
		this.attendancesPerDay = attendancesPerDay;
	}

	public List<Pair<QuizViewModel, Double>> getQuizAverageScore() {
		return quizAverageScore;
	}

	public void setQuizAverageScore(List<Pair<QuizViewModel, Double>> quizAverageScore) {
		this.quizAverageScore = quizAverageScore;
	}



}
