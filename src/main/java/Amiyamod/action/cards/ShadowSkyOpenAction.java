package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ShadowSkyOpenAction extends AbstractGameAction {
    public ShadowSkyOpenAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int i = p.drawPile.size();
        if(i>0){
            i = Math.max(1,i/2);
            int po = 0;
            for (int n = 0 ; n<i;n++){
                AbstractCard c = p.drawPile.getTopCard();
                if (c.cost != -2){
                    p.drawPile.moveToDiscardPile(c);
                } else {
                    p.drawPile.moveToExhaustPile(c);
                    po++;
                }
            }
        }
        this.isDone = true;
    }
}
