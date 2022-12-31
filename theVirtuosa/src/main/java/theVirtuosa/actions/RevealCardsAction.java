package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RevealCardsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private Predicate<AbstractCard> predicate;
    private Consumer<ArrayList<AbstractCard>> callback;

    public RevealCardsAction(int amount, Predicate<AbstractCard> predicate, Consumer<ArrayList<AbstractCard>> callback) {

        this.p = AbstractDungeon.player;
        this.predicate = predicate;
        this.callback = callback;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {
        if (this.p.drawPile.isEmpty()) {
            this.isDone = true;
            return;
        }

        int maxRevealed = Math.min(this.amount, this.p.drawPile.size());

        ArrayList<AbstractCard> revealList = new ArrayList<>();
        ArrayList<AbstractCard> discardList = new ArrayList<>();
        ArrayList<AbstractCard> callbackList = new ArrayList<>();
        AbstractCard card;

        for (int i = 0; i < maxRevealed; i++)
        {
            card = this.p.drawPile.getNCardFromTop(i);
            revealList.add(card);
            if (this.predicate.test(card))
            {
                callbackList.add(card);
            }
            else {
                discardList.add(card);
            }
        }

        // THIS IS IN BACKWARDS ORDER BECAUSE ITS ADD TO TOP!!!! >>

        this.addToTop(new DiscardRevealedCardsAction(this.p, callbackList));
        this.addToTop(new DiscardRevealedCardsAction(this.p, discardList));

        if (this.callback != null) {
            this.callback.accept(callbackList);
        }

        Collections.reverse(revealList);
        revealList.forEach(c -> {
            if (discardList.contains(c)) {
                this.addToTop(new ShowRevealedCardAction(c, false));
            } else {
                this.addToTop(new ShowRevealedCardAction(c, true));
            }
        });

        // <<

        this.isDone = true;

        this.tickDuration();
    }
}
