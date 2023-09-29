package theVirtuosa.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.RevealCardsAction;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeID;

public class VirtuosaAmbushPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheVirtuosa.makeID("VirtuosaAmbushPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theVirtuosaResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVirtuosaResources/images/powers/placeholder_power32.png");
    private boolean isNextTurn = false;

    public VirtuosaAmbushPower(final AbstractCreature owner, int amount) {
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
        String tmp = this.isNextTurn ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        this.description = DESCRIPTIONS[0] + tmp + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
    }

    @Override
    public void atStartOfTurn() {
        this.isNextTurn = true;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if (this.isNextTurn) {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (this.isNextTurn){
            return type == DamageInfo.DamageType.NORMAL ? damage + (float)this.amount : damage;
        }
        return super.atDamageGive(damage, type);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (this.isNextTurn && card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new VirtuosaAmbushPower(owner, amount);
    }
}
