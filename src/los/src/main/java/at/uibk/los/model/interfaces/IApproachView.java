package at.uibk.los.model.interfaces;

import java.util.List;

public interface IApproachView {

	public abstract IQuestion getQuestion();

	public abstract List<IAnswer> getAnswers();
}
