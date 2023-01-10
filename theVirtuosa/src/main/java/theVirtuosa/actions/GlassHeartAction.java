package theVirtuosa.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.characters.TheVirtuosaCharacter;

import java.util.ArrayList;
import java.util.Iterator;

public class GlassHeartAction extends AbstractGameAction
{
    public GlassHeartAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            ArrayList<AbstractCard> cards = this.generateCards();
            cards.forEach(c -> {
                CardModifierManager.addModifier(c, new VirtuosaSpectralMod());
                this.addToBot(new MakeTempCardInHandAction(c));
            });

            this.isDone = true;
            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCards(){
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        ArrayList<AbstractCard.CardColor> cardColors = new ArrayList<>();
        cardColors.add(TheVirtuosaCharacter.Enums.COLOR_BROWN);

        while(retVal.size() != 3){
            boolean dupe = false;
            AbstractCard.CardRarity cardRarity = AbstractCard.CardRarity.RARE;

            AbstractCard tmp = CardLibrary.getAnyColorCard(cardRarity);
            Iterator var6 = retVal.iterator();

            while(var6.hasNext()) {
                AbstractCard c = (AbstractCard)var6.next();
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe && !cardColors.contains(tmp.color)) {
                retVal.add(tmp.makeCopy());
                cardColors.add(tmp.color);
            }
        }
        return retVal;
    }
}
