package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.patches.LeechDamage;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;
import java.util.List;

public class BloodNoPower extends AbstractPower implements DamageModApplyingPower {
    public static final String NAME = "BloodNoPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public BloodNoPower(int n) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = n;
        this.type = PowerType.BUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }
    //打出赤霄时减少一层
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof RedSky){
            this.flash();
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner,  this.ID));
            } else {
                this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }
        }
    }
    //回合结束时退出
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+ this.amount+ DESCRIPTIONS[1];
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && ((AbstractCard) o) instanceof RedSky && list.stream().noneMatch(mod -> mod instanceof LeechDamage);
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new LeechDamage());
    }
}
