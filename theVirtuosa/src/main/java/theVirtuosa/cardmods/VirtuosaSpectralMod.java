package theVirtuosa.cardmods;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches.CardModifierRender;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.SpectralExhaustAction;
import theVirtuosa.patches.CustomTags;

public class VirtuosaSpectralMod extends AbstractCardModifier {
    public static String ID = TheVirtuosa.makeID(VirtuosaSpectralMod.class.getSimpleName());
    private static String spectralString = BaseMod.getKeywordProper("thevirtuosa:Spectral");
    private static String prefixString = TheVirtuosa.getModID().toLowerCase() + ":" + StringUtils.capitalize(spectralString);
    private boolean replaceExhaust = false;

    public VirtuosaSpectralMod() {
        // possibly add locked countdown on sequestered cards
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.tags.contains(CustomTags.SPECTRAL);
    }

    public void onInitialApplication(AbstractCard card) {
        if (card.exhaust) {
            this.replaceExhaust = true;
        } else {
            card.exhaust = true;
        }

        card.tags.add(CustomTags.SPECTRAL);

        // TODO VFX for spectral cards, glow pink / blue (pink for sequestered, blue for spectral)


        // >> doesnt work for non-custom cards >>
        // TODO shader vfx that works for all cards
        /*
        String img_s;
        String img_l;
        switch (card.type)
        {
            case ATTACK:
                img_s = TheVirtuosa.ATTACK_SPECTRAL;
                img_l = TheVirtuosa.ATTACK_SPECTRAL_PORTRAIT;
                break;
            case POWER:
                img_s = TheVirtuosa.POWER_SPECTRAL;
                img_l = TheVirtuosa.POWER_SPECTRAL_PORTRAIT;
                break;
            default:
                img_s = TheVirtuosa.SKILL_SPECTRAL;
                img_l = TheVirtuosa.SKILL_SPECTRAL_PORTRAIT;
                break;
        }
        ((CustomCard)card).setBackgroundTexture(img_s, img_l);

         */
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        // add Spectral to last line, replacing exhaust, purge, or fleeting if it exists

        String retVal = rawDescription;

        // TODO remove exhaust, purge, fleeting from raw. add spectral. return

        return prefixString + (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD + " NL " + retVal;
    }

    @Override
    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        super.atEndOfTurn(card, group);
        AbstractDungeon.actionManager.addToTop(new SpectralExhaustAction(card, group));
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return false;
    }

    public void onRemove(AbstractCard card) {
        // remove vfx
        if (!this.replaceExhaust) { card.exhaust = false; }
        card.tags.remove(CustomTags.SPECTRAL);
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        /*
        if (shader == null) {
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
                System.out.println("ERROR: Failed to init spectral shader:");
                e.printStackTrace();
            }
        }

        sb.setShader(shader);
        // card.render(sb); // this caused an infinite loop...
        // super.onRender(card, sb);
        sb.setShader(null);

        // TODO: doesnt actually interact with main render method for card
        //  may have to patch render method

         */

        // should now be rendered in patch
    }

    public AbstractCardModifier makeCopy() {
        return new VirtuosaSpectralMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
