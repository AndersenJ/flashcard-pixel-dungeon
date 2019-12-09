package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONObject;
import org.json.JSONArray;

public class FlashDecks {
	public static final int MAX_DECKS = 8;
	private static List<IFlashDeck> decks = load();
	private static final String DECK_STRING = "decks";
	private static final String SAVE_LOC = "saved_decks.dat";

	public static void addDeck(IFlashDeck newDeck) {
		decks.add(newDeck);
	}

	public static List<IFlashDeck> getFlashDecks() {
<<<<<<< HEAD
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.retrieveTestDeck());
			// More dummy data for the import UI
			// FlashDeck deck1 = new FlashDeck(new ArrayList<IFlashQuestion>(), "Japanese");
			// FlashDeck deck2 = new FlashDeck(new ArrayList<IFlashQuestion>(), "Pokemon Types");
			// FlashDeck deck3 = new FlashDeck(new ArrayList<IFlashQuestion>(), "Trig Identities");
			// deck1.setIsActive(true);
			// deck2.setIsActive(false);
			// deck3.setIsActive(true);
			// FlashDecks.addDeck(deck1);
			// FlashDecks.addDeck(deck2);
			// FlashDecks.addDeck(deck3);
		}

=======
>>>>>>> development
		return Collections.unmodifiableList(decks);
	}

	public static List<IFlashDeck> getActiveDecks() {
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.retrieveTestDeck());
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
		return FlashDecks.getActiveDecks().get(Random.Int(FlashDecks.getActiveDecks().size())).retrieveQuestion();
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
	public static void save()
	{
		try {
			Bundle bundle = new Bundle();
			Object[] flash_deck = FlashDecks.getFlashDecks().toArray();
			JSONArray own_json = new JSONArray(flash_deck);
			String decks_string = own_json.toString();
			System.out.println("Saving: " + decks_string);
			bundle.put(DECK_STRING, decks_string);

			FileUtils.bundleToFile(SAVE_LOC, bundle);

		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			ShatteredPixelDungeon.reportException(e);
		}
	}
	private static List<IFlashDeck> load()
	{
		try{
			Bundle save_file = FileUtils.bundleFromFile(SAVE_LOC);
			//FlashDeck(List<IFlashQuestion> questions, String deckName)
			//System.out.println(save_file.toString());
			String full_text = save_file.getString(DECK_STRING);
			JSONArray decks = new JSONArray(full_text);
			List<IFlashDeck> result = new ArrayList<>();
			for(int i = 0; i < decks.length(); i++)
			{
				JSONObject obj = decks.getJSONObject(i);
				String deckName = obj.getString("deckName");
				boolean active = obj.getBoolean("active");
				JSONArray qArray = obj.getJSONArray("questions");
				List<IFlashQuestion> questions = new ArrayList<>();
				for(int j = 0; j < qArray.length(); j++)
				{
					JSONObject qObj = qArray.getJSONObject(j);
					questions.add(new FlashQuestion(qObj.getString("question"),
						qObj.getString("answer"), qObj.getInt("weight")));
				}
				FlashDeck newDeck = new FlashDeck(questions, deckName);
				newDeck.setIsActive(active);
				result.add(newDeck);
			}

			return result;

		}
		catch(IOException e)
		{
			System.err.println("Old Decks Not Found");
			return new ArrayList<IFlashDeck>();
		}
		catch(OutOfMemoryError e)
		{
			System.err.println("Old Decks Too Large");
			return new ArrayList<IFlashDeck>();
		}
		
	}
}
