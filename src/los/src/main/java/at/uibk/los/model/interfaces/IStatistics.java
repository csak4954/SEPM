package at.uibk.los.model.interfaces;

import java.util.Map;

public interface IStatistics
{
	Map<IDay, Double> getAttendancePerDay();
	
	Map<IQuizView, Double> getQuizAverageScore();
	
	int getNumRegistratrions();
	
	ILectureView getLectureView();
}
