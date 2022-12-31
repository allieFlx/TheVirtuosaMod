package theVirtuosa.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theVirtuosa.TheVirtuosa;

public class ResonanceEffect extends AbstractGameEffect {

    public ResonanceEffect() {

    }

    public void update() {
        CardCrawlGame.sound.playA(TheVirtuosa.makeID("RESONANCE_1"), 0.0F);
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {

    }

    public void dispose() {

    }
}
