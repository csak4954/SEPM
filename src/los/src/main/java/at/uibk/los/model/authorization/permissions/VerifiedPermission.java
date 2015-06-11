package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class VerifiedPermission implements IPermission {
	
	public static final int id = 9;
	public static final String description = "verified for lecture";
	public static final IPermission instance = new VerifiedPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
