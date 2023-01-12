package theVirtuosa.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.CodaRemoveAction;
import theVirtuosa.util.TextureLoader;

public class VirtuosaCodaPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheVirtuosa.makeID("VirtuosaCodaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theVirtuosaResources/images/powers/coda_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVirtuosaResources/images/powers/coda_power32.png");

    public VirtuosaCodaPower(final AbstractCreature owner, int amount) {
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

    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            card.setCostForTurn(-9);
        }
    // must also patch AbstractPlayer class (onCardDrawOrDiscard()) to set costs to 0
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            if (this.amount == 0) {
                // DIE!
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }
        }
        /*
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1));
        }

         */
    }

    @Override
    public void atStartOfTurn() {
        /* intermediate version : "Attacks cost #b0. At the start of your turn, become #yDoomed."

        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new VirtuosaDoomedPower(this.owner)));

         */
    }

    @Override
    public void onRemove() {
        // reset attack costs
        this.addToBot(new CodaRemoveAction());

        // we shouldnt kill the player if they just beat a boss
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) { return; }

        this.addToBot(new SFXAction(TheVirtuosa.makeID("CROAK_SHRILL"), -0.2F));
        this.addToBot(new VFXAction(new CollectorCurseEffect(this.owner.hb.cX, this.owner.hb.cY), 1.0F));
        this.addToBot(new WaitAction(1.0F));
        this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
    }

    @Override
    public void updateDescription() {
        if (amount == 1){
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new VirtuosaCodaPower(owner, amount);
    }
}
