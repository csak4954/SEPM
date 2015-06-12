package at.uibk.los.model.exceptions;

public class QuizInactiveException extends Exception {

	private static final long serialVersionUID = -2590486881934302751L;

	public QuizInactiveException() {
		super("Quiz not active");
	}
	
}
