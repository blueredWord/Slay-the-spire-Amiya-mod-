package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import org.apache.logging.log4j.LogManager;

public class YRing2 extends YRing {
    public static final String NAME = "YRing2";
    public static final String ID = Amiyamod.makeID(NAME);

    public YRing2() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.BOSS,
                LandingSound.SOLID
        );
    }

    public void atTurnStart() {
        this.work(true);
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new YRing2();
    }

    public void obtain() {
        if (AbstractDungeon.player.hasRelic(YRing.ID)) {
            int i = 0;
            for (AbstractRelic r : AbstractDungeon.player.relics){
                if (r instanceof YRing){
                    instantObtain(AbstractDungeon.player, i, false);
                    break;
                }
                i++;
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        AbstractPlayer p = AbstractDungeon.player;
        int i = 0;
        boolean ok = true;
        for (AbstractCard c : p.masterDeck.group){
            if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                i++;
            }
        }
        if (i > 1){
            ok = false;
        }
        return (
                (AbstractDungeon.player.hasRelic(YRing.ID)) && ok
        );
    }
}
