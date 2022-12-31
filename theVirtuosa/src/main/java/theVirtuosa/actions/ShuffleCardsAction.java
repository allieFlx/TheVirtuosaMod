package theVirtuosa.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class ShuffleCardsAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private CardGroup source;
    private Predicate<AbstractCard> predicate;
    private boolean isRandom;

    public ShuffleCardsAction(CardGroup source, int amount, Predicate<AbstractCard> predicate, boolean isRandom) {
        this.p = AbstractDungeon.player;
        this.source = source;
        this.predicate = predicate;
        this.isRandom = isRandom;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public ShuffleCardsAction(CardGroup source, int amount) {
        this(source, amount, c -> true, false);
    }

    // TODO: trigger on shuffle effects
    @Override
    public void update() {
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var5 = this.source.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                if (this.predicate.test(c)) {
                    if (this.source == this.p.drawPile) {
                        tmp.addToRandomSpot(c);
                    } else {
                        tmp.addToTop(c);
                    }
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                if (this.source == this.p.exhaustPile) {
                    card.unfadeOut();
                }

                card.untip();
                card.unhover();
                card.lighten(true);
                card.setAngle(0.0F);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.current_x = CardGroup.DRAW_PILE_X;
                card.current_y = CardGroup.DRAW_PILE_Y;
                this.source.removeCard(card);
                this.p.drawPile.addToRandomSpot(card);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();

                this.isDone = true;
            } else if (tmp.size() > this.amount) {
                /*
                if (this.sortCards) {
                    tmp.sortAlphabetically(true);
                    tmp.sortByRarityPlusStatusCardType(true);
                }
                 */
                if (!this.isRandom)
                {
                    if (this.source == this.p.hand && tmp.size() == this.p.hand.size()) {
                        AbstractDungeon.handCardSelectScreen.open(this.makeText(true), this.amount, false);
                    }
                    else {
                        AbstractDungeon.gridSelectScreen.open(tmp, this.amount, this.makeText(false), false);
                    }
                    this.tickDuration();
                    return;
                }

                for(int i = 0; i < this.amount; ++i) {
                    card = this.source.getRandomCard(AbstractDungeon.cardRandomRng);

                    card.untip();
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    this.source.removeCard(card);
                    this.p.drawPile.addToRandomSpot(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }

                this.isDone = true;
            } else {

                for(int i = 0; i < tmp.size(); ++i) {
                    card = tmp.getNCardFromTop(i);
                    if (this.source == this.p.exhaustPile) {
                        card.unfadeOut();
                    }

                    card.untip();
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    this.source.removeCard(card);
                    this.p.drawPile.addToRandomSpot(card);
                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                this.isDone = true;
            }
        } else {
            if (this.source != this.p.hand && AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var3 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var3.hasNext()) {
                    card = (AbstractCard)var3.next();
                    card.untip();
                    card.unhover();
                    if (this.source == this.p.exhaustPile) {
                        card.unfadeOut();
                    }

                    this.source.removeCard(card);
                    this.p.drawPile.addToRandomSpot(card);

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            } else if (this.source == this.p.hand && !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while(var4.hasNext()) {
                    card = (AbstractCard)var4.next();
                    card.untip();
                    card.unhover();

                    this.source.removeCard(card);
                    this.p.drawPile.addToRandomSpot(card);

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    private String makeText(boolean fromHand)
    {
        if (fromHand)
        {
            return TEXT[2];
        }

        String ret;
        if (this.amount == 1) {
            ret = TEXT[0];
        } else {
            ret = TEXT[1];
        }

        return String.format(ret, TEXT[3]);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("theVirtuosa:ShuffleCardsAction");
        TEXT = uiStrings.TEXT;
    }
}
