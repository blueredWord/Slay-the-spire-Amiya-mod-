package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Yzuzhou.ASay;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FirstSayPower extends AbstractPower {
    public static final String NAME = "FirstSayPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public FirstSayPower(int am) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = am;
        this.type = PowerType.BUFF;
        // 添加图标

        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.flash();
        for (int i = 0;i<this.amount;i++){
            this.addToBot(new MakeTempCardInHandAction(new ASay().makeCopy()));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }
}
