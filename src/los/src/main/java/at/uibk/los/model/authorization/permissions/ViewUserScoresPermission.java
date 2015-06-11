package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ViewUserScoresPermission implements IPermission {
	
	public static final int id = 4;
	public static final String description = "retrieve scores for given user and lecture";
	public static final IPermission instance = new ViewUserScoresPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
