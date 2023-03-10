package theVirtuosa.actions;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class CostedDrawPileToHandAction extends AbstractGameAction {
    private AbstractPlayer p;

    // Draws a card of cost 1, 2, and 3, if possible.

    public CostedDrawPileToHandAction() {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, this.p, 3);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            // --incorrectly interacts with hand size modification--
            //  should be fixed. this was an error where hand sized is reduced but more than max amount of
            //  cards remain in hand

            if (this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var2 = this.p.drawPile.group.iterator();

            boolean has1 = false;
            boolean has2 = false;
            boolean has3 = false;

            AbstractCard card;
            while(var2.hasNext()) {
                card = (AbstractCard)var2.next();
                if (card.cost == 1 && !has1) {
                    tmp.addToBottom(card);
                    has1 = true;
                } else if (card.cost == 2 && !has2) {
                    tmp.addToBottom(card);
                    has2 = true;
                } else if (card.cost == 3 && !has3) {
                    tmp.addToBottom(card);
                    has3 = true;
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }
            for(int i = 0; i < 3; ++i) {
                if (!tmp.isEmpty()) {
                    tmp.shuffle();
                    card = tmp.getBottomCard();
                    tmp.removeCard(card);
                    if (this.p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.p.drawPile.removeCard(card);
                        AbstractDungeon.player.hand.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }

            this.isDone = true;
        }

        this.tickDuration();
    }
}
