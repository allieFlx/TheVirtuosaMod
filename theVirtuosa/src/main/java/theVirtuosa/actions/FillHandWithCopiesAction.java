package theVirtuosa.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class FillHandWithCopiesAction extends AbstractGameAction {
    private AbstractCard srcCard;
    private AbstractCard cpyCard;
    private boolean spectral;

    public FillHandWithCopiesAction(AbstractCard srcCard, AbstractCard cpyCard) {
        this.srcCard = srcCard;
        this.cpyCard = cpyCard;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        // create spectral copies
        int copies = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.group.size();

        if (copies <= 0) {
            this.isDone = true;
            return;
        }

        AbstractCard c = this.cpyCard.makeCopy();

        for(int i = 0; i < srcCard.timesUpgraded; ++i) {
            c.upgrade();
        }

        c.name = srcCard.name;
        c.target = srcCard.target;
        c.upgraded = srcCard.upgraded;
        c.timesUpgraded = srcCard.timesUpgraded;
        c.baseDamage = srcCard.baseDamage;
        c.baseBlock = srcCard.baseBlock;
        c.baseMagicNumber = srcCard.baseMagicNumber;
        c.cost = srcCard.cost;
        c.costForTurn = srcCard.costForTurn;
        c.isCostModified = srcCard.isCostModified;
        c.isCostModifiedForTurn = srcCard.isCostModifiedForTurn;
        c.isSeen = srcCard.isSeen;
        c.isLocked = srcCard.isLocked;
        c.misc = srcCard.misc;
        c.freeToPlayOnce = srcCard.freeToPlayOnce;

        this.addToBot(new MakeTempCardInHandAction(c, copies));

        this.isDone = true;

        this.tickDuration();
    }
}
