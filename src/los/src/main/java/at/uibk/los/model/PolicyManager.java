package at.uibk.los.model;

import java.util.HashMap;
import java.util.Map;

import at.uibk.los.model.interfaces.IGroupPolicy;
import at.uibk.los.model.interfaces.IObject;
import at.uibk.los.model.interfaces.IPermission;
import at.uibk.los.model.interfaces.IPolicyManager;

public class PolicyManager implements IPolicyManager {
		
	public PolicyManager() {
		
		groups = new HashMap<Integer, IGroupPolicy>();
		
		IGroupPolicy staff = new StaffGroupPolicy();
		staff.grantPermission(Permission.instance);
		staff.grantPermission(ModifyLectureCollectionPermission.instance);
		
		groups.put(staff.getId(), staff);
		
		IGroupPolicy students = new StudentGroupPolicy();
		students.grantPermission(Permission.instance);
		
		groups.put(students.getId(), students);
	}
	
	@Override
	public void verify(IObject object, IPermission... permissions)
			throws LOSAccessDeniedException {
		
		if( object == null || permissions == null) {
			throw new IllegalArgumentException();
		}
		
		IGroupPolicy group = groups.get(object.getGroupPolicy());
		
		if( group == null ) {
			throw new IllegalArgumentException();
		}

		for(IPermission permission : permissions) {
			if (!group.checkPermission(permission)) {
				throw new LOSAccessDeniedException(permission);
			}
		}
	}

	private Map<Integer, IGroupPolicy> groups;
}
