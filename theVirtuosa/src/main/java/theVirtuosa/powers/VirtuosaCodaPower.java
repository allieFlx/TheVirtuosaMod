package theVirtuosa.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.util.TextureLoader;

public class VirtuosaCodaPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheVirtuosa.makeID("VirtuosaCodaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // TODO sort description in strings

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theVirtuosaResources/images/powers/coda_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVirtuosaResources/images/powers/coda_power32.png");

    public VirtuosaCodaPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

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
    // TODO must also patch AbstractPlayer class (onCardDrawOrDiscard()) to set costs to 0
    }

    /* old version
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1), -1));
        }
    }
     */

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EndTurnDeathPower(this.owner)));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VirtuosaCodaPower(owner);
    }
}
