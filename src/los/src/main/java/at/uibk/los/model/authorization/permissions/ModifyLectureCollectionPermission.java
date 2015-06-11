package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ModifyLectureCollectionPermission implements IPermission {
	
	public static final int id = 1;
	public static final String description = "add/remove lectures";
	public static final IPermission instance = new ModifyLectureCollectionPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
