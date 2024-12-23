package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;

public class MindBubblePower extends TwoAmountPower implements OnLoseTempHpPower {
    public static final String NAME = "MindBubblePower";
    public static final String POWERID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWERID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    final static int paopao = 9;
    public MindBubblePower(int i) {
        this.name = powerStrings.NAME;
        this.ID = POWERID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = i;
        this.amount2 = i * paopao;
        this.type = PowerType.BUFF;

        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }
    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+this.amount2+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK){
            this.flash();
            int i = Math.max(0,TempHPField.tempHp.get(this.owner)-this.amount2);
            TempHPField.tempHp.set(this.owner,i);
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.amount2 += (stackAmount) * paopao;
        this.flash();
        this.updateDescription();
    }

    public void onRemove() {
        this.addToBot(new DrawCardAction(this.amount));
    }

    public int onLoseTempHp(DamageInfo info, int damageAmount){
        int tem=(Integer) TempHPField.tempHp.get(this.owner);
        if ( damageAmount > 0 && tem >= damageAmount){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "情感泡泡：damageAmount > 0 && tem >= damageAmount"
            );
            this.onLoseHp(damageAmount);

        } else if (damageAmount > tem){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "情感泡泡：damageAmount > tem"
            );
            this.onLoseHp(tem);
        }
        return damageAmount;
    }

    public int onLoseHp(int damageAmount) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "情感泡泡：onLoseHp"+damageAmount
        );
        if (damageAmount>0){
            this.flash();
            if (this.amount2 > damageAmount) {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "情感泡泡：this.amount2 > damageAmount"
                );
                this.amount2 -= damageAmount;
            } else {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "情感泡泡：this.amount2 ！> damageAmount"
                );
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner,  this.ID));
            }
        }
        this.updateDescription();
        return damageAmount;
    }

}
