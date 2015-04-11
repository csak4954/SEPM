package at.uibk.los.model.mocks;

import at.uibk.los.model.mocks.LoginProviderMock;

public class StaffLoginProviderMock extends LoginProviderMock {
	
	@Override
	public boolean login() {
		setUser(new UserMock("florian", "tischler", "florian.tischler@outlook.com", 123456789, "staff"));
		return true;
	}
}
