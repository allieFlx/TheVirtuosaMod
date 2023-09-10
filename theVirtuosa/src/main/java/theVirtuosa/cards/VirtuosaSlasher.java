package theVirtuosa.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.effects.SlasherEffect;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaSlasher extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Deal !D! damage to ALL enemies 2 (3) times.
     */

    // TODO: fix vfx xy positioning

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaSlasher.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;


    // /STAT DECLARATION/


    public VirtuosaSlasher() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean flip = false;
        AbstractMonster tmp = AbstractDungeon.getCurrRoom().monsters.monsters.get(0);
        for (int i = 0; i < magicNumber; i++) {
            this.addToBot(new VFXAction(new SlasherEffect(tmp.hb.cX, tmp.hb.cY, Color.SCARLET, Color.RED, flip), 0.0F));
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
            flip = !flip;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
