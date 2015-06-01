package at.uibk.los.viewmodel;

//import java.util.List;

import at.uibk.los.model.interfaces.IQuestionView;
import at.uibk.los.model.interfaces.IQuizView;

public class QuizViewModel
{
	private String id;
	private Iterable<IQuestionView> questions ;
	
	public QuizViewModel(IQuizView quiz){
        this.id = quiz.getId();
        this.questions = quiz.getQuestionView();
	}
	
	public QuizViewModel(){}  // needed by jackson to rebuild from json
	
	// getter needed by jackson to build json representation

	public String getQuizId() {
		return this.id;
	}
	public Iterable<IQuestionView> getQuestions(){
		return this.questions;
	}
	
}