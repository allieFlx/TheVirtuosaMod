package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.DamageAllEnemiesForResonanceAction;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.effects.TollTheBellEffect;
import theVirtuosa.patches.CustomTags;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaTollTheBell extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  "Deal 4 (5) damage to ALL enemies. Gain Resonance equal to unblocked damage."
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaTollTheBell.class.getSimpleName());
    public static final String IMG = makeCardPath("VirtuosaTollTheBell_BETA.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 3;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/


    public VirtuosaTollTheBell() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

        this.isMultiDamage = true;
        this.tags.add(CustomTags.GAIN_RESONANCE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new TollTheBellEffect()));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesForResonanceAction(
                p,
                this.multiDamage,
                this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        ));
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