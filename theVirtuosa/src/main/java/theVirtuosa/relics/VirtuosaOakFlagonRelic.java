package theVirtuosa.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MagicFlower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cards.SpectralMirrorSpirit;
import theVirtuosa.powers.VirtuosaResonancePower;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeRelicOutlinePath;
import static theVirtuosa.TheVirtuosa.makeRelicPath;

public class VirtuosaOakFlagonRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Whenever you heal HP during combat, gain an equal amount of Resonance.
     */

    // ID, images, text.
    public static final String ID = TheVirtuosa.makeID("VirtuosaOakFlagonRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png"));

    public VirtuosaOakFlagonRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    public int onPlayerHeal(int healAmount) {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            this.addToTop(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new VirtuosaResonancePower(AbstractDungeon.player, healAmount),
                    healAmount));
        }
        return healAmount;
    }

    // TODO: flavor text

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
