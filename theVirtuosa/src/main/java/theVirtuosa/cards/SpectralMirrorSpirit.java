package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaMirrorSpiritMod;

import static theVirtuosa.TheVirtuosa.makeCardPath;
@NoCompendium
@NoPools
public class SpectralMirrorSpirit extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Spectral. Unplayable. Copies the last card played this turn.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpectralMirrorSpirit.class.getSimpleName());
    public static final String IMG = makeCardPath("SpectralMirrorSpirit.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    // /STAT DECLARATION/


    public SpectralMirrorSpirit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        CardModifierManager.addModifier(this, new VirtuosaMirrorSpiritMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    //Upgraded stats.

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upgrade() {
    }
}