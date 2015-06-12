package at.uibk.los.model.evaluation;

import java.util.LinkedList;
import java.util.List;

import at.uibk.los.model.interfaces.IQuizResult;
import at.uibk.los.model.interfaces.IQuizView;
import at.uibk.los.model.interfaces.IScore;

class QuizResult implements IQuizResult {

	private List<IScore> scores;
	
	public QuizResult() {
		this.scores = new LinkedList<IScore>();
	}
	
	public void addScore(IScore score) {
		scores.add(score);
	}
	
	@Override
	public double getScore() {
		
		double score = 0;
		
		if(!scores.isEmpty())
		{
			for (IScore s : scores) {
				score += s.getScore();
			}
			
			score /= getQuizView().getQuestionView().size();
		}
		
		return score;
	}

	@Override
	public IQuizView getQuizView() {
		
		IQuizView quiz = null;
		if(!scores.isEmpty())
		{
			quiz = scores.get(0).getApproach().getQuestion().getQuizView();
		}
		
		return quiz;
	}

	@Override
	public List<IScore> getQuestionScores() {
		return scores;
	}
	
}
