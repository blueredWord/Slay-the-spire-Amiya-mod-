package Amiyamod.action;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;

public class AddLineAction extends AbstractGameAction {
    boolean up = false;
    public AddLineAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.HEAL;
        this.target = target;
        this.source = source;
        this.amount = amount;
        this.amount = Math.max( 0 ,this.amount);
        this.up = amount > 0 ;
    }

    public void update() {
        if (this.duration == 0.5F) {
            this.amount = Math.min( 999 , (Integer)TempHPField.tempHp.get(this.target)+ this.amount );
            TempHPField.tempHp.set(this.target, this.amount );
            if (this.up) {
                AbstractDungeon.effectsQueue.add(new HealEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY, this.amount));
                this.target.healthBarUpdatedEvent();
            }
        }

        this.tickDuration();
    }
}
