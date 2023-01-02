package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.powers.VirtuosaCodaPower;
import java.util.Iterator;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaCoda extends AbstractDynamicCard{

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Attacks cost 0. [When you play an Attack, lose 1 Strength] -> [Die in 2 turns.]
     */
    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaCoda.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    // /STAT DECLARATION/


    public VirtuosaCoda() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        GraveField.grave.set(this, true); // << this is how you do stslib keywords. no need to prefix in strings json
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        boolean powerExists = false;
        Iterator var4 = p.powers.iterator();

        while(var4.hasNext()) {
            AbstractPower pow = (AbstractPower)var4.next();
            if (pow.ID.equals("VirtuosaCodaPower")) {
                powerExists = true;
                break;
            }
        }

        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(p, p, new VirtuosaCodaPower(p)));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}