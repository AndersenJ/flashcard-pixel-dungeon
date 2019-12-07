
package com.shatteredpixel.shatteredpixeldungeon.windows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Anonymizable;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.MysticalCard;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.*;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.*;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;

public class WndSelectEffect extends Window {
  private static final String AC_USE_AS_POTION = "POTION";

  private static final int WIDTH = 120;
  private static final int BTN_SIZE = 20;

  List<Class<? extends Item>> potionClasses = Arrays.asList(PotionOfExperience.class, PotionOfFrost.class,
      PotionOfHaste.class, PotionOfHealing.class, PotionOfInvisibility.class, PotionOfLevitation.class,
      PotionOfLiquidFlame.class, PotionOfMindVision.class, PotionOfParalyticGas.class, PotionOfPurity.class,
      PotionOfToxicGas.class);

  List<Class<? extends Item>> scrollClasses = Arrays.asList(ScrollOfIdentify.class, ScrollOfLullaby.class,
      ScrollOfMagicMapping.class, ScrollOfMirrorImage.class, ScrollOfRetribution.class, ScrollOfRage.class,
      ScrollOfRecharging.class, ScrollOfRemoveCurse.class, ScrollOfTeleportation.class, ScrollOfTerror.class,
      ScrollOfTransmutation.class);

  Map<Class<? extends Item>, Integer> imageMap = getImages();

  static Class<? extends Item> curSelection = null;

  public WndSelectEffect(String action) {
    GLog.w(Messages.get(this, action + "_text"));
    IconTitle titlebar = new IconTitle();
    titlebar.icon(new ItemSprite(ItemSpriteSheet.MYSTICAL_CARD, null));
    titlebar.label(Messages.get(MysticalCard.class, "name"));
    titlebar.setRect(0, 0, WIDTH, 0);
    add(titlebar);

    RenderedTextBlock text = PixelScene.renderTextBlock(6);
    text.text(Messages.get(this, action + "_text"));
    text.setPos(0, titlebar.bottom());
    text.maxWidth(WIDTH);
    add(text);

    final RedButton choose = new RedButton("") {
      @Override
      protected void onClick() {
        super.onClick();
        hide();
        try {
          Anonymizable selectedItem = (Anonymizable) curSelection.getDeclaredConstructor().newInstance();
          selectedItem.setTemp(true);
          if (!selectedItem.isKnown()) {
            selectedItem.anonymize();
          }
          selectedItem.execute(Dungeon.hero);
        } catch (Exception e) {
          GLog.n(e.getMessage());
        }
      }
    };

    choose.visible = false;
    choose.icon(new ItemSprite(new MysticalCard()));
    choose.enable(false);
    choose.setRect(0, 95, WIDTH, 20);
    add(choose);

    List<Class<? extends Item>> classList = action.equals(AC_USE_AS_POTION) ? potionClasses : scrollClasses;
    float left = (WIDTH - BTN_SIZE * ((classList.size() + 1) / 2)) / 2f;
    float top = text.bottom() + 5;
    int row = action.equals(AC_USE_AS_POTION) ? 0 : 16;
    int placed = 0;

    // Set<Class<? extends Potion>> knownPotions = Potion.getKnown();
    // Set<Class<? extends Scroll>> knownScrolls = Scroll.getKnown();
    Collections.shuffle(classList);
    for (int i = 0; i < 3; ++i) {
      final Class<? extends Item> itemClass = classList.get(i);
      IconButton btn = new IconButton() {
        @Override
        protected void onClick() {
          curSelection = itemClass;
          choose.visible = true;
          choose.text(Messages.get(curSelection, "name"));
          choose.enable(true);
          super.onClick();
        }
      };
      Image im = new Image(Assets.CONS_ICONS, 7 * imageMap.get(itemClass), row, 7, 8);
      im.scale.set(2f);
      btn.icon(im);
      btn.setRect(left + placed * BTN_SIZE, top, BTN_SIZE, BTN_SIZE);
      add(btn);

      ++placed;
      if (placed == ((classList.size() + 1) / 2)) {
        placed = 0;
        if (classList.size() % 2 == 1) {
          left += BTN_SIZE / 2f;
        }
        top += BTN_SIZE;
      }
    }

    resize(WIDTH, 115);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    new MysticalCard().collect();
  }

  private static Map<Class<? extends Item>, Integer> getImages() {
    Map<Class<? extends Item>, Integer> images = new HashMap<>();
    images.put(PotionOfExperience.class, 0);
    images.put(PotionOfFrost.class, 1);
    images.put(PotionOfHaste.class, 2);
    images.put(PotionOfHealing.class, 3);
    images.put(PotionOfInvisibility.class, 4);
    images.put(PotionOfLevitation.class, 5);
    images.put(PotionOfLiquidFlame.class, 6);
    images.put(PotionOfMindVision.class, 7);
    images.put(PotionOfParalyticGas.class, 8);
    images.put(PotionOfPurity.class, 9);
    images.put(PotionOfStrength.class, 10);
    images.put(PotionOfToxicGas.class, 11);
    images.put(ScrollOfIdentify.class, 0);
    images.put(ScrollOfLullaby.class, 1);
    images.put(ScrollOfMagicMapping.class, 2);
    images.put(ScrollOfMirrorImage.class, 3);
    images.put(ScrollOfRetribution.class, 4);
    images.put(ScrollOfRage.class, 5);
    images.put(ScrollOfRecharging.class, 6);
    images.put(ScrollOfRemoveCurse.class, 7);
    images.put(ScrollOfTeleportation.class, 8);
    images.put(ScrollOfTerror.class, 9);
    images.put(ScrollOfTransmutation.class, 10);
    images.put(ScrollOfUpgrade.class, 11);
    return images;
  }
}
