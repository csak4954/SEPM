package at.uibk.los.model.evaluation;

import at.uibk.los.model.interfaces.IAnswer;
import at.uibk.los.model.interfaces.IApproach;
import at.uibk.los.model.interfaces.IScore;

class Score implements IScore {
	
	private IApproach approach;
	private double score;
	
	public Score(IApproach approach)
	{
		this.approach = approach;
		
		int points = 0;
		for(IAnswer answer : approach.getAnswers()) {
			if(answer.isSolution()) {
				points += answer.getRightPointCount();
			}
			else {
				points -= answer.getWrongPointCount();
			}
		}
		
		if(points < 0) points = 0;
		
		int maxPoints = 0;
		for(IAnswer answer : approach.getQuestion().getAnswers())
		{
			if(answer.isSolution()) {
				maxPoints += answer.getRightPointCount();
			}
		}
		
		this.score = ((double)points / maxPoints) * 100;
	}
	
	@Override
	public double getScore() {
		return score;
	}

	public IApproach getApproach() {
		return approach;
	}
}
