package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BreakBuilding  extends CustomRelic implements OnLoseTempHpRelic {
    public static final String NAME = "BreakBuilding";
    public static final String ID = Amiyamod.makeID(NAME);
    public static final int time = 4;
    public BreakBuilding() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.RARE,
                LandingSound.SOLID
        );
        this.counter = 0;
        this.cost = 2;
        this.description = getUpdatedDescription() ;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void onTrigger() {
        this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,DamageInfo.createDamageMatrix( this.counter, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        this.reset();
    }

    public void onPlayerEndTurn() {
        if (this.counter>0){
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            this.onTrigger();
        }
    }


    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+this.cost+DESCRIPTIONS[1];
    }
    public void onVictory() {
        this.reset();
    }
    public void atPreBattle() {
        this.reset();
    }
    public void reset(){
        this.counter = 0;
        this.stopPulse();
    }
    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new BreakBuilding();
    }

    public void onLoseHp(int damageAmount) {
        if (damageAmount> 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT ){
            this.counter += damageAmount*this.cost;
            this.beginPulse();
        }
    }
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        int tem=(Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if ( i > 0 && tem >= 0){
            this.onLoseHp(Math.min(tem,i));
        }
        return i;
    }
}
