package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Yzuzhou.YCard;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.LoseTempHpPower;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BurnSkirt2 extends CustomRelic implements OnLoseTempHpRelic {
    public static final String NAME = "BurnSkirt2";
    public static final String ID = Amiyamod.makeID(NAME);
    public BurnSkirt2() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.BOSS,
                LandingSound.SOLID
        );
        this.cost = 1;
    }

/*
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

 */
//在爆发状态下结束战斗则清零
public void onVictory() {
    work(false);
}

    public void atPreBattle() {
        work(false);
    }

    public void atTurnStart() {
        if (this.pulse){
            this.flash();
            //this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            this.addToBot(new GainEnergyAction(this.cost));
        }
        this.work(false);
    }

    public void onLoseHp(int damageAmount) {
        this.work(true);
    }
public void work(boolean f) {
    this.pulse = f;
    this.isDone = !f;
    //this.grayscale = !f;
    if (f) {
        this.beginPulse();
    } else {
        this.stopPulse();
    }
}

// 返回遗物的描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new BurnSkirt2();
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        this.onLoseHp(i);
        return i;
    }
}
