package theVirtuosa.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class ShadowStepAction extends AbstractGameAction {

    public ShadowStepAction(AbstractCreature source) {
        this.setValues(AbstractDungeon.player, source);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    // TODO: VFX / sfx

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_FASTER) {
                if (AbstractDungeon.player.discardPile.isEmpty()) {
                    this.isDone = true;
                } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.isDone = true;
                } else {
                    AbstractCard c = AbstractDungeon.player.discardPile.getTopCard();
                    AbstractDungeon.player.hand.addToHand(c);
                    AbstractDungeon.player.discardPile.removeCard(c);
                    c.lighten(false);
                    c.applyPowers();

                    Iterator var4 =  AbstractDungeon.player.powers.iterator();

                    while(var4.hasNext()) {
                        AbstractPower p = (AbstractPower)var4.next();
                        p.onCardDraw(c);
                    }

                    var4 = AbstractDungeon.player.relics.iterator();

                    while(var4.hasNext()) {
                        AbstractRelic r = (AbstractRelic)var4.next();
                        r.onCardDraw(c);
                    }

                    this.isDone = true;
                }
            }
        }
        this.tickDuration();
    }
}
