package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CardBackAction extends AbstractGameAction {
    private ArrayList<AbstractCard> cl = new ArrayList<>();
    public CardBackAction(ArrayList<AbstractCard> list) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        if (!list.isEmpty()){
            this.cl.addAll(list);
        }
    }

    public void update() {
        AbstractPlayer p =AbstractDungeon.player;
        ArrayList<AbstractCard> var = new ArrayList<AbstractCard>();
        if (!this.cl.isEmpty()){
            //去重
            List<AbstractCard> newlist = this.cl.stream().distinct().collect(Collectors.toList());
            for(AbstractCard card : newlist ){
                //遍历弃牌堆
                for (AbstractCard c : p.discardPile.group){
                    if (c.uuid == card.uuid){
                        var.add(c);
                    }
                }
                if(!var.isEmpty()){
                    for (AbstractCard c : var){
                        p.discardPile.moveToHand(c);
                        c.triggerWhenDrawn();
                    }
                }
                var.clear();
                //遍历抽牌堆
                for(AbstractCard c : p.drawPile.group){
                    if (c.uuid == card.uuid){
                        var.add(c);
                    }
                }
                if(!var.isEmpty()){
                    for (AbstractCard c : var){
                        p.drawPile.moveToHand(c);
                        c.triggerWhenDrawn();
                    }
                }
                var.clear();
            }
            p.hand.refreshHandLayout();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.isDone = true;
    }
}
