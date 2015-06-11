package at.uibk.los.viewmodel;

import at.uibk.los.model.interfaces.IAnswerView;

public class AnswerViewModel {

	private String id;
	private String text;
	private boolean solution;
	
	public AnswerViewModel(IAnswerView answer) {
		this.id = answer.getId();
		this.text = answer.getText();
		this.solution = false;
	}
	
	public AnswerViewModel() { }
	
	public String getId()  {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public boolean isSolution() {
		return solution;
	}
}
