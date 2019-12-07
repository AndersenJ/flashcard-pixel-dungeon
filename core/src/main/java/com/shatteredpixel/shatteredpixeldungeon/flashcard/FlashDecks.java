package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.nio.file.Files;

import org.json.JSONObject;
import org.json.JSONArray;

public class FlashDecks {
	public static final int MAX_DECKS = 8;
	private static List<IFlashDeck> decks = new ArrayList<IFlashDeck>();

	public static void addDeck(IFlashDeck newDeck) {
		decks.add(newDeck);
	}

	public static List<IFlashDeck> getFlashDecks() {
		return Collections.unmodifiableList(decks);
	}

	public static List<IFlashDeck> getActiveDecks() {
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.getTestDeck());
		}

		List<IFlashDeck> activeDecks = new ArrayList<IFlashDeck>();

		for (IFlashDeck deck : decks) {
			if (deck.isActive()) {
				activeDecks.add(deck);
			}
		}

		if (activeDecks.isEmpty()) {
			IFlashDeck activeDeck = decks.get(0);
			activeDeck.setIsActive(true);
			activeDecks.add(activeDeck);
		}

		return Collections.unmodifiableList(activeDecks);
	}

	public static IFlashDeck importFromFile(File file) throws Exception {
		String data = new String(Files.readAllBytes(file.toPath()));
		JSONObject jsonDeck = new JSONObject(data);
		String name = jsonDeck.getString("name");
		List<IFlashQuestion> questions = new ArrayList<IFlashQuestion>();
		JSONArray jsonQuestions = jsonDeck.getJSONArray("questions");

		for (int i = 0; i < jsonQuestions.length(); ++i) {
			JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
			String q = jsonQuestion.getString("question");
			String a = jsonQuestion.getString("answer");
			questions.add(new FlashQuestion(q, a));
		}

		IFlashDeck newDeck = new FlashDeck(questions, name);
		newDeck.setIsActive(true);
		FlashDecks.addDeck(newDeck);
		return newDeck;
	}

	// todo someday: give decks their own weights
	public static IFlashQuestion getQuestion() {
		// random card from random deck
		return FlashDecks.getActiveDecks().get(Random.Int(FlashDecks.getActiveDecks().size())).getQuestion();
	}

	public static int getCount() {
		return decks.size();
	}

	public static boolean isDeckActive(String deckName) {
		for (IFlashDeck deck : decks) {
			if (deck.getDeckName().equals(deckName)) {
				return deck.isActive();
			}
		}

		return false;
	}

	public static boolean setDeckActive(String deckName, boolean shouldBeActive) {
		for (IFlashDeck deck : decks) {
			if (deck.getDeckName().equals(deckName)) {
				deck.setIsActive(shouldBeActive);
				return true;
			}
		}

		return false;
	}

	public static boolean removeDeck(String deckName) {
		for (IFlashDeck deck : decks) {
			if (deck.getDeckName().equals(deckName)) {
				decks.remove(deck);
				return true;
			}
		}

		return false;
	}
}
