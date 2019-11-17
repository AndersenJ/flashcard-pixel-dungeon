package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;

import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMysticalCardQuestion;

import java.util.List;

public class MysticalCard extends Item {
  private static final String AC_READ = "READ";

  {
    image = ItemSpriteSheet.MYSTICAL_CARD;
    stackable = true;
    defaultAction = AC_READ;
    bones = true;
  }

  @Override
  public List<String> actions(Hero hero) {
    List<String> actions = super.actions(hero);
    actions.add(AC_READ);
    return actions;
  }

  @Override
  public void execute(Hero hero, String action) {
    super.execute(hero, action);

    if (action.equals(AC_READ)) {
      detach(hero.belongings.backpack);
      GameScene.show(new WndMysticalCardQuestion(null));
    }
  }

  @Override
  public boolean isUpgradable() {
    return false;
  }

  @Override
  public boolean isIdentified() {
    return true;
  }

  @Override
  public int price() {
    return 100 * quantity;
  }
}
