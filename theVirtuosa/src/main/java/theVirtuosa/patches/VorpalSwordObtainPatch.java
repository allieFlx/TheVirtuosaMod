package theVirtuosa.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.cards.VirtuosaCounterfeitSword;
import theVirtuosa.cards.VirtuosaVorpalSword;

@SpirePatch(
        clz = ShowCardAndObtainEffect.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractCard.class,
                float.class,
                float.class,
                boolean.class
        }
)
public class VorpalSwordObtainPatch
{
    private static final Logger logger = LogManager.getLogger(VorpalSwordObtainPatch.class.getName());
    @SpirePrefixPatch
    public static void obtainVorpalSword(ShowCardAndObtainEffect __instance, @ByRef AbstractCard[] card) {
        AbstractCard tmp = card[0];
        if (tmp.cardID.equals(VirtuosaVorpalSword.ID)) {
            boolean exists = false;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.cardID.equals(tmp.cardID) && c != tmp) {
                    exists = true;
                }
            }
            if (exists) {
                logger.info("VORPAL OBTAIN PATCH TRIGGERED - Replace with Counterfeit Sword");
                AbstractCard counterfeit = new VirtuosaCounterfeitSword();

                for(int i = 0; i < tmp.timesUpgraded; ++i) {
                    counterfeit.upgrade();
                }

                counterfeit.upgraded = tmp.upgraded;
                counterfeit.timesUpgraded = tmp.timesUpgraded;
                counterfeit.baseDamage = tmp.baseDamage;
                counterfeit.cost = tmp.cost;
                counterfeit.costForTurn = tmp.costForTurn;
                counterfeit.isCostModified = tmp.isCostModified;
                counterfeit.isCostModifiedForTurn = tmp.isCostModifiedForTurn;
                counterfeit.inBottleLightning = tmp.inBottleLightning;
                counterfeit.inBottleFlame = tmp.inBottleFlame;
                counterfeit.inBottleTornado = tmp.inBottleTornado;
                counterfeit.isSeen = tmp.isSeen;
                counterfeit.isLocked = tmp.isLocked;
                counterfeit.misc = tmp.misc;
                counterfeit.freeToPlayOnce = tmp.freeToPlayOnce;

                card[0] = counterfeit;
            }
        }
    }
}
