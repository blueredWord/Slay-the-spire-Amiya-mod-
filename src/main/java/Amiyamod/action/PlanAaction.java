package Amiyamod.action;

import Amiyamod.cards.CiBeI.PlanA;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.PlanAPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlanAaction extends AbstractGameAction {
    public PlanAaction() {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        if (!DrawCardAction.drawnCards.isEmpty()){
            for (AbstractCard c : DrawCardAction.drawnCards) {
                this.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new PlanAPower(c)));
            }
        }
        DrawCardAction.drawnCards.clear();
        this.isDone = true;
    }
}
