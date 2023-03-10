package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.Iterator;

public class SpectralExhaustAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard card;
    private CardGroup group;
    private float startingDuration;

    public SpectralExhaustAction(AbstractCard card, CardGroup group) {
        this.p = AbstractDungeon.player;
        this.card = card;
        this.group = group;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.EXHAUST;
        this.startingDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startingDuration;
    }
    @Override
    public void update() {
        if (this.duration == this.startingDuration && this.group.contains(this.card)) {
            this.doExhaust(this.card);
            CardCrawlGame.dungeon.checkForPactAchievement();
            this.card.exhaustOnUseOnce = false;
            this.card.freeToPlayOnce = false;
        }

        this.tickDuration();
    }

    private void doExhaust(AbstractCard c)
    {
        Iterator var2 = AbstractDungeon.player.relics.iterator();

        while(var2.hasNext()) {
            AbstractRelic r = (AbstractRelic)var2.next();
            r.onExhaust(c);
        }

        var2 = AbstractDungeon.player.powers.iterator();

        while(var2.hasNext()) {
            AbstractPower p = (AbstractPower)var2.next();
            p.onExhaust(c);
        }

        c.triggerOnExhaust();
        this.resetCardBeforeMoving(c);
        //AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        // TODO effect if in hand is exhaust, otherwise do blue fire on draw and discard piles
        if (this.group == this.p.hand) {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        }
        // use BGTorchExtinguish sound for flames

        // TODO spectral cards should go somewhere other than the exhaust pile?
        AbstractDungeon.player.exhaustPile.addToTop(c);
        AbstractDungeon.player.onCardDrawOrDiscard();
    }

    private void resetCardBeforeMoving(AbstractCard c)
    {
        if (AbstractDungeon.player.hoveredCard == c) {
            AbstractDungeon.player.releaseCard();
        }

        AbstractDungeon.actionManager.removeFromQueue(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        this.group.group.remove(c);
    }

}
