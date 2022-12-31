package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.function.Consumer;

public class DiscardRevealedCardsAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cardsToDiscard;

    public DiscardRevealedCardsAction(AbstractPlayer p, ArrayList<AbstractCard> cardsToDiscard) {

        this.p = p;
        this.cardsToDiscard = cardsToDiscard;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (this.cardsToDiscard.isEmpty()) {
            this.isDone = true;
            return;
        }

        for (int i = 0; i < cardsToDiscard.size(); i++)
        {
            AbstractCard c = cardsToDiscard.get(i);
            this.p.drawPile.moveToDiscardPile(c);
        }

        this.isDone = true;

        this.tickDuration();
    }
}
