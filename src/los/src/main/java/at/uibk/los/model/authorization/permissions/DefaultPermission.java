package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class DefaultPermission implements IPermission {
	
	public static final int id = 0;
	public static final String description = "being logged in";
	public static final IPermission instance = new DefaultPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
