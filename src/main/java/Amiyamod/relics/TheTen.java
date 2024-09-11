package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.cards.AmiyaPower;
import Amiyamod.power.YSayPower;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class TheTen extends CustomRelic implements OnApplyPowerRelic {
    public AbstractRelic makeCopy() { return new TheTen(); }
    public static final String NAME = "TheTen";
    public static final String ID = Amiyamod.makeID(NAME);
    public TheTen() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.BOSS,
                LandingSound.SOLID
        );
    }
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof YSayPower){
            //this.work(false);
            this.addToBot(new RelicAboveCreatureAction( AbstractDungeon.player,this));
            this.flash();
            this.addToBot(new MakeTempCardInHandAction(new AmiyaPower()));
        }
        return true;
    }

    public boolean canSpawn() {
        return ((AbstractDungeon.player.hasRelic(TenRelic.ID)) && AbstractDungeon.player.getRelic(TenRelic.ID).counter<10);
    }
}
