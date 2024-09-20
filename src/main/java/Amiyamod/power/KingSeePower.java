package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.action.KingSeeAction;
import Amiyamod.cards.YCard.KingSee;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class KingSeePower extends AbstractPower {
    public static final String NAME = "KingSeePower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final boolean up ;
    public KingSeePower(int n, AbstractCreature mo,boolean up) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = mo;
        this.up = up;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = n;
        this.type = PowerType.DEBUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.up){
            this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[3];
        }else {
            this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
        }
    }

    public void atStartOfTurn() {
        int var = TempHPField.tempHp.get(this.owner);
        this.flash();
        if (var>0){
            this.addToBot(new ApplyPowerAction(this.owner,null,new WeakPower(this.owner,this.amount,false)));
            for (int i = 0;i<this.amount;i++){
                this.addToBot(new KingSeeAction("",1,this.up));
            }
        } else {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
}
