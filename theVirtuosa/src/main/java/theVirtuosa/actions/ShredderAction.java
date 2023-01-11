package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ShredderAction extends AbstractGameAction
{

    private boolean freeToPlayOnce;
    private int damage;
    private int repeat;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageInfo.DamageType damageTypeForTurn;
    private int energyOnUse;
    public ShredderAction(AbstractPlayer p, AbstractMonster m, int damage, int repeat, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.repeat = repeat;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        // damage currently affected by strength

        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.damage + effect > 0) {
            // do attack
            boolean alt = false;
            for (int i = 0; i < repeat; i++)
            {
                if (alt)
                {
                    this.addToBot(
                            new DamageAction(m, new DamageInfo(p, damage + effect, damageTypeForTurn),
                                    AttackEffect.SLASH_DIAGONAL));
                }
                else
                {
                    this.addToBot(
                            new DamageAction(m, new DamageInfo(p, damage + effect, damageTypeForTurn),
                                    AttackEffect.SLASH_VERTICAL));
                }
                alt = !alt;
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }

}
