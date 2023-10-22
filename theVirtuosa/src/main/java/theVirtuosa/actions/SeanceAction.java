package theVirtuosa.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;

public class SeanceAction extends AbstractGameAction {
    private int copies;

    public SeanceAction(int copies) {
        this.copies = copies;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                TheVirtuosa.createDrawPileEmptyDialog();
                this.tickDuration();
                return;
            }
            AbstractCard topCard = AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy();
            CardModifierManager.addModifier(topCard, new VirtuosaSpectralMod());
            this.addToBot(new MakeTempCardInHandAction(topCard, this.copies));
        }

        this.tickDuration();
    }
}
