package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ControlQuizPermission implements IPermission {
	
	public static final int id = 2;
	public static final String description = "start/stop quiz";
	public static final IPermission instance = new ControlQuizPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
