package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.cards.badY;
import Amiyamod.patches.YZCardInterface;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Objects;

public class GoldenStone extends CustomRelic {
    public static final String NAME = "GoldenStone";
    public static final String ID = Amiyamod.makeID(NAME);
    //private static final int ti = 2;
    public  GoldenStone() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.SHOP,
                LandingSound.SOLID
        );
    }
    public void onEquip() {
        /*
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(648);

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

         */
    }
    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    public void atPreBattle() {
        this.addToBot(new MakeTempCardInHandAction(new badY().makeCopy(),2));
    }
    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new GoldenStone();
    }
}
