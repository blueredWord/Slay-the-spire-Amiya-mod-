package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

//本回合造成伤害时获得等同于伤害点丝线。
public class MercyPower extends AbstractPower {
    public static final String NAME = "MercyPower";
    public static final String POWERID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWERID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public MercyPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWERID;
        this.owner = owner;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = amount;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    // 效果 : 本回合造成伤害时获得等同于伤害点丝线。
    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        this.flash();
        this.addToBot(new AddTemporaryHPAction(this.owner,this.owner,(int)damage * this.amount));
        return damage;
    }
    // 能力在回合开始时移除
    @Override
    public void atStartOfTurnPostDraw() {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
