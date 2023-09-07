package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DiscardPileToShuffleAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private Consumer<AbstractCard> callback;
    private int amount;

    public DiscardPileToShuffleAction(AbstractCreature source, Consumer<AbstractCard> callback, int amount) {
        this.p = AbstractDungeon.player;
        this.callback = callback;
        this.setValues((AbstractCreature)null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = amount;
    }

    public DiscardPileToShuffleAction(AbstractCreature source) {
        this(source, null, 1);
    }

    public DiscardPileToShuffleAction(AbstractCreature source, int amount) {
        this(source, null, amount);
    }

    public DiscardPileToShuffleAction(AbstractCreature source, Consumer<AbstractCard> callback) {
        this(source, callback, 1);
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (this.p.discardPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                if (this.p.discardPile.size() == this.amount) {
                    ArrayList<AbstractCard> tmp = new ArrayList<>();
                    tmp.addAll(this.p.discardPile.group);
                    Iterator var3 = tmp.iterator();

                    while(var3.hasNext()) {
                        AbstractCard c = (AbstractCard)var3.next();
                        this.p.discardPile.removeCard(c);
                        this.p.hand.moveToDeck(c, true);
                        if (this.callback != null)
                        {
                            this.callback.accept(c);
                        }
                    }
                }

                if (this.p.discardPile.group.size() > this.amount) {
                    String uiText = this.amount == 1 ? TEXT[0] : TEXT[1] + this.amount + TEXT[2];
                    AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.amount, uiText, false, false, false, false);
                    this.tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var3.hasNext()) {
                    AbstractCard c = (AbstractCard)var3.next();
                    this.p.discardPile.removeCard(c);
                    this.p.hand.moveToDeck(c, true);
                    if (this.callback != null)
                    {
                        this.callback.accept(c);
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("theVirtuosa:DiscardPileToShuffleAction");
        TEXT = uiStrings.TEXT;
    }
}
