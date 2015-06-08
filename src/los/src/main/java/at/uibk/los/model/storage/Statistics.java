package at.uibk.los.model.storage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import at.uibk.los.model.NotImplementedException;
import at.uibk.los.model.interfaces.IAttendance;
import at.uibk.los.model.interfaces.ILecture;
import at.uibk.los.model.interfaces.ILectureView;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IStatistics;
import at.uibk.los.model.interfaces.IUser;

public class Statistics implements IStatistics {

	private ILecture lecture;
	
	private int registeredUsers;
	private Map<Date, Double> attendancesPerDay;
	private Map<IQuizView, Double> averageQuizPoints;
	
	private void generate() 
	{
		List<IAttendance> attendances = lecture.getAttendance();
		List<IUser> users = lecture.getRegisteredUsers();
		
		registeredUsers = users.size();
		
		for(IAttendance att : attendances) {
			
			Double val = attendancesPerDay.get(att.getTime());
			
			if(val == null) {
				val = 0.0;
			}

			attendancesPerDay.put(att.getTime(), ++val);
		}
		
		for(Entry<Date, Double> entry : attendancesPerDay.entrySet()){
			attendancesPerDay.put(entry.getKey(), entry.getValue() / this.registeredUsers * 100);
		}
		
		List<IQuizView> quizList = lecture.getQuizView();
		
		for(IQuizView quiz : quizList)
		{
			for(IUser user : users)
			{

			}	
		}	

	}
	
	public Statistics(ILecture lecture) {
		this.lecture = lecture;
		this.attendancesPerDay = new HashMap<Date, Double>();
		this.averageQuizPoints = new HashMap<IQuizView, Double>();
		generate();
	}
		
	@Override
	public Map<Date, Double> getAttendancePerDay() {
		return attendancesPerDay;
	}

	@Override
	public Map<IQuizView, Double> getQuizAverageScore() {
		throw new NotImplementedException();
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
