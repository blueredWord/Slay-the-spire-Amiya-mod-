package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTempToHandAction;
import Amiyamod.cards.CiBeI.PlanB;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.UUID;

public class PlanAPower extends AbstractPower {
    public static final String NAME = "PlanAPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    public UUID uuid;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private String n;
    public PlanAPower(AbstractCard card) {
        this.n = card.name;
        this.name = powerStrings.NAME+this.n;
        this.owner = AbstractDungeon.player;
        this.ID = POWER_ID+card.uuid;
        this.uuid = card.uuid;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = 1;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.uuid == this.uuid){
            this.addToBot(new GainEnergyAction(this.amount*2));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            this.addToBot(new ChoseTempToHandAction(new PlanB().makeCopy()));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+this.n+DESCRIPTIONS[1]+(this.amount*2)+DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
    }

}
