package theVirtuosa.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class TransformSpecificCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard card;
    private AbstractCard replacement;
    private CardGroup group;

    public TransformSpecificCardAction(AbstractCard card, CardGroup group, AbstractCard replacement) {

        this.p = AbstractDungeon.player;
        this.card = card;
        this.group = group;
        this.replacement = replacement;
        this.actionType = ActionType.CARD_MANIPULATION;

        if (Settings.FAST_MODE) {
            this.startDuration = 0.05F;
        } else {
            this.startDuration = 0.15F;
        }

        this.duration = this.startDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractCard target = (AbstractCard)this.group.getSpecificCard(this.card);
            int index = this.group.group.indexOf(this.card);

            if (this.group == this.p.hand)
            {
                this.replacement.current_x = target.current_x;
                this.replacement.current_y = target.current_y;
                this.replacement.target_x = target.target_x;
                this.replacement.target_y = target.target_y;
                this.replacement.drawScale = 1.0F;
                this.replacement.targetDrawScale = target.targetDrawScale;
                this.replacement.angle = target.angle;
                this.replacement.targetAngle = target.targetAngle;
                this.replacement.superFlash(Color.WHITE.cpy());
            }
            this.group.group.set(index, replacement);
            this.group.glowCheck();
        }

        this.tickDuration();
    }
}
