package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.Iterator;

public class GnarledHornAction extends AbstractGameAction {
    private int energyGainAmt;
    private int drawAmt;
    private DamageInfo info;

    public GnarledHornAction(AbstractCreature target, DamageInfo info, int energyAmt, int drawAmt) {
        this.info = info;
        this.setValues(target, info);
        this.energyGainAmt = energyAmt;
        this.drawAmt = drawAmt;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_LIGHT));
            this.target.damage(this.info);
            if (((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) {
                this.addToBot(new GainEnergyAction(this.energyGainAmt));
                this.addToBot(new DrawCardAction(this.drawAmt));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
