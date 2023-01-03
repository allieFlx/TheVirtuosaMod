package theVirtuosa.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Metamorphosis;
import com.megacrit.cardcrawl.cards.purple.JustLucky;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.InkBottle;
import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.RevealCardsAction;
import theVirtuosa.actions.ShowRevealedCardAction;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.effects.ShowCardRevealEffect;

import java.util.Iterator;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaLimbFromLimb extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *  Reveal 3 (4) cards. Deal 8 damage for each attack Revealed.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaLimbFromLimb.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public VirtuosaLimbFromLimb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new RevealCardsAction(magicNumber,
                        c -> c.type == CardType.ATTACK,
                        matches ->
                        {
                            // final slash
                            this.addToTop(new DamageAction(m,
                                    new DamageInfo(p, damage, damageTypeForTurn),
                                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                            // match slashes
                            matches.forEach(match ->
                                    this.addToTop(new DamageAction(m,
                                            new DamageInfo(p, damage, damageTypeForTurn),
                                            AbstractGameAction.AttackEffect.SLASH_DIAGONAL))
                            );
                        },
                        def ->
                        {
                            this.addToTop(new DamageAction(m,
                                    new DamageInfo(p, damage, damageTypeForTurn),
                                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                        }
                )
        );
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