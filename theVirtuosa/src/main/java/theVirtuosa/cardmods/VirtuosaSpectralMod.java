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
        // vfx
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
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, group, true));
        //group.moveToExhaustPile(card);
        // spectral exhaust effects at pile
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

    public AbstractCardModifier makeCopy() {
        return new VirtuosaSpectralMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
