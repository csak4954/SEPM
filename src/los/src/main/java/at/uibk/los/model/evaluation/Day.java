package at.uibk.los.model.evaluation;

import java.util.Calendar;
import java.util.Date;

import at.uibk.los.model.interfaces.IDay;

public class Day implements IDay {

	int dayOfYear;
	int year;
	Date date;
	
	public Day(Date when) {
		Calendar.getInstance().setTime(when);
		dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		year = Calendar.getInstance().get(Calendar.YEAR);
		date = when;
	}
	
	public int compareTo(IDay o) {
		
		if(o.getClass() != Day.class)
			throw new IllegalStateException();
		
		Day other = (Day)o;
		
		if(this != other) 
		{	
			if(year < other.year) {
				return -1;
			}
			else if(year > other.year) {
				return 1;
			}
			else 
			{
				
				if(dayOfYear < other.dayOfYear) {
					return -1;
				}
				else if(dayOfYear > other.dayOfYear) {
					return 1;
				}
			}
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(this == o) return true;
		
		if(o.getClass() != this.getClass()) return false;
		
		Day other = (Day)o;
		
		return (dayOfYear == other.dayOfYear && year == other.year);		
	}
	
	@Override
	public int hashCode() {
		int hashcode = 17;
		hashcode = hashcode * 31 + year;
		hashcode = hashcode * 31 + dayOfYear;
		return hashcode;
	}
	
	public Date getDate() {
		return date;
	}
}
