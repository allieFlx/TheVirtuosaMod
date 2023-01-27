package theVirtuosa.patches;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CtBehavior;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.powers.VirtuosaHelplessPower;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderImage",
        paramtypez = {
                SpriteBatch.class,
                boolean.class,
                boolean.class
        }
)
public class RenderSpectralPatch
{
    @SpirePrefixPatch
    public static void Prefix(AbstractCard __instance, SpriteBatch sb, boolean hovered, boolean selected)
    {
        // TODO: shader needs to more specifically target render elements
        //  i.e, not shadow or highlight

    }

    @SpireInsertPatch(
            locator = RenderSpectralPatch.Locator.class,
            localvars = {"sb"} // "var1"
    )
    public static void Insert(AbstractCard __instance, SpriteBatch sb) {
        if (CardModifierManager.hasModifier(__instance, VirtuosaSpectralMod.ID))
        {
            ShaderProgram shader = null;
            try {
                shader = new ShaderProgram(
                        Gdx.files.internal(TheVirtuosa.makeShaderPath("spectral/vertex.vs")),
                        Gdx.files.internal(TheVirtuosa.makeShaderPath("spectral/fragment.fs"))
                );
                if (!shader.isCompiled()) {
                    System.err.println(shader.getLog());
                }
                if (shader.getLog().length() > 0) {
                    System.out.println(shader.getLog());
                }
            } catch (GdxRuntimeException e) {
                System.out.println("Failed to load spectral shader:");
                e.printStackTrace();
            }
            if (shader != null){
                sb.setShader(shader);
            }
        }
    }

    @SpirePostfixPatch
    public static void Postfix(AbstractCard __instance, SpriteBatch sb, boolean hovered, boolean selected)
    {
        sb.setShader(null);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderCardBg");

            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

        }
    }
}
