package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;

public class RhodesSword extends CustomRelic {
    private boolean out = true;
    private boolean in = true;
    public static final String NAME = "RhodesSword";
    public static final String ID = Amiyamod.makeID(NAME);
    public RhodesSword() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.RARE,
                LandingSound.SOLID
        );
    }

    public void atPreBattle() {
        work(true);
    }
    public void atTurnStart() {
        this.work(true);
    }
    public void onTrigger(boolean isout) {
        if (isout && this.out){
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            this.addToBot(new DrawCardAction(1));
            this.out = false;
        } else if (!isout && this.in){
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            this.addToBot(new GainEnergyAction(1));
            this.in = false;
        }
    }
    // 返回遗物的描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void work(boolean f) {
        this.out = f;
        this.in = f;
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new RhodesSword();
    }
}
