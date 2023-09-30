package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "applyStartOfTurnRelics"
)
public class ResetMomentumThisTurnPatch
{
    @SpirePrefixPatch
    public static void Prefix(AbstractPlayer __instance) {
        IncrementMomentumThisTurnPatch.CARDS_REVEALED = 0;
        IncrementMomentumThisTurnPatch.CARDS_DRAWN = 0;
    }
}
