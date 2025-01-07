package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.Effect.HappyEffect;
import Amiyamod.action.KingSeeAction;
import Amiyamod.cards.Yzuzhou.Ytiruo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;

public class KingSeePower extends AbstractPower {
    public static final String NAME = "KingSeePower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float particleTimer2;
    private boolean T = true;
    public KingSeePower(int n, AbstractCreature mo) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = mo;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = n;
        this.type = PowerType.DEBUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
            this.description = DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
    }
    public void updateParticles() {

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(1.3F, 1.6F);
            AbstractDungeon.effectsQueue.add(new HappyEffect(this.owner,this.T));
            this.T = !this.T;
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return damage-this.amount;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        return damageAmount;
    }

}
