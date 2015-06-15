package at.uibk.los.model.storage;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;

import at.uibk.los.model.interfaces.IDay;

class Day implements IDay {

	int dayOfYear;
	int year;
	
	@Transient
	Date date;
	
	@PersistenceConstructor
	public Day(int dayOfYear, int year) {

		this.dayOfYear = dayOfYear;
		this.year = year;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		
		this.date = cal.getTime();
	}
	
	public Day() {
		Date when = new Date();
		Calendar.getInstance().setTime(when);
		dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		year = Calendar.getInstance().get(Calendar.YEAR);
		date = when;
	}
	
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
