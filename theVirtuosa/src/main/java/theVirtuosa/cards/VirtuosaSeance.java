package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.powers.VirtuosaParryPower;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaSeance extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Put a (2) Spectral copy (copies) of the top card of your draw pile into your hand.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaSeance.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;



    // /STAT DECLARATION/


    public VirtuosaSeance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard topCard = p.drawPile.getTopCard();
        CardModifierManager.addModifier(topCard, new VirtuosaSpectralMod());
        this.addToBot(new MakeTempCardInHandAction(topCard, magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
