package Amiyamod.Effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.MapDot;

import java.util.ArrayList;
import java.util.Iterator;

public class LineEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float x2;
    private float y2;
    private static final float SPACING;
    private ArrayList<MapDot> dots = new ArrayList();

    public LineEffect(AbstractCreature mo) {
        AbstractPlayer p = AbstractDungeon.player;
        this.x = p.hb_x;
        this.y = p.hb_y;
        this.x2 = mo.hb_x;
        this.y2 = mo.hb_y;
        Vector2 vec2 = (new Vector2(this.x2, this.y2)).sub(new Vector2(this.x, this.y));
        float length = vec2.len();
        float START = SPACING * MathUtils.random();

        //for(float i = START; i < length; i += SPACING) {
            //vec2.clamp(length - i, length - i);
            this.dots.add(new MapDot(this.x + vec2.x, this.y + vec2.y, (new Vector2(this.x - this.x2, this.y - this.y2)).nor().angle() + 90.0F, true));
        //}

        this.duration = 3.0F;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        Iterator var2 = this.dots.iterator();

        while(var2.hasNext()) {
            MapDot d = (MapDot)var2.next();
            d.render(sb);
        }

    }

    public void dispose() {
    }

    static {
        SPACING = 30.0F * Settings.scale;
    }
}
