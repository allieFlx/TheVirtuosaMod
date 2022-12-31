package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.purple.SpiritShield;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.ExhaustSpecificPileAction;
import theVirtuosa.characters.TheVirtuosaCharacter;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class BargainNoFuture extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *
     * Exhaust your draw pile.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(BargainNoFuture.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int BLOCK = 3;
    private static AbstractPlayer p;
    private int totalBlock;
    private boolean appliedPowers;


    // /STAT DECLARATION/


    public BargainNoFuture() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        p = AbstractDungeon.player;
        baseBlock = block = BLOCK;
        this.appliedPowers = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificPileAction(p.drawPile));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, totalBlock));
    }

    @Override
    public void applyPowers() {
        if (!this.appliedPowers)
        {
            super.applyPowers();
            this.appliedPowers = true;
        }
        this.baseBlock = block;
        this.totalBlock = block * p.drawPile.size();

        this.rawDescription = this.rawDescription + VirtuosaFaeBargain.extendedDescription[0]
                + totalBlock + VirtuosaFaeBargain.extendedDescription[1];
        this.initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}
