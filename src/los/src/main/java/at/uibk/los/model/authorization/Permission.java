package at.uibk.los.model.authorization;

import at.uibk.los.model.interfaces.IPermission;

public class Permission implements IPermission {
	
	public static final int id = 0;
	public static final String description = "Permission";
	public static final IPermission instance = new Permission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
