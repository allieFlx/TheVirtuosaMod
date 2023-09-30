package theVirtuosa.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVirtuosa.effects.ShowCardRevealEffect;
import theVirtuosa.interfaces.OnRevealCard;
import theVirtuosa.patches.IncrementMomentumThisTurnPatch;
import theVirtuosa.powers.VirtuosaSamaraPower;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ShowRevealedCardAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean flash;

    public ShowRevealedCardAction(AbstractCard card, boolean isFlash) {
        this.card = card;
        this.flash = isFlash;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : Settings.ACTION_DUR_MED;
        if (card instanceof OnRevealCard) { this.startDuration += ((OnRevealCard)card).revealEffectDelay(); }
        this.duration = this.startDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startDuration) {

            AbstractDungeon.effectsQueue.add(new ShowCardRevealEffect(this.card, this.flash));

            ++IncrementMomentumThisTurnPatch.CARDS_REVEALED;

            if (this.card instanceof OnRevealCard)
            {
                ((OnRevealCard)this.card).onRevealed();
            }

            if (AbstractDungeon.player.hasPower(VirtuosaSamaraPower.POWER_ID))
            {
                AbstractPower pow = AbstractDungeon.player.getPower(VirtuosaSamaraPower.POWER_ID);
                ((VirtuosaSamaraPower)pow).onCardRevealed();
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
