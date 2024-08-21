package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.UUID;

public class CardBackPower extends AbstractPower{
    public static final String NAME = "CardBackPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static ArrayList<AbstractCard> CardList = new ArrayList<AbstractCard>();
    public CardBackPower(AbstractCard card) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        CardList.add(card);
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount =-1;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void atStartOfTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> var = new ArrayList<AbstractCard>();
        for(AbstractCard card : CardList){
            //遍历抽牌堆
            for(AbstractCard c : p.drawPile.group){
                if (c.uuid == card.uuid){
                    var.add(c);
                }
            }
            for (AbstractCard c : var){
                p.drawPile.addToHand(c);
            }
            var.clear();
            //遍历弃牌堆
            for (AbstractCard c : p.discardPile.group){
                if (c.uuid == card.uuid){
                    var.add(c);
                }
            }
            for (AbstractCard c : var){
                p.discardPile.addToHand(c);
            }
        }
        var.clear();
        //退出此能力
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void onRemove() {
        CardList.clear();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // 效果 :每次失去所有 丝线 时获得荆棘。

}
