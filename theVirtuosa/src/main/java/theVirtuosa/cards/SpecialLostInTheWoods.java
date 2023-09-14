package theVirtuosa.cards;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.interfaces.OnRevealCard;

import static theVirtuosa.TheVirtuosa.makeCardPath;
@NoCompendium
public class SpecialLostInTheWoods extends AbstractDynamicCard implements OnRevealCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Exhaust. After you play non-Curse card, shuffle a copy of this into your draw pile.
     *  alt: Unplayable. When Revealed, shuffle a copy of this into your draw pile.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpecialLostInTheWoods.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.STATUS;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    // /STAT DECLARATION/


    public SpecialLostInTheWoods() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) { return false; }

    @Override
    public void onRevealed() {
        this.addToTop(new MakeTempCardInDrawPileAction(this, 1, true, true));
    }

    //Upgraded stats.

    @Override
    public float revealEffectDelay() {
        return 0.2F;
    }

    /*
    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);
        if (c.type != CardType.CURSE) {
            this.flash();
            this.addToBot(new MakeTempCardInDrawPileAction(this.makeCopy(), 1, true, true));
        }
    }
     */

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upgrade() {
    }
}