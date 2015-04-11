package at.uibk.los.model.mocks;

import java.util.LinkedList;
import java.util.List;

import at.uibk.los.model.IAnswer;
import at.uibk.los.model.IQuestion;

public class QuestionMock implements IQuestion {

	private int id;
	private String text;
	private List<IAnswer> answers;
	
	public QuestionMock(int id) {
		this.id = id;
		answers = new LinkedList<IAnswer>();
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Iterable<IAnswer> getAnswers() {
		// TODO Auto-generated method stub
		return answers;
	}

	@Override
	public IQuestion addAnswer(String text, boolean solution) {
		IAnswer answer = new AnswerMock(answers.size(), text, solution);
		answers.add(answer);
		return this;
	}

}
