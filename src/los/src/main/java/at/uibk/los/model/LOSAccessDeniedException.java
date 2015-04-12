package at.uibk.los.model;

public class LOSAccessDeniedException extends Exception
{
	private static final long serialVersionUID = -6728961652585788388L;
	private IPermission permission;
	
	public LOSAccessDeniedException(IPermission cause)
	{
		// TODO Auto-generated constructor stub
	}
	
	public IPermission getPermission() {
		return permission;
	}
}