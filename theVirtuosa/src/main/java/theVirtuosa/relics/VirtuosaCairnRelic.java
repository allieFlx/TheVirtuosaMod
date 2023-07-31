package theVirtuosa.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cardmods.VirtuosaSpectralMod;
import theVirtuosa.powers.VirtuosaResonancePower;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeRelicOutlinePath;
import static theVirtuosa.TheVirtuosa.makeRelicPath;

public class VirtuosaCairnRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Whenever you play a Spectral card, gain 3 Block.
     */

    // ID, images, text.
    public static final String ID = TheVirtuosa.makeID("VirtuosaCairnRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png"));

    public VirtuosaCairnRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if(CardModifierManager.hasModifier(c, VirtuosaSpectralMod.ID)) {
            this.flash();
            this.addToTop(new GainBlockAction(AbstractDungeon.player, 3));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
