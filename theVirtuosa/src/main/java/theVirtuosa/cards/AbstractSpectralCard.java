package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractSpectralCard extends AbstractDynamicCard {

    public AbstractSpectralCard(final String id,
                                final String img,
                                final int cost,
                                final CardType type,
                                final CardColor color,
                                final CardRarity rarity,
                                final CardTarget target) {

        super(id, img, cost, type, color, rarity, target);

        this.exhaust = true;
    }

    // TODO: VFX for spectral cards, glow pink / blue (pink for sequestered, blue for spectral)

    @Override
    public void atTurnStart() {
        // TODO: VFX for spectral exhaust. should purge instead?
        if (AbstractDungeon.player.hand.contains(this))
        {
            AbstractDungeon.actionManager.addToTop(
                    new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
        else if (AbstractDungeon.player.drawPile.contains(this))
        {
            AbstractDungeon.actionManager.addToTop(
                    new ExhaustSpecificCardAction(this, AbstractDungeon.player.drawPile));
        }
        else if (AbstractDungeon.player.discardPile.contains(this))
        {
            AbstractDungeon.actionManager.addToTop(
                    new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
        }
    }

    /*
    @Override
    public void onMoveToDiscard() {
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
    }
    @Override
    public void triggerOnManualDiscard() {
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
    }

     */
}