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
import theVirtuosa.TheVirtuosa;
import theVirtuosa.powers.VirtuosaCodaPower;
import theVirtuosa.powers.VirtuosaResonancePower;
import theVirtuosa.relics.VirtuosaMagicFluteRelic;

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
public class VirtuosaMagicFluteApplyPowerPatch {
    private static final Logger logger = LogManager.getLogger(VirtuosaMagicFluteApplyPowerPatch.class.getName());

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"powerToApply"}
    )
    public static void Insert(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply) {
        if (AbstractDungeon.player.hasRelic(VirtuosaMagicFluteRelic.ID) && powerToApply.ID.equals(VirtuosaResonancePower.POWER_ID)) {
            AbstractDungeon.player.getRelic(VirtuosaMagicFluteRelic.ID).flash();
            ++powerToApply.amount;
            ++__instance.amount;
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
