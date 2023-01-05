package theVirtuosa.cardmods;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import org.apache.commons.lang3.StringUtils;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.SpectralExhaustAction;
import theVirtuosa.actions.TransformSpecificCardAction;
import theVirtuosa.patches.CustomTags;

public class VirtuosaMirrorSpiritMod extends AbstractCardModifier {
    public static String ID = TheVirtuosa.makeID(VirtuosaMirrorSpiritMod.class.getSimpleName());

    public VirtuosaMirrorSpiritMod() {
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, VirtuosaMirrorSpiritMod.ID);
    }

    //TODO if card moves to another pile, transform back into initial MirrorSpirit card

    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.addModifier(card, new VirtuosaSpectralMod());
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        super.onOtherCardPlayed(card, otherCard, group);
        AbstractCard mirrorCopy = otherCard.makeStatEquivalentCopy();
        CardModifierManager.addModifier(mirrorCopy, new VirtuosaMirrorSpiritMod());

        // bug fixed, changed from addToBot -> addToTop
        AbstractDungeon.actionManager.addToTop(new TransformSpecificCardAction(card, group, mirrorCopy));
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return false;
    }

    public void onRemove(AbstractCard card) {
    }

    public AbstractCardModifier makeCopy() {
        return new VirtuosaMirrorSpiritMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
