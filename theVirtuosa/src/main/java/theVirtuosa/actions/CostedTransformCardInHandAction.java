package theVirtuosa.actions;

import basemod.devcommands.hand.HandRemove;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Discovery;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class CostedTransformCardInHandAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard card;
    private int cost;

    public CostedTransformCardInHandAction(AbstractCard card, int targetCost) {

        this.p = AbstractDungeon.player;
        this.card = card;
        this.cost = targetCost;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        AbstractCard newCard = getTransformedCard();

        this.addToTop(new TransformSpecificCardAction(card, p.hand, newCard));

        this.isDone = true;

        this.tickDuration();
    }

    private AbstractCard getTransformedCard(){
        int targetCost = this.cost;
        if (targetCost < 0 && this.card.cardID != "Necronomicurse") {
            // targetCost = 0;
            return new VoidCard();
        }
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var3;
        AbstractCard c;
        Random rng = new Random();

        switch (this.card.color) {
            case COLORLESS:
                if (this.card.type == AbstractCard.CardType.STATUS)
                {
                    /*
                    list.add(new Burn());
                    list.add(new Dazed());
                    list.add(new Slimed());
                    list.add(new VoidCard());
                    list.add(new Wound());

                    list.removeIf(status -> status.cardID == this.card.cardID);

                    return ((AbstractCard)list.get(rng.random(list.size() - 1))).makeCopy();

                     */

                    return new VoidCard();
                }
                else
                {
                    var3 = AbstractDungeon.colorlessCardPool.group.iterator();

                    while(var3.hasNext()) {
                        c = (AbstractCard)var3.next();
                        if (!Objects.equals(c.cardID, this.card.cardID) && c.cost == targetCost && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                            list.add(c);
                        }
                    }

                    return ((AbstractCard)list.get(rng.random(list.size() - 1))).makeCopy();
                }


            case CURSE:
                if (this.card.cardID == "Necronomicurse") {
                    return this.card;
                }
                else {
                    return CardLibrary.getCurse();
                }
            default:
                var3 = AbstractDungeon.commonCardPool.group.iterator();

                while(var3.hasNext()) {
                    c = (AbstractCard)var3.next();
                    if (!Objects.equals(c.cardID, this.card.cardID) && c.cost == targetCost&& !c.hasTag(AbstractCard.CardTags.HEALING)) {
                        list.add(c);
                    }
                }

                var3 = AbstractDungeon.srcUncommonCardPool.group.iterator();

                while(var3.hasNext()) {
                    c = (AbstractCard)var3.next();
                    if (!Objects.equals(c.cardID, this.card.cardID) && c.cost == targetCost && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                        list.add(c);
                    }
                }

                var3 = AbstractDungeon.srcRareCardPool.group.iterator();

                while(var3.hasNext()) {
                    c = (AbstractCard)var3.next();
                    if (!Objects.equals(c.cardID, this.card.cardID) && c.cost == targetCost && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                        list.add(c);
                    }
                }

                return ((AbstractCard)list.get(rng.random(list.size() - 1))).makeCopy();
        }
    }
}
