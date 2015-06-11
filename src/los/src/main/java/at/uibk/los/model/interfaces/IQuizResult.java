package at.uibk.los.model.interfaces;

import java.util.List;

public interface IQuizResult {

	double getScore();
	
	IQuizView getQuizView();
	
	List<IScore> getQuestionScores();
}
