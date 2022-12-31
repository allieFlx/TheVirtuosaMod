package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theVirtuosa.powers.VirtuosaResonancePower;

import java.util.Iterator;

public class SpendResonanceAttackAction extends AbstractGameAction {
    public int multiDamage[];
    private int multiplier;

    public SpendResonanceAttackAction(AbstractCreature source, int multiplier, int[] baseMultiDamage) {
        this.source = source;
        this.multiplier = multiplier;
        this.multiDamage = baseMultiDamage;
        this.damageType = DamageInfo.DamageType.NORMAL;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.FIRE;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {

        if (this.source != null && this.source.hasPower(VirtuosaResonancePower.POWER_ID)){
            for (int i = 0; i < this.multiDamage.length; i++)
            {
                this.multiDamage[i] += this.source.getPower(VirtuosaResonancePower.POWER_ID).amount * this.multiplier;
            }
            this.addToBot(new DamageAllEnemiesAction(this.source, this.multiDamage, this.damageType, this.attackEffect));
            this.addToBot(new RemoveSpecificPowerAction(this.source, this.source, VirtuosaResonancePower.POWER_ID));
        }

        this.isDone = true;
    }
}
