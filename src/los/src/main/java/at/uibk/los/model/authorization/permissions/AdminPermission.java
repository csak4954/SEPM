package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class AdminPermission implements IPermission {
	
	public static final int id = 8;
	public static final String description = "admin rights for lecture";
	public static final IPermission instance = new AdminPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
