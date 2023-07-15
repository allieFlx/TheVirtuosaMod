package theVirtuosa.cardmods;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;
import theVirtuosa.TheVirtuosa;
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
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, group));
        //AbstractDungeon.actionManager.addToTop(new SpectralExhaustAction(card, group));

        // send to spectral specific "spirit pile"
        // the spirit pile is non-specific with what cards are there, only the count matters
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
    public void onExhausted(AbstractCard card) {
        AbstractCard specCopy = card.makeStatEquivalentCopy();
        TheVirtuosa.spectralPile.addToTop(specCopy);

        // CardModifierManager.removeSpecificModifier(card, this, false);
    }

    public AbstractCardModifier makeCopy() {
        return new VirtuosaSpectralMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
