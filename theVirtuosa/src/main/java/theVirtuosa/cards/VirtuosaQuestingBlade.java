package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.interfaces.OnAddToDeckCard;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaQuestingBlade extends AbstractDynamicCard implements OnAddToDeckCard {

    /*
     Deal X damage. When this is added to your deck, gain 30 (50) gold.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaQuestingBlade.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 1; // 2, 3?
    private static final int DAMAGE = 10;    // average damage for an attack
    private static final int UPGRADE_PLUS_DMG = 2;  // average upgrade
    private static final int MAGIC = 30;    //
    private static final int UPGRADE_MAGIC = 20;
    // ^ this could be quite a bit more as the odds of finding upgraded cards is low

    // gold could be gained on both add and remove

    // /STAT DECLARATION/


    public VirtuosaQuestingBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void onAddToMasterDeck() {
        // play gold gain sound
        CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
        AbstractDungeon.player.gainGold(magicNumber);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
