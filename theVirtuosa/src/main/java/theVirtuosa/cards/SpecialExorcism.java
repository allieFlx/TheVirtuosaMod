package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.ExorcismAction;
import theVirtuosa.cardmods.VirtuosaMirrorSpiritMod;

import static theVirtuosa.TheVirtuosa.makeCardPath;
@NoCompendium
public class SpecialExorcism extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Fleeting. Exhaust ALL Curses. Remove ALL debuffs.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpecialExorcism.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;

    // /STAT DECLARATION/


    public SpecialExorcism() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        FleetingField.fleeting.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExorcismAction(p));
    }

    //Upgraded stats.

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upgrade() {
    }
}