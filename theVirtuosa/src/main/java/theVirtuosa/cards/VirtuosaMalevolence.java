package theVirtuosa.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Rampage;
import com.megacrit.cardcrawl.cards.red.SwordBoomerang;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.RevealCardsAction;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.patches.VirtuosaCodaApplyPowerPatch;

import static theVirtuosa.TheVirtuosa.makeCardPath;

@AutoAdd.Ignore
@Deprecated
public class VirtuosaMalevolence extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     * maleficence
     *  Deal 9 damage. Reveal 3 cards. If none are Skills, double this card's damage this combat.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaMalevolence.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 3;

    // /STAT DECLARATION/


    public VirtuosaMalevolence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
        }
        this.addToBot(
                new RevealCardsAction(
                        magicNumber,
                        c -> c.type == CardType.SKILL,
                        matches ->
                        {
                            // do nothing
                        },
                        def -> {
                            this.addToBot(new ModifyDamageAction(this.uuid, this.baseDamage));
                        }
                )
        );
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