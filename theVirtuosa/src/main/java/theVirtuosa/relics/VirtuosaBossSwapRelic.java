package theVirtuosa.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theVirtuosa.TheVirtuosa;
import theVirtuosa.powers.VirtuosaRotPower;
import theVirtuosa.util.TextureLoader;

import static theVirtuosa.TheVirtuosa.makeRelicOutlinePath;
import static theVirtuosa.TheVirtuosa.makeRelicPath;

public class VirtuosaBossSwapRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Marble Gall
     * Regen alt: Replaces Forest Charm. Gain [E] each turn. At the start of each combat, gain 3 Rot.
     */

    // ID, images, text.
    public static final String ID = TheVirtuosa.makeID("VirtuosaBossSwapRelic");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public VirtuosaBossSwapRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
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

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VirtuosaRotPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(VirtuosaStartingRelic.ID);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

}
