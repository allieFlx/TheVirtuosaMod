package theVirtuosa.cardmods;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.actions.TransformSpecificCardAction;

public class VirtuosaMirrorSpiritMod extends AbstractCardModifier {
    public static String ID = TheVirtuosa.makeID(VirtuosaMirrorSpiritMod.class.getSimpleName());

    public VirtuosaMirrorSpiritMod() {
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, VirtuosaMirrorSpiritMod.ID);
    }

    public void onInitialApplication(AbstractCard card) {
        if (!CardModifierManager.hasModifier(card, VirtuosaSpectralMod.ID)) {
            CardModifierManager.addModifier(card, new VirtuosaSpectralMod());
        }
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
