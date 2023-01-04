package theVirtuosa.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FillHandWithSpecificCardAction extends AbstractGameAction {
    private AbstractCard card;

    public FillHandWithSpecificCardAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        int copies = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.group.size();

        if (copies <= 0) {
            this.isDone = true;
            return;
        }

        this.addToTop(new MakeTempCardInHandAction(this.card, copies));

        this.isDone = true;

        this.tickDuration();
    }
}
