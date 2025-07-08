package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.patches.LeechDamage;
import Amiyamod.patches.MercyDamage;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;

import java.util.Collections;
import java.util.List;

//慈悲愿景
//本回合造成伤害时获得等同于伤害点丝线。
//implements DamageModApplyingPower
public class MercyPower extends AbstractPower {
    public static final String NAME = "MercyPower";
    public static final String POWERID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWERID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public MercyPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = POWERID;
        this.owner = owner;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // 能力在回合开始时移除
    @Override
    public void atStartOfTurnPostDraw() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }


    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner == this.owner && damageAmount > 0) {
            int i = Math.max(1,damageAmount/2);
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "慈悲愿景：伤害修饰器成功触发，获得丝线{}", i
            );
            Amiyamod.LinePower(i);
        }
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "onAttackedToChangeDamage  "+ damageAmount
        );
        return damageAmount;
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "onAttackToChangeDamage  "+ damageAmount
        );
        return damageAmount;
    }

    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "onInflictDamage  "+ damageAmount
        );
    }
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "atDamageGive  "+ damage
        );
        return damage;
    }

    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "atDamageFinalGive  "+ damage
        );
        return damage;
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                " atDamageFinalReceive  "+ damage
        );
        return damage;
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                " atDamageReceive  "+ damage
        );
        return damage;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                " atDamageGive  "+ damage
        );
        return this.atDamageGive(damage, type);
    }

    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "  atDamageFinalGive  "+ damage
        );
        return this.atDamageFinalGive(damage, type);
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                " atDamageFinalReceive  "+ damage
        );
        return this.atDamageFinalReceive(damage, type);
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                " atDamageReceive  "+ damage
        );
        return this.atDamageReceive(damage, damageType);
    }
/*

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && list.stream().noneMatch(mod -> mod instanceof MercyDamage);
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new MercyDamage());
    }

 */
}
