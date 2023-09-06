package theVirtuosa.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.OmniscienceAction;
import com.megacrit.cardcrawl.cards.purple.Omniscience;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.ExhaustSpecificPileAction;
import theVirtuosa.actions.FomorianEyeAction;

import static theVirtuosa.TheVirtuosa.makeCardPath;
@AutoAdd.Ignore
public class ChoiceVisionsOfRuin extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *
     * Play a card from your discard pile.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(ChoiceVisionsOfRuin.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;
    private static AbstractPlayer p;


    // /STAT DECLARATION/


    public ChoiceVisionsOfRuin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        p = AbstractDungeon.player;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        this.addToBot(new FomorianEyeAction(AbstractDungeon.player.discardPile));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}