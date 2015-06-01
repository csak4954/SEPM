package at.uibk.los.model;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = -1500580308679463887L;

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

}
