package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.ShadowCry;
import Amiyamod.cards.Yzuzhou.BallCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BallAPower extends AbstractPower {
    public static final String NAME = "BallAPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public BallAPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = amount;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/1_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/1_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        //当打出非0费牌
        if ((card.costForTurn != 0) || (card.cost == -1 && card.energyOnUse != 0)){
            this.flash();
            //赛一张仅剩的创意
            this.addToBot(new MakeTempCardInHandAction(new BallCard()));

            //如果没有 封印 状态 给一层封印
            if(!AbstractDungeon.player.hasPower(BallAPower.POWER_ID)){
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,this.owner,new BallBPower(AbstractDungeon.player)));
            }

            //降低一层
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

}
