package at.uibk.los.model;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = -1500580308679463887L;

	public EntityNotFoundException(String id) {
		super("entity not found (id=" + id + ")");
	}
}
