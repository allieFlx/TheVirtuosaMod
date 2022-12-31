package theVirtuosa.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TollTheBellEffect extends AbstractGameEffect {

    public TollTheBellEffect() {

    }

    public void update() {
        CardCrawlGame.sound.playA("BELL", -0.5F);
        CardCrawlGame.sound.playA("BELL", -0.4F);

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {

    }

    public void dispose() {

    }
}
