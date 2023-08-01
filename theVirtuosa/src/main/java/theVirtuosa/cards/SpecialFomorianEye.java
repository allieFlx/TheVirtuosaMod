package theVirtuosa.cards;

import basemod.AutoAdd;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.ExorcismAction;
import theVirtuosa.characters.TheVirtuosaCharacter;

import java.util.ArrayList;

import static theVirtuosa.TheVirtuosa.makeCardPath;
public class SpecialFomorianEye extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Retain NL Choose one: Play a card from your draw pile, discard pile, or exhaust pile. Purge.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(SpecialFomorianEye.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 0;

    // /STAT DECLARATION/


    public SpecialFomorianEye() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.retain = true; // upgrade only
        PurgeField.purge.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> eyeChoices = new ArrayList<>();

        AbstractCard opt1 = new ChoiceVisionsOfRuin();
        AbstractCard opt2 = new ChoiceVisionsOfFlame();
        AbstractCard opt3 = new ChoiceVisionsOfEternity();

        opt1.applyPowers();
        opt2.applyPowers();
        opt3.applyPowers();

        eyeChoices.add(opt3); // Eternity
        eyeChoices.add(opt2); // Flame
        eyeChoices.add(opt1); // Ruin

        this.addToBot(new ChooseOneAction(eyeChoices));
    }

    //Upgraded stats.

    @Override
    public boolean canUpgrade() { return false; }

    @Override
    public void upgrade() {
    }
}