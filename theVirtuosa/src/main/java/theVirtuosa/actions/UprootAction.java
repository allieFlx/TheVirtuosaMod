package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class UprootAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    public UprootAction() {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.hand.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                AbstractDungeon.handCardSelectScreen.open(TEXT[2], p.hand.size(), true, true);
                this.tickDuration();
                return;
            }

            if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                Iterator var3 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while(var3.hasNext()) {
                    AbstractCard c = (AbstractCard)var3.next();
                    this.p.hand.removeCard(c);
                    this.p.hand.moveToDeck(c, true);
                }

                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("theVirtuosa:ShuffleCardsAction");
        TEXT = uiStrings.TEXT;
    }
}
