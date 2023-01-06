package theVirtuosa.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theVirtuosa.TheVirtuosa;

public class VirtuosaDoomedPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheVirtuosa.makeID("VirtuosaDoomedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VirtuosaDoomedPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.DEBUFF;
        this.amount = -1;

        updateDescription();
        this.loadRegion("end_turn_death");
    }

    public void atStartOfTurn() {
        this.flash();
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL", -0.2F));
        this.addToBot(new VFXAction(new CollectorCurseEffect(this.owner.hb.cX, this.owner.hb.cY), 1.0F));
        this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "VirtuosaDoomedPower"));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VirtuosaDoomedPower(owner);
    }
}
