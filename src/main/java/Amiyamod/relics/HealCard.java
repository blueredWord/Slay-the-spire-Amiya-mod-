package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import org.apache.logging.log4j.LogManager;

public class HealCard extends CustomRelic implements OnLoseTempHpRelic {
    public static final String NAME = "HealCard";
    public static final String ID = Amiyamod.makeID(NAME);
    public static final int time = 4;
    public HealCard() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.COMMON,
                LandingSound.SOLID
        );
        this.counter = 0;
    }

    public void onTrigger() {
        this.flash();
        this.counter++;
        if (this.counter >= time){
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new UpgradeRandomCardAction());
            this.counter = 0;
        }
    }

    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+time+DESCRIPTIONS[1];
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new HealCard();
    }

    public void onLoseHp(int damageAmount) {
        if (damageAmount>0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            this.onTrigger();
        }
    }
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        int tem=(Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if ( i > 0 && tem >= i){
            this.onLoseHp(i);
        }
        return i;
    }
}
