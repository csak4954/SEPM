package at.uibk.los.model.interfaces;

import java.util.List;

public interface IQuestionView extends IIdentifiable {
	
	String getText();
	
	List<IAnswerView> getAnswerView();
	
	IQuizView getQuizView();
}
