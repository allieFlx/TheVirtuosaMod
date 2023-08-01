package theVirtuosa.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.patches.VirtuosaCodaApplyPowerPatch;

import java.util.Iterator;

public class FomorianEyeAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(FomorianEyeAction.class.getName());
    public static final String[] TEXT;
    private CardGroup group;
    public FomorianEyeAction(CardGroup group) {
        this.group = group;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        logger.info("Init Fomarian Eye");
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.group.isEmpty()) {
                logger.info("Fomo Eye: Group was empty");
                this.isDone = true;
            } else {
                logger.info("Fomo Eye: select cards");
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                Iterator var6 = this.group.group.iterator();

                while(var6.hasNext()) {
                    AbstractCard c = (AbstractCard)var6.next();
                    temp.addToTop(c);
                }

                temp.sortAlphabetically(true);
                temp.sortByRarityPlusStatusCardType(false);
                AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                logger.info("Fomo Eye: card selected");
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    c.exhaust = true;
                    this.group.group.remove(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    this.addToBot(new NewQueueCardAction(c, true, false, true));
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        // TODO: ui string for play from pile
        TEXT = CardCrawlGame.languagePack.getUIString("WishAction").TEXT;
    }
}
