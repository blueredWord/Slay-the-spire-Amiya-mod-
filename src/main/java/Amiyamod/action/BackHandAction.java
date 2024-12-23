package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BackHandAction extends AbstractGameAction {
    final AbstractCard ca;
    boolean ex = false;
    public BackHandAction(AbstractCard c) {
        this.ca = c;
    }
    public BackHandAction(AbstractCard c,boolean ex) {
        this.ca = c;
        this.ex = ex;
    }
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (!p.drawPile.group.isEmpty()){
            for (AbstractCard c : p.drawPile.group){
                if (c == this.ca){
                    p.drawPile.moveToHand(this.ca);
                    this.ca.superFlash();
                    this.isDone = true;
                    return;
                }
            }
        }
        if (!p.discardPile.group.isEmpty()){
            for (AbstractCard c : p.discardPile.group){
                if (c == this.ca){
                    p.discardPile.moveToHand(this.ca);
                    this.ca.superFlash();
                    this.isDone = true;
                    return;
                }
            }
        }
        if (this.ex && !p.exhaustPile.group.isEmpty()){
            for (AbstractCard c : p.exhaustPile.group){
                if (c == this.ca){
                    p.exhaustPile.moveToHand(this.ca);
                    this.ca.superFlash();
                    this.isDone = true;
                    return;
                }
            }
        }


        this.isDone = true;
    }
}

