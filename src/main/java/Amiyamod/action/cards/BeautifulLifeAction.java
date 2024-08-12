package Amiyamod.action.cards;

import Amiyamod.power.BeautifulLifePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class BeautifulLifeAction extends AbstractGameAction {
    public BeautifulLifeAction() {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (!DrawCardAction.drawnCards.isEmpty()){
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.cost > 0) {
                    c.cost = 0;
                    c.costForTurn = 0;
                    c.isCostModified = true;
                    break;
                }
            }
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BeautifulLifePower(AbstractDungeon.player,DrawCardAction.drawnCards)));
        }


        this.isDone = true;
    }
}
