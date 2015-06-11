package at.uibk.los.model.storage;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import at.uibk.los.model.interfaces.IAttendance;

class Attendance implements IAttendance {

	@Id
	private ObjectId id;
	
	@DBRef
	private User user;
	
	@DBRef
	private Lecture lecture;
	
	private Date time;
	
	public Attendance(User user, Lecture lecture) {
		this.id = ObjectId.get();
		this.user = user;
		this.lecture = lecture;
		this.time = new Date();
	}
	
	public Lecture getLecture() {
		return lecture;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(this.getClass() != o.getClass()) return false;
		
		Attendance other = (Attendance)o;
		
		return this.id.equals(other.id);
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	static AttendanceRepository Repo;

	@Override
	public Date getTime() {
		return time;
	}
}
