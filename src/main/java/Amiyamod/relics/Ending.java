package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.cards.badY;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Ending extends CustomRelic {
    public static final String NAME = "Ending";
    public static final String ID = Amiyamod.makeID(NAME);
    private static final int N = 42;
    public Ending() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.SPECIAL,
                LandingSound.SOLID
        );
    }

    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(N, true);
    }

    public int changeNumberOfCardsInReward(int numberOfCards) {
        return 0;
    }

    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new Ending();
    }
}
