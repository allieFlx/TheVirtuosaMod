package theVirtuosa.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RevealedCardsToHandAction extends AbstractGameAction {

    private ArrayList<AbstractCard> cardList;

    public RevealedCardsToHandAction(ArrayList<AbstractCard> cardList) {
        this.cardList = cardList;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
    }
    
    @Override
    public void update() {
        ArrayList<AbstractCard> drawCards = new ArrayList<>(this.cardList);
        drawCards.forEach(c -> {
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
            {
                AbstractDungeon.player.drawPile.moveToHand(c);
                this.cardList.remove(c);
            }
        });
        if (cardList.size() > 0) { AbstractDungeon.player.createHandIsFullDialog(); }


        this.isDone = true;
    }
}
