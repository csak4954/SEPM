package at.uibk.los.model.storage;

class Administration extends Registration {

	public Administration(User user, Lecture lecture) {
		super(user, lecture);
	}
	
	static AdministrationRepository Repo;
}
