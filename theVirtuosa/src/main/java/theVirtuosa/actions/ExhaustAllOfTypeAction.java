package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;

import java.util.Iterator;

public class ExhaustAllOfTypeAction extends AbstractGameAction
{
    private AbstractCard.CardType cardType;
    private CardGroup cardGroup;
    public ExhaustAllOfTypeAction(AbstractCard.CardType cardType, CardGroup cardGroup) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.cardType = cardType;
        this.cardGroup = cardGroup;
    }

    @Override
    public void update() {
        Iterator<AbstractCard> var1 = this.cardGroup.group.iterator();
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        while(var1.hasNext())
        {
            AbstractCard c = var1.next();
            if (c.type == this.cardType)
            {
                temp.addToTop(c);
            }
        }

        var1 = temp.group.iterator();

        while(var1.hasNext())
        {
            AbstractCard c = var1.next();
            this.cardGroup.moveToExhaustPile(c);
        }

        this.isDone = true;
    }
}
