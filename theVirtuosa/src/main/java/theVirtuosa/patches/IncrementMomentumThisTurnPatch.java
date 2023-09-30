package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;


@SpirePatch(
        clz = AbstractPlayer.class,
        method = "draw",
        paramtypez = {int.class}
)
public class IncrementMomentumThisTurnPatch
{
    public static int CARDS_REVEALED = 0;
    public static int CARDS_DRAWN = 0;
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {}
    )
    public static void onDraw(AbstractPlayer __instance) {
        ++CARDS_DRAWN;
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
