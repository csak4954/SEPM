package at.uibk.los.model.authorization;

import at.uibk.los.model.interfaces.IPermission;

public class LOSAccessDeniedException extends Exception
{
	private static final long serialVersionUID = -6728961652585788388L;
	private IPermission permission;
	
	public LOSAccessDeniedException(IPermission cause)
	{
		this.permission = cause;
	}
	
	public LOSAccessDeniedException()
	{
		this.permission = null;
	}
	
	public IPermission getPermission() {
		return permission;
	}
}
