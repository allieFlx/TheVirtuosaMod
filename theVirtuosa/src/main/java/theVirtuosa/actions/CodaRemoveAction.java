package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theVirtuosa.powers.VirtuosaCodaPower;

import java.util.Iterator;
import java.util.function.Predicate;

public class CodaRemoveAction extends AbstractGameAction
{
    public CodaRemoveAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        // reset cost of attacks

        if (this.duration == Settings.ACTION_DUR_XFAST) {
            Iterator var8 = AbstractDungeon.player.hand.group.iterator();

            AbstractCard c;
            AbstractCard cpy;
            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                cpy = c.makeCopy();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.updateCost(cpy.cost);
                    c.isCostModified = false;
                }
            }

            var8 = AbstractDungeon.player.drawPile.group.iterator();

            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                cpy = c.makeCopy();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.updateCost(cpy.cost);
                    c.isCostModified = false;
                }
            }

            var8 = AbstractDungeon.player.discardPile.group.iterator();

            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                cpy = c.makeCopy();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.updateCost(cpy.cost);
                    c.isCostModified = false;
                }
            }

            var8 = AbstractDungeon.player.exhaustPile.group.iterator();

            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                cpy = c.makeCopy();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.updateCost(cpy.cost);
                    c.isCostModified = false;
                }
            }
            this.isDone = true;
            this.tickDuration();
        }
    }
}
