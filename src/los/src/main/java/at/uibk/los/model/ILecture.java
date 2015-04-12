package at.uibk.los.model;

public interface ILecture
{
	int getId();
	void setId(int id);
	
	String getTitle();
	void setTitle(String title);
	
	String getDescription();
	void setDescription(String description);
	
	String getVerificationKey();
	void startAttendanceVerification();
	void endAttendanceVerification();
	boolean isAttendanceVerificationActive();
	
	void addAttendee(int userId);
	void removeAttendee(int userId);
	Iterable<Integer> getAttendees();
	
	void addAdmin(int userId);
	void removeAdmin(int userId);
	Iterable<Integer> getAdmins();
	
	void addQuiz(IQuiz quiz);
	void removeQuiz(int quizId);
	Iterable<IQuiz> getQuiz();
}
