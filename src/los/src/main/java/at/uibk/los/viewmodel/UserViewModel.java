package at.uibk.los.viewmodel;

import java.util.List;

import at.uibk.los.model.interfaces.IUser;
import at.uibk.los.viewmodel.ViewModelConverter.Instantiator;


public class UserViewModel
{
	private String id;
	private String name;
	private String surname;
	private String email;
	private String affiliation;
	
	public UserViewModel(IUser user){
		this.id = user.getId();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.email = user.getEmail();
        this.affiliation = user.getAffilation();  
	}
	
	public UserViewModel() { } 

	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getAffiliation() {
		return this.affiliation;
	}
	
	public static List<UserViewModel> get(List<IUser> src) {
		return ViewModelConverter.<UserViewModel, IUser>convert(src, 
		new Instantiator<UserViewModel, IUser>() {
			@Override
			public UserViewModel create(IUser data) {
				return new UserViewModel(data);
			}
		});
	}
}