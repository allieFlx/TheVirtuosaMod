package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.powers.VirtuosaCodaPower;

import java.util.Iterator;

@SpirePatch(
        clz = ApplyPowerAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez={
                AbstractCreature.class,
                AbstractCreature.class,
                AbstractPower.class,
                int.class,
                boolean.class,
                AbstractGameAction.AttackEffect.class
        }
)
public class VirtuosaCodaApplyPowerPatch {
    private static final Logger logger = LogManager.getLogger(VirtuosaCodaApplyPowerPatch.class.getName());

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"powerToApply"}
    )
    public static void Insert(AbstractPower powerToApply) {

        if (powerToApply.ID.equals(VirtuosaCodaPower.POWER_ID)) {
            logger.info("CODA apply power patch triggered.");
            Iterator var8 = AbstractDungeon.player.hand.group.iterator();

            AbstractCard c;
            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.modifyCostForCombat(-9);
                }
            }

            var8 = AbstractDungeon.player.drawPile.group.iterator();

            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.modifyCostForCombat(-9);
                }
            }

            var8 = AbstractDungeon.player.discardPile.group.iterator();

            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.modifyCostForCombat(-9);
                }
            }

            var8 = AbstractDungeon.player.exhaustPile.group.iterator();

            while(var8.hasNext()) {
                c = (AbstractCard) var8.next();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    c.modifyCostForCombat(-9);
                }
            }
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

            Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterGroup.class, "areMonstersBasicallyDead");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

        }
    }
}
