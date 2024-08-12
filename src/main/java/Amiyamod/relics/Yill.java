package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import javax.naming.Name;
import java.util.Iterator;

public class Yill extends CYrelic implements CustomSavable<Integer> {
    public static final String NAME = "Yill";
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = Amiyamod.makeID(NAME);
    public static boolean calledTransform = false;
    public Yill() {
        super(NAME, CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS[0], NAME,false);
        // 图片路径
        this.img = ImageMaster.loadImage("img/relics/"+NAME+".png");
        this.outlineImg = ImageMaster.loadImage("img/relics/"+NAME+"outline.png");

    }

    //感染计数到达上限的处理
    public void OnBreak() {
        super.OnBreak(); //先调用超类

        AbstractCard card = Amiyamod.GetNextYcard(false); //随机获取下一张诅咒

        calledTransform = true;//呼叫UI?和下面的update一起需要测试

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onPreviewObtainCard(card);
        }//触发所有遗物的获取卡片效果 真的有获取诅咒时触发的吗？万一呢

        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToBottom(card);//建立一个列表用于传递

        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group,"你的感染加深了");//给卡的action?不确定 需要测试
    }
    public void update() {
        //应该是展示一下诅咒卡的代码？可能有问题吧 到时候试试先
        super.update();
        if (calledTransform && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID) {
            calledTransform = false;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }
    }
    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.description;
    }

    public AbstractBlight makeCopy() {
        return new Yill();
    }
}
