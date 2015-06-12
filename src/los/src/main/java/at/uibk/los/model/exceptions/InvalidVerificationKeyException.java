package at.uibk.los.model.exceptions;

public class InvalidVerificationKeyException extends Exception {

	private static final long serialVersionUID = 4935347726998110182L;

	public InvalidVerificationKeyException() {
		super("Invalid Verification Key");
	}
}
