package at.uibk.los.model.interfaces;

import java.util.List;

public interface IApproach {

	public abstract IQuestion getQuestion();

	public abstract List<IAnswer> getAnswers();

	public abstract IScore getScore();
}
