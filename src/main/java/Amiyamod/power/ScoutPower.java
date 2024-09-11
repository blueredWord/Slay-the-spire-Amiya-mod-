package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Memory.Outcast2;
import Amiyamod.patches.OutcastDamage;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoutPower extends TwoAmountPower{
    public static final String NAME = "ScoutPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ScoutPower(int am,AbstractCreature mo) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner =  mo;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = am;
        this.amount2 = am;
        this.type = PowerType.DEBUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void atEndOfRound() {
        this.flash();
        this.amount2 += this.amount;
        /*
        this.addToBot(
                new DamageAction(this.owner,new DamageInfo(null,this.amount, DamageInfo.DamageType.NORMAL))
        );
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        */
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] +  this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }
}
