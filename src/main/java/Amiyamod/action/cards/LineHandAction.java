package Amiyamod.action.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.DeckToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Objects;

public class LineHandAction extends AbstractGameAction {
    private String ID;
    public LineHandAction(String id) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.ID = id;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> lis = new ArrayList<>();
        for (AbstractCard c : p.drawPile.group){
            if (Objects.equals(c.cardID, this.ID)){
                lis.add(c);
            }
        }
        if (!lis.isEmpty()){
            for (AbstractCard c : lis){
                p.drawPile.moveToHand(c);
            }
        }
        p.hand.refreshHandLayout();
        this.isDone = true;
    }
}
