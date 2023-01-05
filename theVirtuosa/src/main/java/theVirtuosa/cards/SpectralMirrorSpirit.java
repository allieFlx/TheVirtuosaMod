package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.patches.CustomTags;
import theVirtuosa.powers.VirtuosaResonancePower;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class SpectralMirrorSpirit extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Spectral. Unplayable. Copies the last card played this turn.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpectralMirrorSpirit.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private AbstractCard cardToCopy = null;

    // /STAT DECLARATION/


    public SpectralMirrorSpirit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        CardModifierManager.addModifier(this, new VirtuosaSpectralMod());
    }

    // Actions the card should do.

    public void use(AbstractPlayer p, AbstractMonster m) {
        // play the card stored as a copy, otherwise do nothing
        if (this.cardToCopy != null)
        {
            this.cardToCopy.use(p, m);
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        // if there has been a card played this turn, return true, otherwise false
        return this.cardToCopy != null;
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        // if this card is upgraded, copy an upgraded version of the card
        this.cardToCopy = c.makeStatEquivalentCopy();
        // change the appearance of this card to match / change description
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // upgrade the copy
            initializeDescription();
        }
    }
}