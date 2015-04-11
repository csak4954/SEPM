package at.uibk.los.model.mocks;

public class StudentLoginProviderMock extends LoginProviderMock {

	@Override
	public boolean login() {
		setUser(new UserMock("florian", "tischler", "florian.tischler@outlook.com", 123456789, "student"));
		return true;
	}

}
