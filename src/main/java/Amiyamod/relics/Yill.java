package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.cards.AmiyaPower;
import Amiyamod.patches.YZCardInterface;
import Amiyamod.power.YSayPower;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import javax.naming.Name;
import java.util.Iterator;
import java.util.Objects;

public class Yill extends CYrelic{
    public static final String NAME = "Yill";
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = Amiyamod.makeID(NAME);
    // 图片路径
    private static final String IMG_PATH = "img/relics/"+NAME+".png";
    // 构造函数，初始化遗物
    public Yill() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png")
        );
        this.counter = Math.max(0,this.counter);
    }
    public Yill(int number) {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png")
        );
        this.addC(number);
        this.counter = Math.max(0,this.counter);
    }

    //感染计数到达上限的处理
    public void OnBreak() {
        super.OnBreak(); //先调用超类
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        YZCardInterface card = (YZCardInterface)Amiyamod.GetNextYcard(false); //随机获取下一张诅咒
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractRelic r : p.relics) {
            r.onPreviewObtainCard( (AbstractCard)card);
        }//触发所有遗物的获取卡片效果 真的有获取诅咒时触发的吗？万一呢
        for (AbstractCard c : p.masterDeck.group){
            if (Objects.equals(c.cardID,((AbstractCard)card).cardID)){
                card.YZupgrade();
                break;
            }
        }

        this.addToBot(new ApplyPowerAction(p,p,new YSayPower()));

        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToBottom((AbstractCard)card);//建立一个列表用于传递
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group,"你的感染加深了");//给卡的action?不确定 需要测试
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    public String getUpdatedDescription() {
        if (this.counter < MAXY){
            int n = Math.min (MAXY - this.counter,MAXY);
            return (DESCRIPTIONS[0]+n+DESCRIPTIONS[1]);
        } else {
            return (DESCRIPTIONS[2]);
        }
    }
    public AbstractRelic makeCopy() {
        return new Yill();
    }
}
