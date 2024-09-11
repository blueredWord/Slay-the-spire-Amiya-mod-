package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import org.apache.logging.log4j.LogManager;

public class YRing extends CustomRelic {
    public static final String NAME = "YRing";
    public static final String ID = Amiyamod.makeID(NAME);
    public static final int time = 1;
    public YRing() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.STARTER,
                LandingSound.SOLID
        );
    }

    public YRing(String id, Texture texture, Texture texture1, RelicTier relicTier, LandingSound landingSound) {
            super(id,texture,texture1,relicTier,landingSound);
    }

    //在爆发状态下结束战斗则清零
    public void onVictory() {
        this.grayscale = false;
        this.isDone = false;
        this.pulse = false;
        this.counter = -1;
        this.stopPulse();
    }
    public void atPreBattle() {
        work(true);
    }
    public void onTrigger() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "特殊抑制器触发"
        );
        if (this.counter <= 1){
            this.counter = -1;
            this.work(false);
        } else {
            this.counter--;
        }
        this.flash();
        this.addToBot(new VFXAction(new RelicAboveCreatureEffect(AbstractDungeon.player.hb_x,AbstractDungeon.player.hb_y,this)));
    }
    // 返回遗物的描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void work(boolean f) {
        this.pulse = f;
        this.isDone = !f;
        this.grayscale = !f;
        if (f) {
            this.counter = time;
            this.beginPulse();
        } else {
            this.stopPulse();
        }
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new YRing();
    }
}
