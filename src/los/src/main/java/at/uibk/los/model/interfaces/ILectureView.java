package at.uibk.los.model.interfaces;

import java.util.List;

public interface ILectureView extends IIdentifiable {
	
	String getTitle();
	
	String getDescription();
	
	List<IAttendance> getAttendance();
	
	List<IUser> getAdmins();
	
	List<IQuizView> getQuizView();
	
	IQuizView getQuizView(String quizId);
}
