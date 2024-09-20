package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.character.Amiya;
import Amiyamod.relics.CYrelic;
import Amiyamod.relics.TenRelic;
import Amiyamod.relics.Yill;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class YSayPower extends AbstractPower {
    public static final String NAME = "YSayPower";

    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public YSayPower() {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;
        this.type = PowerType.BUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    //回合结束时退出
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    public void onInitialApplication() {
        if (this.owner.isPlayer && this.owner instanceof Amiya && !this.owner.hasPower(RedSkyPower.POWER_ID)){
            this.owner.state.setAnimation(0, "Skill_Begin", false);
            this.owner.state.addAnimation(0, "Skill", true,0.0F);
        }
    }
    public void onRemove() {
        if (this.owner.isPlayer && this.owner instanceof Amiya && !this.owner.hasPower(RedSkyPower.POWER_ID)){
            if (this.owner.hasPower(ChiMeRaPower.POWERID)){
                if (this.owner.getPower(ChiMeRaPower.POWERID).amount != 0){
                    this.owner.state.setAnimation(0, "Skill_2_Begin", false);
                    this.owner.state.addAnimation(0, "Skill_2", true,0.0F);
                }else {
                    this.owner.state.setAnimation(0, "Skill_2_End", false);
                    this.owner.state.addAnimation(0, "Stun", true,0.0F);
                }
            }else {
                this.owner.state.setAnimation(0, "Skill_End", false);
                this.owner.state.addAnimation(0, "Idle", true,0.0F);
            }
        }
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
