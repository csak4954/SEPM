package at.uibk.los.model.interfaces;

import at.uibk.los.model.authorization.LOSAccessDeniedException;

public interface IPolicyManager
{
	void verify(IObject object, IPermission... permissions) throws LOSAccessDeniedException;
}
