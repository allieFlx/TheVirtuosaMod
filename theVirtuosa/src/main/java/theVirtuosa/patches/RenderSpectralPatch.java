package theVirtuosa.patches;

import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import javassist.CtBehavior;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;

public class RenderSpectralPatch {

    private static final ShaderProgram bgShader = new ShaderProgram(
            Gdx.files.internal(TheVirtuosa.makeShaderPath("spectral/vertex.vs")),
            Gdx.files.internal(TheVirtuosa.makeShaderPath("spectral/fragment.fs"))
    );
    private static final ShaderProgram ptShader = new ShaderProgram(
            Gdx.files.internal(TheVirtuosa.makeShaderPath("spectralGradientMap/vertex.vs")),
            Gdx.files.internal(TheVirtuosa.makeShaderPath("spectralGradientMap/fragment.fs"))
    );
    private static final Texture TEXT_GLOW = ImageMaster.loadImage("theVirtuosaResources/images/512/spectral_card_glow.png");

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderImage",
            paramtypez = {
                    SpriteBatch.class,
                    boolean.class,
                    boolean.class
            }
    )
    public static class MainRenderSpectralPatch {
        // TODO:
        //  glow has a weird strobe like effect when it spawns in
        //  fixed performance issues, may need to add error handling to shader load

        @SpireInsertPatch(
                locator = PreBgLocator.class,
                localvars = {"sb"} // "var1"
        )
        public static void BgShader(AbstractCard __instance, SpriteBatch sb) {
            /*
            if (CardModifierManager.hasModifier(__instance, VirtuosaSpectralMod.ID)) {
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
                    System.out.println("Failed to load spectral background shader:");
                    e.printStackTrace();
                }
                if (shader != null) {
                    sb.setShader(shader);
                }
            }

             */
            if (CardModifierManager.hasModifier(__instance, VirtuosaSpectralMod.ID)) {
                sb.setShader(bgShader);
            }
        }

        @SpireInsertPatch(
                locator = PostBgLocator.class,
                localvars = {"sb"} // "var1"
        )
        public static void PortraitShader(AbstractCard __instance, SpriteBatch sb) {
            /*
            if (CardModifierManager.hasModifier(__instance, VirtuosaSpectralMod.ID)) {
                ShaderProgram shader = null;
                try {
                    shader = new ShaderProgram(
                            Gdx.files.internal(TheVirtuosa.makeShaderPath("spectralPortrait/vertex.vs")),
                            Gdx.files.internal(TheVirtuosa.makeShaderPath("spectralPortrait/fragment.fs"))
                    );
                    if (!shader.isCompiled()) {
                        System.err.println(shader.getLog());
                    }
                    if (shader.getLog().length() > 0) {
                        System.out.println(shader.getLog());
                    }
                } catch (GdxRuntimeException e) {
                    System.out.println("Failed to load spectral portrait shader:");
                    e.printStackTrace();
                }
                if (shader != null) {
                    sb.setShader(shader);
                }
            }

             */
            if (CardModifierManager.hasModifier(__instance, VirtuosaSpectralMod.ID)) {
                sb.setShader(ptShader);
            }
        }

        @SpireInsertPatch(
                locator = PostPortraitLocator.class,
                localvars = {"sb"} // "var1"
        )
        public static void RemoveShader(AbstractCard __instance, SpriteBatch sb) {
            if (CardModifierManager.hasModifier(__instance, VirtuosaSpectralMod.ID)) {
                sb.setShader(null);
            }
        }

        private static class PreBgLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderCardBg");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

            }
        }

        private static class PostBgLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderCardBg");

                int[] retVal = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
                retVal[0] += 1;
                return retVal;
            }
        }

        private static class PostPortraitLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {

                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderPortraitFrame");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);

            }
        }
    }

    /*
     *   Credit
     *   following code ruthlessly stolen from ThePackmaster: rippack
     */

    //Replace card glow
    @SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, Color.class})
    public static class CutOffGlow {

        @SpirePostfixPatch()
        public static void Postfix(CardGlowBorder __instance, AbstractCard ___card) {
            if (CardModifierManager.hasModifier(___card, VirtuosaSpectralMod.ID)) {
                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", new TextureAtlas.AtlasRegion(TEXT_GLOW, 0, 0, TEXT_GLOW.getWidth(), TEXT_GLOW.getHeight()));
            }
        }
    }

}
