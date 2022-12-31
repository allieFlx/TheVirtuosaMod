package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import theVirtuosa.powers.VirtuosaResonancePower;

import java.util.Iterator;

public class DamageAllEnemiesForResonanceAction extends AbstractGameAction {
    public int[] damage;

    public DamageAllEnemiesForResonanceAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this.source = source;
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        int i;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean playedMusic = false;
            int m = AbstractDungeon.getCurrRoom().monsters.monsters.size();

            for(i = 0; i < m; ++i) {
                if (!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying && ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).currentHealth > 0 && !((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect));
                    }
                }
            }
        }

        this.tickDuration();
        if (this.isDone) {
            Iterator var5 = AbstractDungeon.player.powers.iterator();

            while(var5.hasNext()) {
                AbstractPower p = (AbstractPower)var5.next();
                p.onDamageAllEnemies(this.damage);
            }

            int resonanceAmount = 0;

            for(i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
                AbstractMonster target = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                    target.damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                    if (target.lastDamageTaken > 0) {
                        resonanceAmount += target.lastDamageTaken;
                        /*
                        for(int j = 0; j < target.lastDamageTaken / 2 && j < 10; ++j) {
                            this.addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                        }
                         */
                    }
                }
            }

            if (resonanceAmount > 0) {
                if (!Settings.FAST_MODE) {
                    this.addToBot(new WaitAction(0.3F));
                }

                //this.addToBot(new HealAction(this.source, this.source, resonanceAmount));

                this.addToBot(
                        new ApplyPowerAction(this.source, this.source, new VirtuosaResonancePower(this.source, resonanceAmount)));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            this.addToTop(new WaitAction(0.1F));
        }

    }
}
