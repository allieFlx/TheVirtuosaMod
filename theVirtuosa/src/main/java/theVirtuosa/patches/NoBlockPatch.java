package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theVirtuosa.powers.VirtuosaHelplessPower;

/*
 * Credit discord user - Alchyr#3696
 */

@SpirePatch(
        clz = AbstractCreature.class, //This is the class you're patching.
        method = "addBlock" //This is the name of the method of that class that you're patching.
)
public class NoBlockPatch
{
    @SpirePrefixPatch //This means this is going to be added at the "start" of the thing you're patching.
    public static SpireReturn<?> No(AbstractCreature __instance, int blockAmount) //Patches receive both the instance (when the method is called, the AbstractCreature it's being called on) and any parameters of the method being patched (in this case, the block amount.)
    {
        if (__instance.hasPower(VirtuosaHelplessPower.POWER_ID))
        {
            AbstractPower power_instance = __instance.getPower(VirtuosaHelplessPower.POWER_ID);
            power_instance.flash();

            return SpireReturn.Return(null); //Patches can have a return type of void or SpireReturn<return type of the method>
        }
        return SpireReturn.Continue(); //This allows you to either return early, change the value returned, or just continue like this.
    }
}
