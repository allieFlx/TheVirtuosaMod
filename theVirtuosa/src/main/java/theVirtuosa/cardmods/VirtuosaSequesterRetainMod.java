package theVirtuosa.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theVirtuosa.TheVirtuosa;

public class VirtuosaSequesterRetainMod extends AbstractCardModifier {
    public static String ID = TheVirtuosa.makeID(VirtuosaSequesterRetainMod.class.getSimpleName());
    private Color storeColor;

    public VirtuosaSequesterRetainMod() {
        // possibly add locked countdown on sequestered cards
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.selfRetain;
    }

    public void onInitialApplication(AbstractCard card) {

        card.selfRetain = true;
        this.storeColor = card.glowColor;
        card.glowColor = Color.valueOf("ff00bf40").cpy(); // roughly pink colour
        // vfx
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    public void onRemove(AbstractCard card) {
        card.selfRetain = false;
        card.glowColor = this.storeColor;
        // remove vfx
    }

    public AbstractCardModifier makeCopy() {
        return new VirtuosaSequesterRetainMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
