package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class AttendanceVerificationPermission implements IPermission {
	
	public static final int id = 5;
	public static final String description = "control attendance verification";
	public static final IPermission instance = new AttendanceVerificationPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
