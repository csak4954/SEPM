package at.uibk.los.model.interfaces;

public interface IAnswer extends IAnswerView
{
	void setText(String text);
	
	void setIsSolution(boolean solution);
	
	boolean isSolution();
	
	void setRightPointCount(int count);
	
	void setWrongPointCount(int count);
}
