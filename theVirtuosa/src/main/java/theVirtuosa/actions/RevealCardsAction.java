package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.interfaces.OnRevealCard;
import theVirtuosa.patches.OnAddCardToDeckPatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RevealCardsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private Predicate<AbstractCard> predicate;
    private Consumer<ArrayList<AbstractCard>> callback;
    private boolean isMovingCallback;
    private Consumer<Integer> defaultCallback;

    // boolean parameter for the caller: is the caller attempting to move cards in the callback?
    //  if false, return callback as normal
    //  if true, only return cards where isMovedOnReveal is false
    public RevealCardsAction(int amount, Predicate<AbstractCard> predicate, Consumer<ArrayList<AbstractCard>> callback, boolean isMovingCallback, Consumer<Integer> defaultCallback) {
        this.p = AbstractDungeon.player;
        this.predicate = predicate;
        this.callback = callback;
        this.isMovingCallback = isMovingCallback;
        this.defaultCallback = defaultCallback;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;

        if (AbstractDungeon.player.hasRelic(TheVirtuosa.makeID("VirtuosaGoldenTorcRelic"))) {
            AbstractDungeon.player.getRelic(TheVirtuosa.makeID("VirtuosaGoldenTorcRelic")).flash();
            this.amount += 1;
        }
    }

    public RevealCardsAction(int amount, Predicate<AbstractCard> predicate, Consumer<ArrayList<AbstractCard>> callback, Consumer<Integer> defaultCallback) {
        this(amount, predicate, callback, false, defaultCallback);
    }
    public RevealCardsAction(int amount, Predicate<AbstractCard> predicate, Consumer<ArrayList<AbstractCard>> callback, boolean isMovingCallback) {
        this(amount, predicate, callback, isMovingCallback, null);
    }

    public RevealCardsAction(int amount, Predicate<AbstractCard> predicate, Consumer<ArrayList<AbstractCard>> callback) {
        this(amount, predicate, callback, false, null);
    }
    
    @Override
    public void update() {
        if (this.p.drawPile.isEmpty()) {
            if (this.defaultCallback != null) {
                this.defaultCallback.accept(-1);}
            this.isDone = true;
            return;
        }

        int maxRevealed = Math.min(this.amount, this.p.drawPile.size());

        ArrayList<AbstractCard> revealList = new ArrayList<>();
        ArrayList<AbstractCard> discardList = new ArrayList<>();
        ArrayList<AbstractCard> callbackList = new ArrayList<>();
        AbstractCard card;
        boolean cardIsMoving;

        for (int i = 0; i < maxRevealed; i++)
        {
            card = this.p.drawPile.getNCardFromTop(i);
            revealList.add(card);

            //  if card instance OnRevealCard and card.isMovedOnReveal, dont add to a discard list
            //  (but we might still want to act on it in the callback)
            //  if both the effect that revealed it and the card itself want to move it upon reveal,
            //  OnReveal effects take prescidence, therefore add a check when calling this action

            cardIsMoving = false;
            if (card instanceof OnRevealCard)
            {
                cardIsMoving = ((OnRevealCard)card).isMovedOnReveal();
            }

            if (!this.isMovingCallback || !cardIsMoving)
            {
                if (this.predicate.test(card))
                {
                    callbackList.add(card);
                }
                else {
                    discardList.add(card);
                }
            }
        }

        // ACTIONS ADDED IN BACKWARDS ORDER BECAUSE ITS ADD TO TOP!!!! >>

        // if we might move cards in the callback:
        //  the callback is responsible for removing the cards from the callbackList it doesnt want to discard,
        //  otherwise, we discard everything as default
        if (this.isMovingCallback) {
            this.addToTop(new DiscardRevealedCardsAction(this.p, callbackList));
        } else {
            discardList.addAll(callbackList);
        }
        this.addToTop(new DiscardRevealedCardsAction(this.p, discardList));

        if (this.callback != null && !callbackList.isEmpty()) {
            this.callback.accept(callbackList);
        } else if (this.defaultCallback != null) {
            this.defaultCallback.accept(-1);
        }

        Collections.reverse(revealList);
        revealList.forEach(c -> {
            if (callbackList.contains(c) || c instanceof OnRevealCard) {
                this.addToTop(new ShowRevealedCardAction(c, true));
            } else {
                this.addToTop(new ShowRevealedCardAction(c, false));
            }
        });

        // <<

        this.isDone = true;

        this.tickDuration();
    }
}
