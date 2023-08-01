package theVirtuosa.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.characters.TheVirtuosaCharacter;

import java.util.ArrayList;
import java.util.Iterator;

public class VorpalSwordAction extends AbstractGameAction
{
    private int magic;
    private DamageInfo info;
    public VorpalSwordAction(AbstractCreature target, DamageInfo info, int magic) {
        this.info = info;
        this.setValues(target, info);
        this.magic = magic;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));
            this.target.damage(this.info);
            if (((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) {
                // do magic shit
                if (((AbstractMonster)this.target).type == AbstractMonster.EnemyType.NORMAL && this.target.hasPower("Minion")) {
                    // do minion effect - energy and card draw
                    this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                    this.addToTop(new GainEnergyAction(1));
                } else if (((AbstractMonster)this.target).type == AbstractMonster.EnemyType.NORMAL && !this.target.hasPower("Minion")) {
                    // do monster effect - heal
                    this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magic));
                } else if (((AbstractMonster)this.target).type == AbstractMonster.EnemyType.ELITE) {
                    // do elite effect - max hp
                    AbstractDungeon.player.increaseMaxHp(this.magic, true);
                    this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.magic));
                } else if (((AbstractMonster)this.target).type == AbstractMonster.EnemyType.BOSS) {
                    // do boss effect - summon leshy
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
