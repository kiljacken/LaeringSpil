package dk.kiljacken.laeringsspil;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import dk.kiljacken.laeringsspil.MainClass.Category;

public class QuestionCard extends Button {
	private Category category;
	private Answer[] answers;
	private boolean answered;
	private boolean correct;
	
	private Rectangle titleBox;
	private Rectangle[] answerBoxes;
	
	private WrappedText title;
	private WrappedText[] answerLabels;
	
	public QuestionCard(GameContainer container, Category category, String title, Answer... answers) {
		super(container, (800 - Assets.card.getWidth()) / 2, (600 - Assets.card.getHeight()) / 2, Assets.card.getWidth(), Assets.card.getHeight());
		this.category = category;
		this.answers = answers;
		
		this.titleBox = new Rectangle(getBounds().getMinX() + 82, getBounds().getMinY() + 5, 348, 41);
		this.answerBoxes = new Rectangle[3];
		
		float x = getBounds().getMinX() + 54;
		float y = getBounds().getMinY() + 77; 
		float yOff = 62;
		
		this.answerLabels = new WrappedText[3];
		for (int i = 0; i < 3; i++) {
			this.answerLabels[i] = new WrappedText(container, x, y + (i * yOff), Assets.card.getWidth() - 60, answers[i].getAnswer());
			
			
			this.answerBoxes[i] = new Rectangle(x, y + (i * yOff), container.getDefaultFont().getWidth(this.answerLabels[i].getText()), container.getDefaultFont().getHeight(this.answerLabels[i].getText()));
			
		}
		
		this.title = new WrappedText(container, titleBox.getMinX(), titleBox.getMinY(), titleBox.getWidth(), title);
	}
	
	public void render(GameContainer container, Graphics g) {
		super.render(container, g);
		
		Assets.card.draw(getBounds().getMinX(), getBounds().getMinY());
		
		g.setColor(Color.black);
		title.render(container, g);
		
		for (int i = 0; i < answers.length; i++) {
			answerLabels[i].render(container, g);
		}
		
		if (MainClass.DEBUG_SHOW_BOUNDS) {
			g.setColor(Color.red);
			
			for (int i = 0; i < 3; i++)
				g.draw(answerBoxes[i]);
		}
	}
	
	public void reset() {
		this.answered = false;
		this.correct = false;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public boolean isAnswered() {
		return answered;
	}
	
	public boolean isCorrect() {
		return correct;
	}

	@Override
	public void onClick(float x, float y) {
		for (int i=0; i < 3; i++) {
			if (answerBoxes[i].contains(x, y)) {
				Answer answer = answers[i];
				
				answered = true;
				correct = answer.isRightAnswer();
			}
		}
	}
}
