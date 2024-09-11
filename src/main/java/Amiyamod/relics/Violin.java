package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Violin extends CustomRelic {
    public static final String NAME = "Violin";
    public static final String ID = Amiyamod.makeID(NAME);
    private static final int ti = 14;
    public  Violin() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.UNCOMMON,
                LandingSound.SOLID
        );
    }

    public void atBattleStartPreDraw(){
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        Amiyamod.LinePower(ti);
        this.grayscale = true;
    }
    public void onVictory() {
        this.grayscale = false;
    }
    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+ti+DESCRIPTIONS[1];
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new Violin();
    }
}
