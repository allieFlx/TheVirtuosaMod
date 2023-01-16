package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.OrangePellets;

import java.util.Iterator;

public class ExorcismAction extends AbstractGameAction
{
    private AbstractPlayer p;
    public ExorcismAction(AbstractPlayer p) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;

        this.p = p;
    }

    @Override
    public void update() {
        // TODO: VFX

        this.addToBot(new ExhaustAllOfTypeAction(AbstractCard.CardType.CURSE, p.hand));
        this.addToBot(new ExhaustAllOfTypeAction(AbstractCard.CardType.CURSE, p.drawPile));
        this.addToBot(new ExhaustAllOfTypeAction(AbstractCard.CardType.CURSE, p.discardPile));
        this.addToBot(new RemoveDebuffsAction(p));

        this.isDone = true;
    }
}
