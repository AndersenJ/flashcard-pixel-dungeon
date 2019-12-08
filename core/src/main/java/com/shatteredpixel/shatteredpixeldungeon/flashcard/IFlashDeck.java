package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public interface IFlashDeck {
	public IFlashQuestion retrieveQuestion();

	public void addQuestion(IFlashQuestion newQuestion);

	public String getDeckName();

	public void setDeckName(String deckName);

	public boolean isActive();

	public void setIsActive(boolean isActive);
}
