package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import java.util.ArrayList;
import java.util.List;
import com.watabou.utils.Random;

public class FlashDeck implements IFlashDeck {
	private List<IFlashQuestion> questions;
	private boolean isActive;
	private String deckName;

	public FlashDeck(List<IFlashQuestion> questions, String deckName) {
		this.questions = questions;
		this.deckName = deckName;
	}

	public static FlashDeck getTestDeck() {
		List<IFlashQuestion> multQuestions = new ArrayList<IFlashQuestion>();

		for (int i = 1; i < 5; ++i) {
			for (int j = 1; j < 5; ++j) {
				String q = Integer.toString(i) + " * " + Integer.toString(j);
				String a = Integer.toString(i * j);
				multQuestions.add(new FlashQuestion(q, a));
			}
		}

		FlashDeck testDeck = new FlashDeck(multQuestions, "Times Tables");
		testDeck.setIsActive(true);
		return testDeck;
	}

	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void addQuestion(IFlashQuestion newQuestion) {
		questions.add(newQuestion);
	}

	// this function could definitely be made faster if it ends up slowing things
	// down
	public IFlashQuestion getQuestion() {
		if (questions.size() == 0) {
			return new FlashQuestion("Error: the \"" + deckName + "\" deck has no cards", "Error: the \"" + deckName + "\" deck has no cards");
		}
		int totalWeight = 0;

		for (IFlashQuestion question : questions) {
			totalWeight += question.getWeight();
		}

		int rand = Random.Int(totalWeight);

		for (IFlashQuestion question : questions) {
			rand -= question.getWeight();

			if (rand <= 0) {
				return question;
			}
		}

		// this should never happen, but I put it here just in case.
		return questions.get(0);
	}
}
