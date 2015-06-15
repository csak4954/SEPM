package at.uibk.los.model.authorization;

import java.util.HashMap;
import java.util.Map;

import at.uibk.los.model.authorization.permissions.AttendanceVerificationPermission;
import at.uibk.los.model.authorization.permissions.ControlQuizPermission;
import at.uibk.los.model.authorization.permissions.DefaultPermission;
import at.uibk.los.model.authorization.permissions.ModifyAdminCollectionPermission;
import at.uibk.los.model.authorization.permissions.ModifyLectureCollectionPermission;
import at.uibk.los.model.authorization.permissions.ModifyQuizCollectionPermission;
import at.uibk.los.model.authorization.permissions.ViewFeedbackPermission;
import at.uibk.los.model.authorization.permissions.ViewStatisticsPermission;
import at.uibk.los.model.authorization.permissions.ViewUserScoresPermission;
import at.uibk.los.model.interfaces.IGroupPolicy;
import at.uibk.los.model.interfaces.IObject;
import at.uibk.los.model.interfaces.IPermission;
import at.uibk.los.model.interfaces.IPolicyManager;

public class PolicyManager implements IPolicyManager {
		
	public PolicyManager() {
		
		groups = new HashMap<Integer, IGroupPolicy>();
		
		
		IGroupPolicy admin = new StaffGroupPolicy();
		admin.grantPermission(ModifyLectureCollectionPermission.instance);
		admin.grantPermission(ModifyQuizCollectionPermission.instance);
		admin.grantPermission(ModifyAdminCollectionPermission.instance);
		admin.grantPermission(ControlQuizPermission.instance);
		admin.grantPermission(AttendanceVerificationPermission.instance);
		admin.grantPermission(ViewFeedbackPermission.instance);
		admin.grantPermission(ViewStatisticsPermission.instance);
		admin.grantPermission(ViewUserScoresPermission.instance);
		
		groups.put(admin.getId(), admin);
		
		IGroupPolicy staff = new StaffGroupPolicy();
		staff.grantPermission(ModifyLectureCollectionPermission.instance);
		staff.grantPermission(ModifyQuizCollectionPermission.instance);
		staff.grantPermission(ModifyAdminCollectionPermission.instance);
		staff.grantPermission(ControlQuizPermission.instance);
		staff.grantPermission(AttendanceVerificationPermission.instance);
		staff.grantPermission(ViewFeedbackPermission.instance);
		staff.grantPermission(ViewStatisticsPermission.instance);
		staff.grantPermission(ViewUserScoresPermission.instance);
		
		groups.put(staff.getId(), staff);
		
		IGroupPolicy students = new StudentGroupPolicy();
		
		groups.put(students.getId(), students);
	}
	
	@Override
	public void verify(IObject object, IPermission... permissions)
			throws LOSAccessDeniedException {
		
		if(object == null) {
			throw new LOSAccessDeniedException(DefaultPermission.instance);
		}
		
		if(permissions != null) {
		
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
	}

	private Map<Integer, IGroupPolicy> groups;
}
