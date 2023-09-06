package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class SpecialLostInTheWoods extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Exhaust. After you play non-Curse card, shuffle a copy of this into your draw pile.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpecialLostInTheWoods.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public SpecialLostInTheWoods() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    //Upgraded stats.


    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        if (c.type != CardType.CURSE) {
            this.flash();
            this.addToBot(new MakeTempCardInDrawPileAction(this.makeCopy(), 1, true, true));
        }
    }

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upgrade() {
    }
}