package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.character.Amiya;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ShadowSkyOpenPower extends AbstractPower {
    public static final String NAME = "ShadowSkyOpenPower";
    public static final String POWERID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWERID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final int ketnumber = 2;
    private int n;
    public ShadowSkyOpenPower(int i) {
        this.name = powerStrings.NAME;
        this.ID = POWERID;
        this.owner = AbstractDungeon.player;
        this.amount = i;
        this.type = PowerType.BUFF;
        this.n = ketnumber;
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);

        this.updateDescription();
    }

    public void onInitialApplication() {
        if (this.owner.isPlayer && this.owner instanceof Amiya && this.owner.hasPower(RedSkyPower.POWER_ID)){

            this.owner.state.addAnimation(0, "Skill_2_Idle", true, 0.0F);

        }
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[3]+this.amount+DESCRIPTIONS[4];
        //this.description = DESCRIPTIONS[0]+this.n+DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        /*
        if (card instanceof RedSky){
            --this.n;
            if (this.n == 0) {
                this.flash();
                this.n = ketnumber;
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BigNotWorkPower(this.amount)));
            }
            this.updateDescription();
        }

         */
    }
}
