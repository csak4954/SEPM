package at.uibk.los.model.mocks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import at.uibk.los.model.IQuestion;
import at.uibk.los.model.IQuiz;

public class QuizMock implements IQuiz {

	private int id;
	private String title;
	private List<IQuestion> questions;
	private Map<Integer, Map<Integer, Map<Integer, List<Integer>>>> approaches;

	public QuizMock() {
		approaches = new HashMap<Integer, Map<Integer, Map<Integer, List<Integer>>>>();
		questions = new LinkedList<IQuestion>();
	}
	
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

	@Override
	public void addApproach(int userId, int questionId, int[] answers) {
		Map<Integer, Map<Integer, List<Integer>>> quizMap = approaches.get(userId);
		if(quizMap == null) {
			approaches.put(userId, new HashMap<Integer, Map<Integer, List<Integer>>>());
		}
		
		Map<Integer, List<Integer>> questionMap = quizMap.get(id);
		if(questionMap == null) {
			quizMap.put(id, new HashMap<Integer, List<Integer>>());
		}
	
		List<Integer> answerList = questionMap.get(questionId);
		if(answerList == null) {
			questionMap.put(id, new LinkedList<Integer>());
		}
		
		for(int answer : answers)
			answerList.add(answer);
	}

}
