package at.uibk.los.model;

public interface IQuiz
{
	int getId();
	void setTitle(String title);
	String getTitle();
	Iterable<IQuestion> getQuestions();
	IQuestion addQuestion(String text); 
	void addApproach(int userId, int questionId, int[] answers);
}
