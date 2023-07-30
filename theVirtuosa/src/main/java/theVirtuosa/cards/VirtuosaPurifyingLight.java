package theVirtuosa.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.RevealCardsAction;
import theVirtuosa.characters.TheVirtuosaCharacter;

import java.util.ArrayList;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaPurifyingLight extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *
     * Reveal 3 cards. Exhaust 1 (Any number) and put the rest into your hand.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaPurifyingLight.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final Logger logger = LogManager.getLogger(VirtuosaPurifyingLight.class.getName());
    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String PROMPT = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String UPGRADE_PROMPT = cardStrings.EXTENDED_DESCRIPTION[1];
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int REVEALED = 3;

    public VirtuosaPurifyingLight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = REVEALED;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new RevealCardsAction(magicNumber, c -> true, cardGroup ->
                    this.addToTop(new SelectCardsAction(
                            cardGroup,
                            this.upgraded ? cardGroup.size() : 1,
                            this.upgraded ? UPGRADE_PROMPT : PROMPT,
                            this.upgraded,
                            card -> true,
                            cards ->
                            {
                                logger.info("PURIFYING LIGHT: Begin Callback");
                                cards.forEach(c -> {
                                    logger.info("Exhausting Selection");
                                    p.drawPile.moveToExhaustPile(c);
                                    cardGroup.remove(c);
                                });
                                // --
                                int iniSize = cardGroup.size();
                                ArrayList<AbstractCard> drawCards = new ArrayList<>(cardGroup);
                                logger.info("Putting rest into hand.");
                                drawCards.forEach(c -> {
                                    if (p.hand.size() < BaseMod.MAX_HAND_SIZE)
                                    {
                                        p.drawPile.moveToHand(c);
                                        cardGroup.remove(c);
                                    }
                                });
                                if (cardGroup.size() > iniSize - cards.size()) { p.createHandIsFullDialog(); }
                            }
                    )),
                        true
                )
        );
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
