package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import Amiyamod.patches.YCardTagClassEnum;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

//源石遗物的抽象类
public abstract class CYrelic extends CustomRelic  implements CustomSavable<Integer> {
    public static int level = 1;//初始感染水平
    public String name;
    public static Boolean isBraeking = false;
    public static final int MAXY = 10;//公用感染爆发最大值
    public CYrelic(String id, Texture url, Texture out) {
        super(  id,
                url,
                out,
                RelicTier.SPECIAL,
                LandingSound.SOLID);
        this.name = CardCrawlGame.languagePack.getRelicStrings(this.relicId).NAME+level;

    }

    public void atTurnStart() {
        if (isBraeking){
            this.counter = 0;
            isBraeking = false;
        }
    }

    //提供外部调动源石数值的接口
    public void addC(int n) {
        if (isBraeking){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "源石病：感染爆发中，拒绝增加感染进度"
            );
        }else {
            if (n != 0){
                this.counter += n;
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "源石病：感染进度增加"+n+",目前为"+this.counter
                );
                this.flash();
                if (this.counter >= MAXY){
                    this.OnBreak();
                }
            }
        }

    }

    /*打出特定Tag卡牌增加计数 修改实现方法 封存
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(YCardTagClassEnum.YCard)) {
            addC(1);
            flash();
        }
    }
    */

    // 保存
    @Override
    public Integer onSave() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "源石病：遗物保存"
        );
        return level;
    }

    // 读取
    @Override
    public void onLoad(Integer save) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "源石病：遗物读取"
        );
        level = (int)save;
    }



    public void OnBreak() {
        this.flash();
        isBraeking = true;
        level++;
        this.name = CardCrawlGame.languagePack.getRelicStrings(this.relicId).NAME + level;
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "触发感染阶段加深，目前感染等级："+level
        );
    }
}