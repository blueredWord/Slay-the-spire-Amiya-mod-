package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.ShadowCry;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.WrathParticleEffect;

public class ShadowBlueFirePower extends AbstractPower {
    public static final String NAME = "ShadowBlueFirePower";
    protected float particleTimer;
    protected float particleTimer2;
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public ShadowBlueFirePower() {
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
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
        }
    }

    //打出攻击牌时给赤霄
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(
                (card.type != AbstractCard.CardType.ATTACK)
                        && (!card.hasTag(YCardTagClassEnum.RedSky))
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
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
