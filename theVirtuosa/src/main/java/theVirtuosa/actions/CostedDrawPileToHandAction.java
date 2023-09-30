package theVirtuosa.actions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
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
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {

        if (this.p.drawPile.isEmpty()) {
            this.isDone = true;
            return;
        } else if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
            return;
        } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
            return;
        } else if (this.duration == Settings.ACTION_DUR_FAST) {
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

            this.amount = tmp.size();
            Iterator var3 = tmp.group.iterator();

            while (var3.hasNext()) {
                AbstractCard c = (AbstractCard)var3.next();
                this.p.drawPile.removeCard(c);
                this.p.drawPile.addToTop(c);
            }
        }

        if (!SoulGroup.isActive()){

            this.duration -= Gdx.graphics.getDeltaTime();

            if (this.amount != 0 && this.duration < 0.0F) {
                if (Settings.FAST_MODE) {
                    this.duration = Settings.ACTION_DUR_XFAST;
                } else {
                    this.duration = Settings.ACTION_DUR_FASTER;
                }

                --this.amount;
                if (!AbstractDungeon.player.drawPile.isEmpty()) {
                    AbstractDungeon.player.draw();
                    AbstractDungeon.player.hand.refreshHandLayout();
                } else {
                    this.isDone = true;
                    return;
                }

                if (this.amount == 0) {
                    this.isDone = true;
                }
            }
        }
    }
}
