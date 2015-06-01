package at.uibk.los;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware
{
	private ApplicationContext ctx;
	private static ApplicationContextProvider instance = null;
	
	public ApplicationContextProvider() {
		ctx = null;
		instance = this;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException { 
		ctx = arg0;
	}
	
	public static ApplicationContext getContext() {
		return instance.ctx;
	}
}