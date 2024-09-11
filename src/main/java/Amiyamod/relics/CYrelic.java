package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.power.YSayPower;
import basemod.abstracts.CustomRelic;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

//源石遗物的抽象类
public abstract class CYrelic extends CustomRelic {
    public int cost = 1;//初始感染水平
    public String name;
    public static final int MAXY = 10;//公用感染爆发最大值
    public CYrelic(String id, Texture url, Texture out) {
        super(  id,
                url,
                out,
                RelicTier.SPECIAL,
                LandingSound.SOLID);
        this.name = CardCrawlGame.languagePack.getRelicStrings(this.relicId).NAME;
        this.counter = 0;
    }
    public void atTurnStart() {
        if (this.counter >= MAXY){
            this.counter = 0;
            this.updateDescription(null);
            this.flash();
        }
    }

    //在爆发状态下结束战斗则清零
    public void onVictory() {
        if (this.counter >= MAXY){
            this.counter = 0;
        }
        this.updateDescription(null);
    }
    //提供外部调动源石数值的接口
    public void addC(int n) {
        if (AbstractDungeon.player.hasPower(YSayPower.POWER_ID) && n > 0){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "源石病：感染爆发中，拒绝增加感染进度"
            );
        }else {
            if (n != 0){
                if (n>0){
                    this.counter = Math.min(MAXY,this.counter+n);
                }else {
                    this.counter = Math.max(0,this.counter+n);
                }
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "源石病：感染进度增加"+n+",目前为"+this.counter
                );
                this.flash();
                if (this.counter >= MAXY){
                    this.OnBreak();
                }
            }
        }
        this.updateDescription(null);
    }

    public void OnBreak() {
        this.flash();
        this.cost++;
        //this.name = CardCrawlGame.languagePack.getRelicStrings(this.relicId).NAME;
        this.updateDescription(null);
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "触发感染阶段加深，目前感染等级："+this.cost
        );
    }
}