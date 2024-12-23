package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

//特蕾西亚的庇护
//获得格挡时改为获得等量的 丝线。
public class LittleTePower2 extends AbstractPower {
    public static final String NAME = "LittleTePower";
    public static final String ID1 = "AmiyaMod:"+NAME+"2";
    public static final String ID2 = "AmiyaMod:"+NAME;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID2);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public LittleTePower2(int i) {
        this.name = powerStrings.NAME;
        this.ID = ID1;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = i;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            Iterator var2 = AbstractDungeon.player.hand.group.iterator();

            while(var2.hasNext()) {
                AbstractCard c = (AbstractCard)var2.next();
                if (!c.isEthereal) {
                    c.retain = true;
                }
            }

            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner,  this.ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }
        }

    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
    }
}
