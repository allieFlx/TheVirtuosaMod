package theVirtuosa.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class SlasherEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private Color color2;
    boolean reversed;

    public SlasherEffect(float x, float y, Color color1, Color color2, boolean reversed) {
        this.x = x;
        this.y = y;
        this.color = color1;
        this.color2 = color2;
        this.reversed = reversed;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(-0.1F, -0.4F));
        } else {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(-0.1F, -0.4F));
        }
        float tmp = this.reversed ? -185.0F : 185.0F;
        float sign = this.reversed ? -1.0F : 1.0F;
        float tmpDX = this.reversed ? -900.0F : 900.0F;

        AbstractDungeon.effectsQueue.add(new AnimatedSlasherEffect(this.x - sign * 10.0F, this.y + 15.0F, tmpDX, 100.0F, tmp, 1.0F, this.color, this.color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlasherEffect(this.x, this.y, tmpDX, 100.0F, tmp, 1.0F, this.color, this.color2));
        AbstractDungeon.effectsQueue.add(new AnimatedSlasherEffect(this.x + sign* 10.0F, this.y - 15.0F, tmpDX, 100.0F, tmp, 1.0F, this.color, this.color2));
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {

    }

    public void dispose() {

    }
}
