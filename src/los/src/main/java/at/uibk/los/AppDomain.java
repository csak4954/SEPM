package at.uibk.los;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import at.uibk.los.login.ExternalUser;
import at.uibk.los.login.LoginProvider;
import at.uibk.los.model.Model;

public class AppDomain extends Model
{	
	private static ServiceProvider tmp = null;
	private AppDomain()
	{
		super(tmp = new ServiceProvider());
		provider = tmp;
	}
	
	private ServiceProvider provider;
	public ServiceProvider getServiceProvider() {
		return provider;
	}
	
	private static AppDomain instance = null;
	public static synchronized AppDomain get()
	{
		if(instance == null) 
		{	
			instance = new AppDomain();
			
			// add users 
			LoginProvider loginProvider = instance.getServiceProvider().getLoginProvider();
			try
			{
				loginProvider.addUser(new ExternalUser("Florian", "Tischler", "csaq5126", generateHash("secret"), "csaq5126@uibk.ac.at", "student"));
				loginProvider.addUser(new ExternalUser("Mathias", "Hoelzl", "csaq5244", generateHash("secret"), "csaq5244@uibk.ac.at", "student"));

				loginProvider.addUser(new ExternalUser("Christian", "Sillaber", "12345", generateHash("secret"), "christian.sillaber@uibk.ac.at", "staff"));
				loginProvider.addUser(new ExternalUser("Philipp", "Kalb", "23456", generateHash("secret"), "philipp.kalb@uibk.ac.at", "staff"));
				
				loginProvider.addUser(new ExternalUser("admin", "admin", "admin", generateHash("admin"), "admin@uibk.ac.at", "admin"));	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
		}
		
		return instance;
	}
	
	public static String generateHash(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException 
	{
	    MessageDigest md = null;
	    byte[] hash = null;
        md = MessageDigest.getInstance("SHA-512");
        hash = md.digest(pwd.getBytes("UTF-8"));
	     
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}
}