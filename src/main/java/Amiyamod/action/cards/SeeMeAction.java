package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SeeMeAction extends AbstractGameAction {
    final int n;
    public SeeMeAction(int n) {
        this.actionType = ActionType.DRAW;
        this.n = n;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.drawPile.group.isEmpty()){
            for(AbstractCard c : p.drawPile.group){
                if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                    p.drawPile.moveToHand(c);
                    break;
                }
            }
        }
        Amiyamod.LinePower(p.hand.size()*n,false);
        this.isDone = true;
    }
}
