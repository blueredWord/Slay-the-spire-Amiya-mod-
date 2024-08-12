package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import Amiyamod.patches.YCardTagClassEnum;
//源石遗物的抽象类
public abstract class CYrelic extends AbstractBlight implements CustomSavable<Integer> {
    //公用感染爆发最大值
    public int value = 0;
    public static int level = 1;//初始感染水平
    public static final int MAXY = 10;
    public CYrelic(String name,String des,String imgName,boolean unique) {
        super(Amiyamod.makeID(name),name, des, imgName, unique);
        this.blightID = Amiyamod.makeID(name);
    }

    //提供外部调动源石数值的接口
    public void addC(int n) {
        if (n != 0){
            this.counter += n;
            this.flash();
            if (this.counter >= MAXY){
                this.OnBreak();
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
        return value;
    }

    // 读取
    @Override
    public void onLoad(Integer save) {
        this.value = save;
    }

    public void OnBreak() {
        this.flash();
        level++;
        this.name = CardCrawlGame.languagePack.getRelicStrings(this.blightID).NAME+level;
        this.updateDescription();
    }
}