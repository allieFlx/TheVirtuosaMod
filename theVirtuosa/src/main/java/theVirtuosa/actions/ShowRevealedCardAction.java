package theVirtuosa.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import theVirtuosa.effects.ShowCardRevealEffect;
import theVirtuosa.interfaces.OnRevealCard;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ShowRevealedCardAction extends AbstractGameAction {

    private AbstractCard card;
    private boolean flash;

    public ShowRevealedCardAction(AbstractCard card, boolean isFlash) {
        this.card = card;
        this.flash = isFlash;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        if (card instanceof OnRevealCard) { this.startDuration += ((OnRevealCard)card).revealEffectDelay(); }
        this.duration = this.startDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startDuration) {

            AbstractDungeon.effectsQueue.add(new ShowCardRevealEffect(this.card, this.flash));

            if (this.card instanceof OnRevealCard)
            {
                ((OnRevealCard)this.card).onRevealed();
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
