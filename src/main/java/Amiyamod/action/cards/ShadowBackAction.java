package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;


public class ShadowBackAction extends AbstractGameAction {
    private int cost;
    public ShadowBackAction(int c) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.cost = c;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        ArrayList<AbstractCard> addlist = new ArrayList<AbstractCard>();
        int discost = 0;
        for(AbstractCard c: p.hand.group){
            if(c.type == AbstractCard.CardType.ATTACK ){
                addlist.add(c);
            }else {
                discost += c.costForTurn;
                list.add(c);
            }
        }
        if(!addlist.isEmpty()) {
            for (AbstractCard c : addlist) {
                c.setCostForTurn(c.costForTurn+1);
            }
        }
        if (!list.isEmpty()){
            for(AbstractCard c : list){
                p.hand.moveToDiscardPile(c);
            }
        }
        this.addToTop(new GainEnergyAction(discost));
        this.isDone = true;
    }

}
