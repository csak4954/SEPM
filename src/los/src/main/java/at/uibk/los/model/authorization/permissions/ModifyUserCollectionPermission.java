package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ModifyUserCollectionPermission implements IPermission {
	
	public static final int id = 10;
	public static final String description = "modify user collection";
	public static final IPermission instance = new ModifyUserCollectionPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
