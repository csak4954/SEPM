package at.uibk.los.model.mocks;

import java.util.List;

import at.uibk.los.model.IQuestion;
import at.uibk.los.model.IQuiz;

public class QuizMock implements IQuiz {

	private int id;
	private String title;
	private List<IQuestion> questions;

	@Override
	public Iterable<IQuestion> getQuestions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQuestion addQuestion(String text) {
	
		IQuestion question = new QuestionMock(questions.size());
		question.setText(text);
		questions.add(question);
		
		return question;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;		
	}

	@Override
	public String getTitle() {
		return title;
	}

}
