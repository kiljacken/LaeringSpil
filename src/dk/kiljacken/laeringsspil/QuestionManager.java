package dk.kiljacken.laeringsspil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.GameContainer;

import dk.kiljacken.laeringsspil.MainClass.Category;

public class QuestionManager {
	private final HashMap<Category, ArrayList<QuestionCard>> questionsByCategory;
	
	private final ArrayList<QuestionCard> chemistryQuestions;
	private final ArrayList<QuestionCard> physicsQuestions;
	private final ArrayList<QuestionCard> biologyQuestions;
	private final ArrayList<QuestionCard> geographyQuestions;
	
	private final Random random = new Random();
	
	public QuestionManager(GameContainer container, String questionList) {
		questionsByCategory = new HashMap<>();
		
		chemistryQuestions = new ArrayList<>();
		physicsQuestions = new ArrayList<>();
		biologyQuestions = new ArrayList<>();
		geographyQuestions = new ArrayList<>();
		
		questionsByCategory.put(Category.PHYSICS, physicsQuestions);
		questionsByCategory.put(Category.CHEMISTRY, chemistryQuestions);
		questionsByCategory.put(Category.BIOLOGY, biologyQuestions);
		questionsByCategory.put(Category.GEOGRAPHY, geographyQuestions);
		
		for (String question: questionList.split("\n")) {
			String[] parts = question.split("@");
			
			Category category = Category.valueOf(parts[0]);
			String title = parts[1];
			int rightAnswer = Integer.parseInt(parts[5]) - 1;
			
			Answer[] answers = new Answer[3];
			for (int i = 2; i < 5; i++) {
				answers[i - 2] = new Answer(parts[i], i - 2 == rightAnswer);
			}
			
			QuestionCard card = new QuestionCard(container, category, title, answers);
			questionsByCategory.get(category).add(card);
		}
	}
	
	public ArrayList<QuestionCard> getQuestionsForCategory(Category category) {
		return questionsByCategory.get(category);
	}
	
	public QuestionCard getRandomQuestionForCategory(Category category) {
		ArrayList<QuestionCard> cards = getQuestionsForCategory(category);
		
		QuestionCard card = cards.get(random.nextInt(cards.size()));
		card.reset();
		
		return card;
	}
}
