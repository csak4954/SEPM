package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ModifyQuizCollectionPermission implements IPermission {
	
	public static final int id = 3;
	public static final String description = "add/remove quiz";
	public static final IPermission instance = new ModifyQuizCollectionPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
