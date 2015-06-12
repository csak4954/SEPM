package at.uibk.los.model.exceptions;

public class VerificationInactiveException extends Exception {

	private static final long serialVersionUID = 4093170945266589983L;

	public VerificationInactiveException() {
		super("Attendance verification not active");
	}
}
