package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import org.apache.logging.log4j.LogManager;

//行她所行效果
// 效果 : 被攻击后对所有敌人造成相当于层数的反伤
public class HerDoPower extends TwoAmountPower implements OnLoseTempHpPower {
    public static final String NAME = "HerDoPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public HerDoPower(int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = amount;
        this.amount2 = 0;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]+ this.amount*this.amount2+ DESCRIPTIONS[2];
    }

    // 效果 :失去生命后造成相当于层数的反伤
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer && this.amount2>0){
            this.flash();
            this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,DamageInfo.createDamageMatrix( this.amount2,true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            this.amount2 = 0;
        }
    }

    public int onLoseTempHp(DamageInfo info, int damageAmount){
        int tem=(Integer) TempHPField.tempHp.get(this.owner);
        if ( damageAmount > 0 && tem >= damageAmount){
            this.onLoseHp(damageAmount);
        }
        return damageAmount;
    }
    public int onLoseHp(int damageAmount) {
        if (damageAmount>0){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "行她所行能力：触发受伤效果"
            );
            this.flash();
            this.amount2 += this.amount;
            //this.addToBot(new SFXAction("ATTACK_HEAVY"));
            //this.addToBot(new VFXAction(this.owner, new CleaveEffect(), 0.1F));
            //this.addToBot(new DamageAllEnemiesAction((AbstractPlayer) this.owner,DamageInfo.createDamageMatrix( this.amount, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE));
        }
        return damageAmount;
    }
}
