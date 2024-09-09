package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;


public class ShadowBackAction extends AbstractGameAction {
    public ShadowBackAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        ArrayList<AbstractCard> addlist = new ArrayList<AbstractCard>();
        //int discost = 0;
        for(AbstractCard c: p.hand.group){
            if(c.type != AbstractCard.CardType.ATTACK ){
                list.add(c);
            }
                /*
                addlist.add(c);
                if (c.canUpgrade()){
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }

                //c.setCostForTurn(c.costForTurn+1);
            }else {
                //discost += c.costForTurn;

            }

                 */
        }


        if (!list.isEmpty()){
            for (AbstractCard ca : list){
                p.hand.moveToDiscardPile(ca);
            }
            for (int i = list.size();i>0;i--){
                this.addToTop(new UpgradeRandomCardAction());
            }
        }
        //this.addToTop(new GainEnergyAction(discost));
        this.isDone = true;
    }
}
