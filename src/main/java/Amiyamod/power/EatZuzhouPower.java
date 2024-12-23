package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Yzuzhou.*;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;

import java.util.Objects;

public class EatZuzhouPower extends AbstractPower implements OnLoseTempHpPower {
    public static final String NAME = "EatZuzhouPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private AbstractCard C;
    public EatZuzhouPower(AbstractCard card, AbstractCreature cc,int am) {
        this.name = powerStrings.NAME+":"+card.name;
        this.ID = card.cardID+POWER_ID;
        this.owner = cc;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = am;
        this.type = PowerType.DEBUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
        this.C = card.makeCopy();
        this.updateDescription();
    }
    @Override
    public void updateDescription() {
        if (this.C instanceof Yjianwang){
            //健忘 回合开始时，有 #b50 %的几率给予你 #b", " 点能量。"
            this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1] ;
        } else if (this.C instanceof Ydead) {
            //忘陨 "回合结束时，失去 #b", " 点生命。",
            this.description = DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
        } else if (this.C instanceof Ytiruo) {
            //体弱 "造成的伤害减少 #b", " 点。",
            this.description = DESCRIPTIONS[4]+this.amount+DESCRIPTIONS[5];
        } else if (this.C instanceof Yjiejin) {
            //结晶 "受到的伤害增加 #b", " 点。",
            this.description = DESCRIPTIONS[6]+this.amount+DESCRIPTIONS[7];
        } else if (this.C instanceof Yangry) {
            //狂躁 "单次失去至少 #b8 点生命后，获得 #b", " 层 #y易伤 。",
            this.description = DESCRIPTIONS[8]+this.amount+DESCRIPTIONS[9];
        } else if (this.C instanceof Ysex) {
            //成瘾 "每当你打出 #y源石技艺 时，目标失去所有 #y格挡 。"
            this.description = DESCRIPTIONS[10];
        } else if (this.C instanceof Ysnake) {
            //偏差 "回合开始时，使你额外抽 #b", " 张牌。",
            this.description = DESCRIPTIONS[12]+ this.amount +DESCRIPTIONS[13];
        } else if (this.C instanceof Ychengyin) {
            //成瘾 "每当你打出 #b1 张 #y源石技艺 后，获得 #b", " 层 #y虚弱 。",
            this.description = DESCRIPTIONS[14]+ this.amount +DESCRIPTIONS[15];
        } else if (this.C instanceof Ymust) {
            //偏执 "回合开始时时，下修生命值为为整数。",
            this.description = DESCRIPTIONS[16];
            if (this.amount >1){
                this.description += DESCRIPTIONS[17]+this.amount+DESCRIPTIONS[18];
            }
        }

    }
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount> 0){
            if (this.C instanceof Yangry && damageAmount >= 8){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "螯合诅咒：atEndOfRound()"+this+"却认为狂躁，开始触发"
                );
                this.flash();
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info("// 狂躁效果 易伤{}", damageAmount);
                this.addToBot(new ApplyPowerAction(this.owner,this.owner,new VulnerablePower(this.owner,this.amount,false)));

            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "螯合诅咒：atEndOfTurn()"
        );
        if (this.C instanceof Ydead){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "螯合诅咒："+this+" 开始触发，owner："+this.owner
            );
            this.flashWithoutSound();
            this.playApplyPowerSfx();
            this.addToBot(new LoseHPAction(this.owner,AbstractDungeon.player,this.amount));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (this.C instanceof Ytiruo){
            //肾虚体弱 减少伤害
            return type == DamageInfo.DamageType.NORMAL ? Math.max(0,damage - this.amount) : damage;
        } else {
            return damage;
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (this.C instanceof Yjiejin && type == DamageInfo.DamageType.NORMAL) {
            // 体表结晶增加受伤
            return damage + this.amount;
        } else {
            return damage;
        }
    }


    public void atStartOfTurn() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "螯合诅咒：atStartOfTurn()"
        );
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (this.C instanceof Ysnake){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "螯合诅咒：{} = {}让玩家多抽一张卡",this.C, this.owner
                );
                this.flash();
                this.addToBot(new DrawCardAction(this.owner, this.amount));
            } else if (this.C instanceof Yjianwang){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "螯合诅咒：atEndOfRound()"+this+"却认为健忘，开始触发"
                );
                int roll = AbstractDungeon.cardRandomRng.random(1);
                if (roll == 1){
                    this.flash();
                    this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedBluePower(AbstractDungeon.player, this.amount)));
                }
            } else if (this.C instanceof Ymust){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "螯合诅咒：atEndOfRound()"+this+"却认为偏执，开始触发"
                );
                boolean first = true;
                for (int i = 0 ;i< this.amount;i++){
                    if ((this.owner.currentHealth % 10 != 0) && first ){
                        first = false;
                        //this.owner.currentHealth = this.owner.currentHealth - (this.owner.currentHealth % 5);
                        this.addToBot(new LoseHPAction(this.owner,this.owner,this.owner.currentHealth % 10));
                    } else {
                        this.addToBot(new LoseHPAction(this.owner,this.owner,10));
                    }
                }
            }
        }

    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {

        if (card.hasTag(YCardTagClassEnum.YCard)) {
            if (this.C instanceof Ychengyin){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "螯合诅咒：{} = {}获得虚弱",this.C, this.owner
                );
                this.addToBot(new ApplyPowerAction(this.owner,this.owner,new WeakPower(this.owner,this.amount,false)));
            } else if (this.C instanceof Ysex ) {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "螯合诅咒：{} = {}失去所有格挡",this.C, this.owner
                );
                this.addToTop( new RemoveAllBlockAction(this.owner,this.owner));
            }
        }
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        int tem=(Integer) TempHPField.tempHp.get(this.owner);
        if ( damageAmount > 0 && damageAmount >= 8 && damageAmount - tem < 8 ){
            this.wasHPLost(damageInfo,damageAmount);
        }
        return damageAmount;
    }
}
