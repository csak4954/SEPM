package at.uibk.los.viewmodel;

//import java.util.List;

import at.uibk.los.model.interfaces.IQuiz;
import at.uibk.los.model.interfaces.IQuestion;

public class QuizViewModel
{
	private int id;
	private Iterable<IQuestion> questions ;
	
	public QuizViewModel(IQuiz quiz){
        this.id = quiz.getId();
        this.questions = quiz.getQuestions();
	}
	
	public QuizViewModel(){}  // needed by jackson to rebuild from json
	
	// getter needed by jackson to build json representation

	public int getQuizId() {
		return this.id;
	}
	public Iterable<IQuestion> getQuestions(){
		return this.questions;
	}
	
}