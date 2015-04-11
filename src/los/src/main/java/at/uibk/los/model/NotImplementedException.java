package at.uibk.los.model;

public class NotImplementedException extends RuntimeException
{
	private static final long serialVersionUID = 703537653082377072L;

	public NotImplementedException() {
		super("method not implemented");
	}
}
