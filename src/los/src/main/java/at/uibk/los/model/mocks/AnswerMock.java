package at.uibk.los.model.mocks;

import at.uibk.los.model.interfaces.IAnswer;

public class AnswerMock implements IAnswer {

	private String text;
	private int id;
	private boolean solution;
	
	public AnswerMock(int id, String text, boolean solution) 
	{
		this.id = id;
		this.text = text;
		this.solution = solution;
	}
	
	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public boolean isSolution() 
	{
		return solution;
	}

}
