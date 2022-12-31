package theVirtuosa.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.patches.DefaultInsertPatch;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustSpecificPileAction extends AbstractGameAction {
    private CardGroup group;
    private float startingDuration;

    private static final Logger logger = LogManager.getLogger(ExhaustSpecificPileAction.class.getName());

    public ExhaustSpecificPileAction(CardGroup group) {
        this.actionType = ActionType.EXHAUST;
        this.group = group;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    @Override
    public void update() {

        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.duration == this.startingDuration && !this.group.isEmpty()) {
                ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
                this.group.group.forEach(
                        c -> {
                            logger.info("Adding " + c.name + " to exhaust list.");
                            //this.group.moveToExhaustPile(c);
                            c.exhaustOnUseOnce = false;
                            c.freeToPlayOnce = false;
                            cardsToExhaust.add(c);
                        }
                );
                cardsToExhaust.forEach(
                        c -> {
                            logger.info("Exhausting " + c.name);
                            this.group.moveToExhaustPile(c);
                        }
                );
            }
        }
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.duration == this.startingDuration && !this.group.isEmpty()) {
                ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
                this.group.group.forEach(
                        c -> {
                            logger.info("Adding " + c.name + " to exhaust list.");
                            //this.group.moveToExhaustPile(c);
                            c.exhaustOnUseOnce = false;
                            c.freeToPlayOnce = false;
                            cardsToExhaust.add(c);
                        }
                );
                cardsToExhaust.forEach(
                        c -> {
                            logger.info("Exhausting " + c.name);
                            this.group.moveToExhaustPile(c);
                        }
                );
            }
        }
        this.tickDuration();
    }
}
