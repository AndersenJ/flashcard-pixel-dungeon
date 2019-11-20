package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.flashcard.IFlashQuestion;

public class WndMysticalCardAnswer extends WndMysticalCard {

  protected static final float FAILURE_TIME = 1.0f;

  WndMysticalCardAnswer(IFlashQuestion question) {
    super(question);
  }

  @Override
  public String getText() {
    return question.getAnswer();
  }

  @Override
  public int renderInputs(final int height, final int width) {
    RedButton fail = new RedButton("Failure...", 8) {
      @Override
      protected void onClick() {
        hide();
        onQuestionFail();
      }
    };

    RedButton success = new RedButton("Success!", 8) {
      @Override
      protected void onClick() {
        hide();
        onQuestionSuccess();
      }
    };

    fail.setRect(MARGIN_HORIZONTAL, height, (width - MARGIN_BETWEEN) / 2, BUTTON_HEIGHT);
    add(fail);

    success.setRect(MARGIN_HORIZONTAL + fail.width() + (MARGIN_BETWEEN / 2), height, (width - MARGIN_BETWEEN) / 2,
        BUTTON_HEIGHT);

    add(success);
    return (int) BUTTON_HEIGHT + MARGIN_VERTICAL;
  }

  private void onQuestionFail() {
    question.increaseWeight();
    Dungeon.hero.spendAndNext(FAILURE_TIME);
    //GameScene.show(new WndMysticalCardQuestion(null));
  }

  private void onQuestionSuccess() {
    GameScene.show(new WndMysticalCardReward());
  }
}
