package at.uibk.los.viewmodel;

import java.util.LinkedList;
import java.util.List;

import at.uibk.los.model.interfaces.IAnswerView;
import at.uibk.los.model.interfaces.IQuestionView;

public class QuestionViewModel {

	private String id;
	private String text;
	private List<AnswerViewModel> answers;
	
	public QuestionViewModel(IQuestionView question) {
		
		id = question.getId();
		text = question.getText();
		
		answers = new LinkedList<AnswerViewModel>();
		for(IAnswerView answer : question.getAnswerView()) {
			answers.add(new AnswerViewModel(answer));			
		}
	}
	
	public QuestionViewModel() { }
	
	public String getId() {
		return id;
	}

	public String getText() {
		return text;
	}
	
	public List<AnswerViewModel> getAnswers() {
		return answers;
	}
}

