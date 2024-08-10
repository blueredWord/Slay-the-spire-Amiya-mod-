package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import Amiyamod.patches.YCardTagClassEnum;
//源石遗物的抽象类
public abstract class CYrelic extends CustomRelic implements CustomSavable<Integer> {
    //公用感染爆发最大值
    public static final int MAXY = 10;
    public CYrelic(String id, Texture texture, RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
    }

    //提供外部调动源石数值的接口
    public void addC(int n) {
        this.counter += n;
        if (this.counter >= this.MAXY){
            this.breakC();
        }
    }

    //打出特定Tag卡牌增加计数
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(YCardTagClassEnum.YCard)) {
            this.counter++;
            flash();
        }
    }
    public void breakC() {}
}