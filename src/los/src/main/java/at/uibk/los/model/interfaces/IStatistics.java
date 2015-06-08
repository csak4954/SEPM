package at.uibk.los.model.interfaces;

import java.util.Date;
import java.util.Map;

public interface IStatistics
{
	Map<Date, Double> getAttendancePerDay();
	
	Map<IQuizView, Double> getQuizAverageScore();
	
	int getNumRegistratrions();
	
	ILectureView getLectureView();
}
