package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Objects;

public class MusicBook extends CustomRelic {
    public static final String NAME = "MusicBook";
    public static final String ID = Amiyamod.makeID(NAME);
    public MusicBook() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.BOSS,
                LandingSound.SOLID
        );
        this.counter = -1;
        this.cost = 2 ;
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    public void atPreBattle() {
        this.counter = 0;
        this.grayscale = false;
        this.beginPulse();
    }
    public void atTurnStart() {
        if (this.counter<this.cost){
            this.counter++;
        }else{
            this.grayscale = true;
            this.stopPulse();
        }
    }

    public void onVictory() {
        this.stopPulse();
        this.grayscale = false;
    }

    public boolean canPlay(AbstractCard card) {
        if (!this.grayscale){
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
                if (Objects.equals(c.cardID, card.cardID)){
                    card.cantUseMessage = DESCRIPTIONS[1];
                    return false;
                }
            }
        }
        return true;
    }
    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new MusicBook();
    }

}
