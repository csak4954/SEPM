package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ModifyAdminCollectionPermission implements IPermission {
	
	public static final int id = 8;
	public static final String description = "add/remove admins";
	public static final IPermission instance = new ModifyAdminCollectionPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
