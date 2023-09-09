package theVirtuosa.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.interfaces.OnRevealCard;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaShadowBlade extends AbstractDynamicCard implements OnRevealCard {

    /*
     Shadow Strike - Deal x true damage. (When Revealed, add a Spectral copy of this to your hand.)
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaShadowBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 0;
    private static final int MAGIC = 7;

    // /STAT DECLARATION/

    public VirtuosaShadowBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void onRevealed() {
        if (this.upgraded) {
            AbstractCard specCopy = this.makeStatEquivalentCopy();
            CardModifierManager.addModifier(specCopy, new VirtuosaSpectralMod());
            this.addToTop(new MakeTempCardInHandAction(specCopy));
        }
    }

    @Override
    public float revealEffectDelay() {
        return this.upgraded ? 0.2f : 0f;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
