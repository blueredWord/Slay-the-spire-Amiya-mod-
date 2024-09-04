package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class TikaziMagicAction extends AbstractGameAction {
    public TikaziMagicAction() {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (!DrawCardAction.drawnCards.isEmpty()){
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.hasTag(YCardTagClassEnum.YZuZhou)||c.hasTag(YCardTagClassEnum.YCard)) {
                    this.addToTop(new GainEnergyAction(1));
                    break;
                }
            }

        }
        this.isDone = true;
    }
}
