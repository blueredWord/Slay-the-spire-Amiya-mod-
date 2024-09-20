package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.cards.RedSky.ShadowCry;
import Amiyamod.character.Amiya;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CuriosityPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;
import org.apache.logging.log4j.LogManager;

public class RedSkyPower extends AbstractPower {
    public static final String NAME = "RedSkyPower";
    protected float particleTimer;
    protected float particleTimer2;
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public RedSkyPower() {
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
    public void updateParticles() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new WrathParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        }
    }

    //打出攻击牌时给赤霄
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(
                (card.type == AbstractCard.CardType.ATTACK)
                 && ( !(card instanceof RedSky) )
                && AbstractDungeon.player.hand.size()<10
        ) {
            if(card.cost == -1){
                this.flash();
                Amiyamod.getRedSky(card.energyOnUse);
            }else if (card.costForTurn >=0) {
                int i = card.costForTurn;
                if (card instanceof ShadowCry){
                    i += card.magicNumber;
                }
                this.flash();
                Amiyamod.getRedSky(i);
            }
        }
    }

    public void onInitialApplication() {
        if (this.owner.isPlayer && this.owner instanceof Amiya){
            ((Amiya)this.owner).ChangeA(false);
            this.owner.state.setAnimation(0, "Start", false);
            if (this.owner.hasPower(ShadowSkyOpenPower.POWERID)){
                this.owner.state.addAnimation(0, "Skill_2_Idle", true, 0.0F);
            } else {
                this.owner.state.addAnimation(0, "Idle", true, 0.0F);
            }
        }
    }
    public void onRemove() {
        if (this.owner.isPlayer && this.owner instanceof Amiya){
            ((Amiya)this.owner).ChangeA(true);
            if (this.owner.hasPower(YSayPower.POWER_ID)){
                this.owner.state.setAnimation(0, "Skill_Begin", false);
                this.owner.state.addAnimation(0, "Skill", true,0.0F);
            } else if (this.owner.hasPower(ChiMeRaPower.POWERID)){
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

    //回合结束时退出
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
