package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.flashcard.FlashDecks;

public class DeckExitButton extends ExitButton {
    @Override
    protected void onClick() {
        FlashDecks.save();
        super.onClick();
    }
}