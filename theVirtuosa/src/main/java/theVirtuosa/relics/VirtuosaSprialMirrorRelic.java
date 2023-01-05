package theVirtuosa.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.NinjaScroll;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.cards.SpectralMirrorSpirit;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeRelicOutlinePath;
import static theVirtuosa.TheVirtuosa.makeRelicPath;

public class VirtuosaSprialMirrorRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each turn, add a Spectral card to your hand that copies the last card played each turn.
     */

    // ID, images, text.
    public static final String ID = TheVirtuosa.makeID("VirtuosaSpiralMirrorRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png"));

    public VirtuosaSprialMirrorRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        this.flash();
        this.addToTop(new MakeTempCardInHandAction(new SpectralMirrorSpirit()));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
