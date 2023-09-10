package theVirtuosa.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class AnimatedSlasherEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float scaleX;
    private float scaleY;
    private float targetScale;
    private Color color2;
    private TextureAtlas.AtlasRegion img;
    public AnimatedSlasherEffect(float x, float y, float dX, float dY, float angle, float targetScale, Color color1, Color color2) {
        this.x = x - 362.0F - dX / 2.0F * Settings.scale;
        this.y = y - 16.5F - dY / 2.0F * Settings.scale;
        this.sX = this.x;
        this.sY = this.y;
        this.tX = this.x + dX / 2.0F * Settings.scale;
        this.tY = this.y + dY / 2.0F * Settings.scale;
        this.color = color1.cpy();
        this.color2 = color2.cpy();
        this.color.a = 0.2F;
        this.startingDuration = 0.4F;
        this.duration = this.startingDuration;
        this.targetScale = targetScale;
        this.scaleX = 0.01F;
        this.scaleY = 0.01F;
        this.rotation = -135.0F;
        this.rotation = angle;
    }

    public void update() {
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.exp10In.apply(1.0F, 0.2F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.scaleX = Interpolation.exp10In.apply(this.targetScale, 0.1F, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.scaleY = this.scaleX;
            this.x = Interpolation.fade.apply(this.tX, this.sX, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
            this.y = Interpolation.fade.apply(this.tY, this.sY, (this.duration - this.startingDuration / 2.0F) / (this.startingDuration / 2.0F));
        } else {
            this.scaleX = Interpolation.pow2In.apply(0.5F, this.targetScale, this.duration / (this.startingDuration / 2.0F));
            this.color.a = Interpolation.pow5In.apply(0.2F, 1.0F, this.duration / (this.startingDuration / 2.0F));
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        //  size: 724, 33, combat/streak
        this.img = ImageMaster.vfxAtlas.findRegion("combat/streak");
        sb.setColor(this.color2);
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.x, this.y, 362.0F, 16.5F, 724.0F, 33.0F, this.scaleX * 0.4F * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.scaleY * 0.7F * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, 362.0F, 16.5F, 724.0F, 33.0F, this.scaleX * 0.7F * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.scaleY * MathUtils.random(0.95F, 1.05F) * Settings.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {

    }
}
