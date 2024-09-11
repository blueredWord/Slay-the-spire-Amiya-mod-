package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ShadowSkyOpenAction extends AbstractGameAction {
    private int v ;
    public ShadowSkyOpenAction(int V) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.v = V;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int i = p.drawPile.size();
        ArrayList<AbstractCard> list =new ArrayList<>();
        if (i>this.v){
            i = i - this.v;
            int po = 0;
            for (int n = 0 ; n<i;n++){
                AbstractCard c = p.drawPile.getTopCard();
                if (c.cost != -2){
                    p.drawPile.moveToDiscardPile(c);
                } else {
                    p.drawPile.moveToExhaustPile(c);
                }
            }
        }
        if (!p.discardPile.isEmpty()){
            for (AbstractCard c : p.discardPile.group){
                if (c.cost == -2){
                    list.add(c);
                }
            }
        }
        if (!list.isEmpty()){
            for (AbstractCard c : list){
                p.discardPile.moveToExhaustPile(c);
            }
        }
        this.isDone = true;
    }
}
