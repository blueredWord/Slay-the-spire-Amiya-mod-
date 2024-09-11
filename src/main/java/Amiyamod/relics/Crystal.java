package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Crystal extends CustomRelic {
    public static final String NAME = "Crystal";
    public static final String ID = Amiyamod.makeID(NAME);
    public  Crystal() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.COMMON,
                LandingSound.SOLID
        );
    }

    public void atBattleStartPreDraw(){
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
        Amiyamod.Sword(true);
    }

    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new Crystal();
    }
}
