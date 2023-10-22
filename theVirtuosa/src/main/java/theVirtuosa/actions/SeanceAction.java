package theVirtuosa.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theVirtuosa.cardmods.VirtuosaSpectralMod;

public class SeanceAction extends AbstractGameAction {
    private int copies;

    public SeanceAction(int copies) {
        this.copies = copies;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && !AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractCard topCard = AbstractDungeon.player.drawPile.getTopCard().makeStatEquivalentCopy();
            CardModifierManager.addModifier(topCard, new VirtuosaSpectralMod());
            this.addToBot(new MakeTempCardInHandAction(topCard, this.copies));
        }

        this.tickDuration();
    }
}
