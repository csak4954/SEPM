package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ViewFeedbackPermission implements IPermission {
	
	public static final int id = 7;
	public static final String description = "view lecture feedback";
	public static final IPermission instance = new ViewFeedbackPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
