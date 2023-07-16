package theVirtuosa.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;

import java.util.Iterator;

@SpirePatch(
        clz = CardGroup.class,
        method = "moveToExhaustPile"
)
public class SpectralExhaustPatch
{
    // private static final Logger logger = LogManager.getLogger(SpectralExhaustPatch.class.getName());
    @SpirePrefixPatch
    public static SpireReturn<?> spectralCheck(CardGroup __instance, AbstractCard c) {
        if (CardModifierManager.hasModifier(c, VirtuosaSpectralMod.ID)) {
            // ---
            Iterator var2 = AbstractDungeon.player.relics.iterator();

            while(var2.hasNext()) {
                AbstractRelic r = (AbstractRelic)var2.next();
                r.onExhaust(c);
            }

            var2 = AbstractDungeon.player.powers.iterator();

            while(var2.hasNext()) {
                AbstractPower p = (AbstractPower)var2.next();
                p.onExhaust(c);
            }

            c.triggerOnExhaust();
            if (AbstractDungeon.player.hoveredCard == c) {
                AbstractDungeon.player.releaseCard();
            }

            AbstractDungeon.actionManager.removeFromQueue(c);
            c.unhover();
            c.untip();
            c.stopGlowing();
            __instance.group.remove(c);

            // ---
            // TODO: Spectral exhaust from hand effect
            if (__instance == AbstractDungeon.player.hand) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
            }

            // ---

            TheVirtuosa.spectralPile.addToTop(c);
            AbstractDungeon.player.onCardDrawOrDiscard();
            // ---
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
