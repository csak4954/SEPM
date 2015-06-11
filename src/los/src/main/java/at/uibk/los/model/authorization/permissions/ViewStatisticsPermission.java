package at.uibk.los.model.authorization.permissions;

import at.uibk.los.model.interfaces.IPermission;

public class ViewStatisticsPermission implements IPermission {
	
	public static final int id = 6;
	public static final String description = "view lecture statistics";
	public static final IPermission instance = new ViewStatisticsPermission();
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
