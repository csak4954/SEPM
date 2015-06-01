package at.uibk.los.model.interfaces;

import java.util.List;

public interface IQuestionView {

	String getId();
	
	String getText();
	
	List<IAnswerView> getAnswerView();
}
