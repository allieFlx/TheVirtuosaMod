package theVirtuosa.cards;

import basemod.AutoAdd;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.ExorcismAction;

import static theVirtuosa.TheVirtuosa.makeCardPath;

@AutoAdd.Ignore
public class SpecialBloodmothForm extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Dummy card: Whenever an Attack deals unblocked damage, Heal 1 HP.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpecialBloodmothForm.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/

    public SpecialBloodmothForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    //Upgraded stats.

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upgrade() {
    }
}