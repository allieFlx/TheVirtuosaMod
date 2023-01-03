package theVirtuosa.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.characters.TheVirtuosaCharacter;
import theVirtuosa.interfaces.OnAddToDeckCard;

import static theVirtuosa.TheVirtuosa.makeCardPath;

public class VirtuosaBraceUp extends VirtuosaBraceUpNoCopy implements OnAddToDeckCard {

    /*
     Gain x block. When added, add an additional copy.
     */

    // TEXT DECLARATION

    public static final String ID = TheVirtuosa.makeID(VirtuosaBraceUp.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION
    public static final CardColor COLOR = TheVirtuosaCharacter.Enums.COLOR_BROWN;

    // /STAT DECLARATION/

    public VirtuosaBraceUp() {
        super();
    }

    @Override
    public void onAddToMasterDeck() {
        // only obtain a second copy, not recursive
        if (this.upgraded)
        {
            AbstractCard c = new VirtuosaBraceUpNoCopy();
            c.upgrade();
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(c,(float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
        else
        {
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new VirtuosaBraceUpNoCopy(),(float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }
}
