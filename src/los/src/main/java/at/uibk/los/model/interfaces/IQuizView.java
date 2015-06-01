package at.uibk.los.model.interfaces;

import java.util.List;

public interface IQuizView {
	
	String getId();
	
	String getTitle();

	boolean isActive();
	
	List<IQuestionView> getQuestionView();
	
	IQuestionView getQuestionView(String questionId);

}
