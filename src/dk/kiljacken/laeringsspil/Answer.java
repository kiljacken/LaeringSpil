package dk.kiljacken.laeringsspil;

public class Answer {
	private String answer;
	private boolean rightAnswer;

	public Answer(String answer, boolean rigthAnswer) {
		this.answer = answer;
		this.rightAnswer = rigthAnswer;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public boolean isRightAnswer() {
		return rightAnswer;
	}
}
