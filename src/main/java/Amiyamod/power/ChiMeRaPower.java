package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.character.Amiya;
import Amiyamod.patches.LeechDamage;
import Amiyamod.relics.CYrelic;
import Amiyamod.relics.Yill;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;
import java.util.List;

public class ChiMeRaPower extends AbstractPower implements DamageModApplyingPower {
    public static final String NAME = "ChiMeRaPower";
    public static final String POWERID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWERID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    final static int leve  = 2;
    public ChiMeRaPower(int n) {
        this.name = powerStrings.NAME;
        this.ID = POWERID;
        this.owner = AbstractDungeon.player;
        this.amount = n;
        this.type = PowerType.BUFF;

        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);

        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+(leve*100-100)+DESCRIPTIONS[1];
    }
    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        return (float) (damage*leve);
    }

    @Override
    public void onRemove() {
        if (this.owner.isPlayer && this.owner instanceof Amiya){
            //this.owner.state.setAnimation(0, "Skill2_End", false);
            if (this.owner.hasPower(YSayPower.POWER_ID)){
                ((Amiya)this.owner).ChangeA(true);
                this.owner.state.setAnimation(0, "Skill_Begin", false);
                this.owner.state.addAnimation(0, "Skill", true,0.0F);
            } else if (this.owner.hasPower(RedSkyPower.POWER_ID)) {
                ((Amiya)this.owner).ChangeA(false);
                //this.owner.state.addAnimation(0, "Skill_2_Idle", true, 0.0F);
            } else {
                ((Amiya)this.owner).ChangeA(true);
                //this.owner.state.setAnimation(0, "Idle", true);
            }
        }
    }
    public void onInitialApplication() {
        if (this.owner.isPlayer && this.owner instanceof Amiya){
            ((Amiya)this.owner).ChangeA(true);
            this.owner.state.setAnimation(0, "Skill_2_Begin", false);
            this.owner.state.addAnimation(0, "Skill_2", true,0.0F);
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner,  this.ID));
        } else if (this.amount == 1){

            if( Amiya.Skin != 2){
                this.owner.state.setAnimation(0, "Skill_2_End", false);
                this.owner.state.addAnimation(0, "Stun", true,0.0F);
            }else {
                this.owner.state.addAnimation(0, "Skill_Loop_2", true,0.0F);
            }

            this.addToBot(new DiscardAction(this.owner, this.owner, AbstractDungeon.player.hand.size(), true));
            this.addToBot(new PressEndTurnButtonAction());
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && this.amount != -1 && list.stream().noneMatch(mod -> mod instanceof LeechDamage);
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new LeechDamage());
    }
}
