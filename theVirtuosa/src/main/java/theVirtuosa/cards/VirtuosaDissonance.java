package theVirtuosa.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.SpendResonanceAttackAction;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.powers.VirtuosaResonancePower;

import java.util.Iterator;

import static theVirtuosa.TheVirtuosa.makeCardPath;

@AutoAdd.Ignore
@Deprecated
public class VirtuosaDissonance extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Spend Resonance to deal twice the damage to ALL enemies. Exhaust.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaDissonance.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    private static final int DAMAGE = 0;


    // /STAT DECLARATION/


    public VirtuosaDissonance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = DAMAGE;

        this.isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SpendResonanceAttackAction(p, magicNumber, multiDamage));
    }

    public void applyPowers() {
        int amount = this.calcResonanceDamage();

        if (amount > 0) {
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0]
                    + amount + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }

    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0]
                + this.calcResonanceDamage() + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    private int calcResonanceDamage() {
        AbstractPlayer p = AbstractDungeon.player;
        int amount = 0;
        if (p != null && p.hasPower(VirtuosaResonancePower.POWER_ID)){
            amount = this.damage + (p.getPower(VirtuosaResonancePower.POWER_ID).amount * magicNumber);
        }
        return amount;
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