package at.uibk.los.model.interfaces;

import java.util.List;

public interface ILectureView {

	String getId();
	
	String getTitle();
	
	String getDescription();
	
	String getVerificationKey();
	
	List<IAttendance> getAttendance();
	
	List<IUser> getAdmins();
	
	List<IQuizView> getQuizView();
	
	IQuizView getQuizView(String quizId);
}
