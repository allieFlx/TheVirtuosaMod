package theVirtuosa.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVirtuosa.cardmods.VirtuosaSpectralMod;

import java.util.Iterator;

public class EnergyGainIfSpectralAction extends AbstractGameAction {
    private int energyGain;
    public EnergyGainIfSpectralAction(int amount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, 0);
        this.duration = Settings.ACTION_DUR_FAST;
        this.energyGain = amount;
    }

    @Override
    public void update() {
        boolean isGained = false;
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        while(var2.hasNext()){
            AbstractCard c = (AbstractCard)var2.next();
            if (CardModifierManager.hasModifier(c, VirtuosaSpectralMod.ID)){
                isGained = true;
            }
        }

        if (isGained) {
            AbstractDungeon.player.gainEnergy(this.energyGain);
            AbstractDungeon.actionManager.updateEnergyGain(this.energyGain);
            Iterator var1 = AbstractDungeon.player.hand.group.iterator();

            while(var1.hasNext()) {
                AbstractCard c = (AbstractCard)var1.next();
                c.triggerOnGainEnergy(this.energyGain, true);
            }
        }

        this.isDone = true;
    }
}
