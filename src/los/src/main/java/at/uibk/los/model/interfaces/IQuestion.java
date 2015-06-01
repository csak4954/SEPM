package at.uibk.los.model.interfaces;

import java.util.List;

public interface IQuestion extends IQuestionView {

	void setText(String text);
	
	List<IAnswer> getAnswers();
	
	void removeAnswer(String answerId);
	
	IQuestion addAnswer(String text, boolean solution);
	
	void addApproach(String userId, List<String> answers);
}
