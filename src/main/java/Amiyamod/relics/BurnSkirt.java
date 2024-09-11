package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import org.apache.logging.log4j.LogManager;

public class BurnSkirt extends CustomRelic {
    public static final String NAME = "BurnSkirt";
    public static final String ID = Amiyamod.makeID(NAME);
    public BurnSkirt() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.BOSS,
                LandingSound.SOLID
        );
    }

    //在爆发状态下结束战斗则清零
    public void onVictory() {
        this.grayscale = false;
        this.stopPulse();
    }
    public void atPreBattle() {
        work(true);
    }
    public void atTurnStart() {
        if (!this.grayscale){
            this.flash();
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "BurnSkirt触发获得能量"
            );
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            this.addToBot(new GainEnergyAction(1));
        }
        this.work(true);
    }
    public void onTrigger() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "BurnSkirt触发下回合不获得能量"
        );
        work(false);
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
            this.beginPulse();
        } else {
            this.stopPulse();
        }
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new BurnSkirt();
    }

    public boolean canSpawn() {
        boolean n= true;
        AbstractPlayer p = AbstractDungeon.player;
        if (
                (p.hasRelic(TheTen.ID))
                        ||
                        ( p.hasRelic(TenRelic.ID) && p.getRelic(TenRelic.ID).counter < 10 )
        ) {
            n = false;
        }
        return n;
    }
}
