package theVirtuosa.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.RevealCardsAction;
import theVirtuosa.characters.TheVirtuosaCharacter;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaRevealingLight extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     *
     * Reveal 3 (4) cards and put 1 (2) into your hand.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaRevealingLight.class.getSimpleName());
    public static final String IMG = makeCardPath("VirtuosaRevealingLight_BETA.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String PROMPT = cardStrings.EXTENDED_DESCRIPTION[0];

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    private static final int COST = 0;
    private static final int REVEALED = 3;
    private static final int UPGRADE_REVEALED = 1;
    private static final int DRAW = 1;
    private static final int UPGRADE_DRAW = 1;


    // /STAT DECLARATION/


    public VirtuosaRevealingLight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = REVEALED;
        virtuosaBaseSecondMagicNumber = virtuosaSecondMagicNumber = DRAW;

        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: check hand size before adding to hand
        AbstractDungeon.actionManager.addToBottom(
                new RevealCardsAction(magicNumber, c -> true, cardGroup ->
                    this.addToTop(new SelectCardsAction(
                            cardGroup,
                            virtuosaSecondMagicNumber,
                            PROMPT,
                            true,
                            card -> true,
                            cards ->
                            {
                                int iniSize = cardGroup.size();
                                cards.forEach(c -> {
                                    if (p.hand.size() < BaseMod.MAX_HAND_SIZE)
                                    {
                                        p.drawPile.moveToHand(c);
                                        cardGroup.remove(c);
                                    }
                                });
                                if (cardGroup.size() > iniSize - cards.size()) { p.createHandIsFullDialog(); }
                            }
                    ))
                )
        );
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_REVEALED);
            upgradeVirtuosaSecondMagicNumber(UPGRADE_DRAW);
            initializeDescription();
        }
    }
}
