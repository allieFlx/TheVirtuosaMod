package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.ForeignInfluence;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.GlassHeartAction;
import theVirtuosa.characters.TheVirtuosaCharacter;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaGlassHeart extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Add 3 Rare cards of different colours to your hand with Spectral. Exhaust.
     */


    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaGlassHeart.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;


    // /STAT DECLARATION/


    public VirtuosaGlassHeart() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GlassHeartAction());
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
