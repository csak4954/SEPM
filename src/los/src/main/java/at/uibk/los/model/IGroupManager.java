package at.uibk.los.model;

public interface IGroupManager
{
	void verify(IObject object, IPermission permission) throws LOSAccessDeniedException;
}
