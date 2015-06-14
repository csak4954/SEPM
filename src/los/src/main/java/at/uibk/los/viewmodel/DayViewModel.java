package at.uibk.los.viewmodel;

import java.util.Date;

import at.uibk.los.model.interfaces.IDay;

public class DayViewModel implements Comparable<DayViewModel> {

	private IDay day;
	
	public DayViewModel(IDay day) {
		this.day = day;
	}
	
	public Date getDate() {
		return day.getDate();
	}
	
	@Override
	public int compareTo(DayViewModel o) {
		return this.day.compareTo(o.day);
	}	
}
