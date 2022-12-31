package theVirtuosa.powers;

import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.util.TextureLoader;

public class VirtuosaSylvanFormPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TheVirtuosa.makeID("VirtuosaSylvanFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // TODO sort description in strings

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theVirtuosaResources/images/powers/sidheForm_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theVirtuosaResources/images/powers/sidheForm_power32.png");

    private int initialCardDrawPerTurn;

    public VirtuosaSylvanFormPower(final AbstractCreature owner, final int amount) {
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

    public void onInitialApplication() {
        // change max hand size
        this.initialCardDrawPerTurn = AbstractDungeon.player.masterHandSize;
        BaseMod.MAX_HAND_SIZE = amount;
        AbstractDungeon.player.masterHandSize = amount;
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        this.addToBot(new DrawCardAction(this.owner, 1));
    }

    public void onRemove() {
        AbstractDungeon.player.masterHandSize = initialCardDrawPerTurn;
        BaseMod.MAX_HAND_SIZE = BaseMod.DEFAULT_MAX_HAND_SIZE;
    }

    public void onVictory() {
        this.onRemove();
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VirtuosaSylvanFormPower(owner, amount);
    }
}
