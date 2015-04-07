package model;

public interface IModel 
{
	String logInUser();
	String logOffUsser();	
	String getLectures();
	String addLecture();	
	String removeLecture();
	String startAttendanceVerification();
	String endAttendanceVErification();
	String getVerificationKey();	
	String startQuiz();	
	String endQuiz();
	String getStatistices();
	String getStutentResults();
	String getFeedback();
	String confirmAttendance();
	String getQuiz();
	String submitAnswer();
	String getSolution();
	String getPerformance();
}
