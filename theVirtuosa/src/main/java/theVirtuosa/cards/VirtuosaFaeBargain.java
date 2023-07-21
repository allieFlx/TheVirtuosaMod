package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.characters.TheVirtuosaCharacter;

import java.util.ArrayList;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaFaeBargain extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *
     * Choose one: Exhaust your discard pile, your hand, or your draw pile. Gain 1 Block for each card Exhausted. Exhaust.
     */

    // TEXT DECLARATION

    // TODO: bug / interaction
    //  Choosing No Present after playing Determination discards the hand but still gains block
    //  that would have been gained from exhausting it - feature or fix?

    public static final String ID = TheVirtuosa.makeID(VirtuosaFaeBargain.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] extendedDescription = cardStrings.EXTENDED_DESCRIPTION;

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 3;
    private static final int BLOCK = 3;


    // /STAT DECLARATION/


    public VirtuosaFaeBargain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;

        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> bargainChoices = new ArrayList<>();

        AbstractCard opt1 = new BargainNoPast();
        AbstractCard opt2 = new BargainNoPresent();
        AbstractCard opt3 = new BargainNoFuture();

        opt1.applyPowers();
        opt2.applyPowers();
        opt3.applyPowers();

        bargainChoices.add(opt3); // No Future
        bargainChoices.add(opt2); // No Present
        bargainChoices.add(opt1); // No Past

        this.addToBot(new ChooseOneAction(bargainChoices));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            initializeDescription();
        }
    }
}
