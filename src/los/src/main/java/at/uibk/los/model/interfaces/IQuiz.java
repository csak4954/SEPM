package at.uibk.los.model.interfaces;

import java.util.List;

public interface IQuiz extends IQuizView
{
	void setTitle(String title);
	
	void start();
	void stop();
	
	List<IQuestion> getQuestions();
	void RemoveQuestion(String questionId);
	IQuestion getQuestion(String questionId);
	IQuestion addQuestion(String text); 
}
