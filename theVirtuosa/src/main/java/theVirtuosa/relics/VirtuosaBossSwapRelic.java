package theVirtuosa.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.FrozenCore;
import com.megacrit.cardcrawl.rewards.chests.BossChest;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.screens.stats.BossRelicChoiceStats;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.powers.VirtuosaResonancePower;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeRelicOutlinePath;
import static theVirtuosa.TheVirtuosa.makeRelicPath;

public class VirtuosaBossSwapRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each turn, gain 1 Resonance
     */

    // ID, images, text.
    public static final String ID = TheVirtuosa.makeID("VirtuosaBossSwapRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public VirtuosaBossSwapRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(VirtuosaStartingRelic.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(VirtuosaStartingRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
    @Override
    public void atTurnStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VirtuosaResonancePower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(VirtuosaStartingRelic.ID);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
