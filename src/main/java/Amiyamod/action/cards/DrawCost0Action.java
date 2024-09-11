package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class DrawCost0Action extends AbstractGameAction {
    private int blockGain;

    public DrawCost0Action() {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        Iterator var1 = DrawCardAction.drawnCards.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.costForTurn >0) {
                c.setCostForTurn(0);
            }
        }

        this.isDone = true;
    }
}
