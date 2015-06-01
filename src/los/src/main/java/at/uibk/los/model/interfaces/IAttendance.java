package at.uibk.los.model.interfaces;

import java.util.Date;

public interface IAttendance {
	IUser getUser();
	ILecture getLecture();
	Date getTime();	
}
