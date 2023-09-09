package theVirtuosa.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.RevealCardsAction;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeID;

public class VirtuosaParryPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheVirtuosa.makeID("VirtuosaParryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVirtuosaResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVirtuosaResources/images/powers/placeholder_power32.png");

    public VirtuosaParryPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (this.amount > 0 && info.type == DamageInfo.DamageType.NORMAL && damageAmount > 0)
        {
            damageAmount = 0;
            --this.amount;
            this.flash();
            this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            this.addToTop(new RevealCardsAction(
                    1,
                    c -> c.type == AbstractCard.CardType.ATTACK,
                    negate -> {
                        CardCrawlGame.sound.playA(makeID("VIRTUOSA_SELECT"), +0.2F);
                        // TODO: vfx
                    },
                    false,
                    def -> this.addToTop(new DamageAction(
                            this.owner,
                            new DamageInfo(info.owner, info.base, DamageInfo.DamageType.THORNS))
                    ),
                    true
            ));
        }

        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new VirtuosaParryPower(owner, amount);
    }
}
