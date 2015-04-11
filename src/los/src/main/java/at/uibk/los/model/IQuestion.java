package at.uibk.los.model;

public interface IQuestion {

	int getId();
	
	void setText(String text);
	String getText();
	
	Iterable<IAnswer> getAnswers();
	IQuestion addAnswer(String text, boolean solution);
}
