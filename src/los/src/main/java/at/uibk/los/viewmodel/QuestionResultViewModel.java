package at.uibk.los.viewmodel;

import at.uibk.los.model.interfaces.IScore;

public class QuestionResultViewModel {

	private double score;
	
	public QuestionResultViewModel(IScore score) {
		this.score = score.getScore();
	}
	
	public double getScore() {
		return score;
	}
}
