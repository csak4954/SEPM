package at.uibk.los.model;

public interface IGroupPolicy
{
	int getId();
	void grantPermission(IPermission permission);
	void revokePermission(IPermission permission);
	boolean checkPermission(IPermission permission);
}
