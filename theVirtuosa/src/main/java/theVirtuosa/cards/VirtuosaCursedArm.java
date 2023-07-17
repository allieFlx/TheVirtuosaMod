package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.cards.blue.SteamBarrier;
import com.megacrit.cardcrawl.cards.blue.Streamline;
import com.megacrit.cardcrawl.cards.purple.Tantrum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.characters.TheVirtuosaCharacter;

import java.util.Iterator;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaCursedArm extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Ethereal. Deal 8 (12) damage. Shuffle this card into your draw pile.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaCursedArm.class.getSimpleName());
    public static final String IMG = makeCardPath("VirtuosaCursedArm_BETA.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/


    public VirtuosaCursedArm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        this.isEthereal = true;
        this.shuffleBackIntoDrawPile = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}