package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.FillHandWithCopiesAction;
import theVirtuosa.actions.FillHandWithSpecificCardAction;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.characters.TheVirtuosaCharacter;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaReverberatingStrike extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Deal x damage. Fill your hand with Spectral copies of this card.
     *  -> Add a Spectral copy of this card to your hand.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaReverberatingStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String COPY_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    private boolean isCopy;
    // /STAT DECLARATION/


    public VirtuosaReverberatingStrike() {
        this(false);
    }

    public VirtuosaReverberatingStrike(boolean isCopy){
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;

        this.tags.add(CardTags.STRIKE);

        this.isCopy = isCopy;
        if (this.isCopy) {
            this.rawDescription = COPY_DESCRIPTION;
            CardModifierManager.addModifier(this, new VirtuosaSpectralMod());
        }
        else {
            this.cardsToPreview = new VirtuosaReverberatingStrike(true);
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        if (!this.isCopy)
        {
            AbstractCard spectralCopy = this.makeSpectralCopy();
            this.addToBot(new FillHandWithSpecificCardAction(spectralCopy));
        }

        /*
        AbstractCard spectralCopy = this.makeStatEquivalentCopy();
        CardModifierManager.addModifier(spectralCopy, new VirtuosaSpectralMod());

        this.addToBot(new MakeTempCardInHandAction(spectralCopy));


         */
        // this.addToBot(new FillHandWithSpecificCardAction(spectralCopy));
        // this.addToBot(new FillHandWithCopiesAction(this, new SpectralReverberatingStrike()));
    }

    private AbstractCard makeSpectralCopy(){
        AbstractCard copy = new VirtuosaReverberatingStrike(true);

        copy.name = this.name;
        copy.target = this.target;
        copy.upgraded = this.upgraded;
        copy.timesUpgraded = this.timesUpgraded;
        copy.baseDamage = this.baseDamage;
        copy.baseBlock = this.baseBlock;
        copy.baseMagicNumber = this.baseMagicNumber;
        copy.cost = this.cost;
        copy.costForTurn = this.costForTurn;
        copy.isCostModified = this.isCostModified;
        copy.isCostModifiedForTurn = this.isCostModifiedForTurn;
        copy.inBottleLightning = this.inBottleLightning;
        copy.inBottleFlame = this.inBottleFlame;
        copy.inBottleTornado = this.inBottleTornado;
        copy.isSeen = this.isSeen;
        copy.isLocked = this.isLocked;
        copy.freeToPlayOnce = this.freeToPlayOnce;

        return copy;
    }

    @Override
    public AbstractCard makeCopy() {
        if (this.isCopy)
        {
            return this.makeSpectralCopy();
        }
        return new VirtuosaReverberatingStrike();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            if (this.isCopy) { this.rawDescription = COPY_DESCRIPTION;}
            initializeDescription();
        }
    }
}