package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BreakHugAction extends AbstractGameAction {
    private int i;
    public BreakHugAction(int i) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.i = i;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> l = new ArrayList<>();
        if (!p.drawPile.isEmpty()&& this.i>0){
            for (AbstractCard c : p.drawPile.group){
                if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                    l.add(c);
                    this.i--;
                }
                if(this.i<=0){break;}
            }
            if (!l.isEmpty()){
                for (AbstractCard c : l){
                    p.drawPile.moveToHand(c);
                }
            }
        }
        this.isDone = true;
    }
}
