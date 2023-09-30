package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.ShadowStepAction;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.interfaces.OnRevealCard;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaShadowStep extends AbstractDynamicCard implements OnRevealCard {

    /*
     Shadow Step - Put the top card of your discard pile into your hand. (When Revealed, add a Spectral copy of this to your hand.)
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaShadowStep.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 0;

    // /STAT DECLARATION/

    public VirtuosaShadowStep() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ShadowStepAction(p));
    }

    public void onRevealed() {
        if (this.upgraded) {
            AbstractCard specCopy = this.makeStatEquivalentCopy();
            CardModifierManager.addModifier(specCopy, new VirtuosaSpectralMod());
            this.addToTop(new MakeTempCardInHandAction(specCopy));
        }
    }

    @Override
    public float revealEffectDelay() {
        return this.upgraded ? 0.2f : 0f;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
