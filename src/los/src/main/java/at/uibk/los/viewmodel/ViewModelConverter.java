package at.uibk.los.viewmodel;

import java.util.LinkedList;
import java.util.List;

public class ViewModelConverter {

	public interface Instantiator<T, U> {
		public T create(U data);
	}
	
	public interface Predicate<U> {
		public boolean isValid(U o);
	}
	
	public static <T, U> List<T> convert(List<U> src, Instantiator<T, U> instantiator)
	{
		List<T> vm = new LinkedList<T>();
		
		for(U o : src) {
			vm.add(instantiator.create(o));
		}
		
		return vm;
	}
	
	public static <T, U> List<T> convert(List<U> src, Instantiator<T, U> instantiator, Predicate<U> predicate)
	{
		List<T> vm = new LinkedList<T>();
		
		for(U o : src) {
			if(predicate.isValid(o)) {
				vm.add(instantiator.create(o));
			}
		}
		
		return vm;
	}
	
}
