package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Yjianceyi extends CYrelic implements CustomSavable<Integer> {
    public static final String NAME = "Yjianceyi";
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = Amiyamod.makeID(NAME);
    // 图片路径
    private static final String IMG_PATH = "img/relics/"+NAME+".png";
    // 遗物类型 初始遗物STARTER
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;
    public int value = 0;

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

    public Yjianceyi() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    //复写获得时效果
    @Override
    public void onEquip() {
        this.counter = 1; //计数为1
        this.addC(1);
        super.onEquip();
    }

    //感染计数到达上限的处理
    public void breakC() {

    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new Yjianceyi();
    }
}