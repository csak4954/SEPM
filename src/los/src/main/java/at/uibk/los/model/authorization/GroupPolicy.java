package at.uibk.los.model.authorization;

import java.util.HashMap;
import java.util.Map;

import at.uibk.los.model.interfaces.IGroupPolicy;
import at.uibk.los.model.interfaces.IPermission;

public abstract class GroupPolicy implements IGroupPolicy {

	public GroupPolicy() {
		permissions = new HashMap<Integer, IPermission>();
	}
	
	@Override
	public void grantPermission(IPermission permission) {
		permissions.put(permission.getId(), permission);
	}

	@Override
	public void revokePermission(IPermission permission) {
		permissions.remove(permission.getId());		
	}

	@Override
	public boolean checkPermission(IPermission permission) {
		return permissions.containsKey(permission.getId());
	}
	
	private Map<Integer, IPermission> permissions;
}
