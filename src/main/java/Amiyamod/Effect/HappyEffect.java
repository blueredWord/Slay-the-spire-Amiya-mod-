package Amiyamod.Effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.Random;

import static com.megacrit.cardcrawl.helpers.ImageMaster.vfxAtlas;

public class HappyEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private float speed;
    private float speedStart;
    private float speedTarget;
    private Texture img;
    public static boolean switcher = true;
    private float stallTimer;
    private AbstractCreature p ;

    public HappyEffect(AbstractCreature P,boolean T) {
        this.img = ImageMaster.loadImage("img/powers/KingSeePower_128.png");
        this.duration = 4.0F;
        this.scale = MathUtils.random(0.7F, 0.5F) * Settings.scale;
        this.p = P;
        if(new Random().nextBoolean()){
            this.color = new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.4F, 0.5F), MathUtils.random(0.2F, 0.3F), 0.0F);
        }else {
            this.color = new Color(MathUtils.random(0.4F, 0.5F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.3F, 0.2F), 0.0F);
        }
        if (T){
            this.x = this.p.hb.cX + MathUtils.random(this.p.hb.width/6, this.p.hb.width/2);
        }else {
            this.x = this.p.hb.cX + MathUtils.random(-this.p.hb.width/6 , -this.p.hb.width/2);
        }
        this.x -= 50 * Settings.scale;
        this.y = this.p.hb.cY + MathUtils.random(-this.p.hb.height / 12.0F, this.p.hb.height / 6.0F);
        //this.x -= (float)64.0F;
        //this.y -= (float)64.0F;
        switcher = !switcher;
        this.renderBehind = true;
        this.rotation = MathUtils.random(360.0F);
        if (switcher) {
            this.renderBehind = true;
            this.vY = MathUtils.random(0.0F, 40.0F);
        } else {
            this.renderBehind = false;
            this.vY = MathUtils.random(0.0F, -40.0F);
        }
    }

    public void update() {
        if (this.duration > 3.0F) {
            this.color.a = Interpolation.fade.apply(0.6F, 0.0F, this.duration - 2.0F);
        } else {
            this.color.a = Interpolation.fade.apply(0.0F, 0.6F, this.duration);
        }

        this.rotation += Gdx.graphics.getDeltaTime()*10.0F;
        this.duration -= Gdx.graphics.getDeltaTime();
        this.y += Gdx.graphics.getDeltaTime()*10.0F;

        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);

        sb.draw(this.img, this.x, this.y, 64, 64, 128, 128, this.scale, this.scale, this.rotation,0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }

}
