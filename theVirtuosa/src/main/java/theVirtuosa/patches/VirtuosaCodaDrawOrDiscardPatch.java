package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.powers.VirtuosaCodaPower;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "onCardDrawOrDiscard"
)
public class VirtuosaCodaDrawOrDiscardPatch {
    private static final Logger logger = LogManager.getLogger(VirtuosaCodaDrawOrDiscardPatch.class.getName());

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {} // "var1"
    )
    public static void Insert(AbstractPlayer __instance) { // @ByRef Iterator[] ___var1
        // ___var1[0]

        if (__instance.hasPower(VirtuosaCodaPower.POWER_ID)) {
            Iterator var2  = __instance.hand.group.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();

                if (c.type == AbstractCard.CardType.ATTACK && c.costForTurn != 0) {
                    c.modifyCostForCombat(-9);
                    logger.info("CODA draw/discard patch modified cost.");
                }
            }
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasPower");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

        }
    }
}
