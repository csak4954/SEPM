package at.uibk.los.model.interfaces;

public interface IAnswerView extends IIdentifiable {
	String getId();
	String getText();
	int getRightPointCount();
	int getWrongPointCount();
}
