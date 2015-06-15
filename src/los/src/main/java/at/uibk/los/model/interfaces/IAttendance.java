package at.uibk.los.model.interfaces;

public interface IAttendance {
	IUser getUser();
	ILecture getLecture();
	IDay getDay();	
}
