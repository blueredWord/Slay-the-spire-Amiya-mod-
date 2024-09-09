package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DrBloodAction extends AbstractGameAction {

    public DrBloodAction() {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> l = new ArrayList<>();
        if (!p.discardPile.isEmpty()){
            for (AbstractCard c : p.discardPile.group){
                if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                    l.add(c);
                }
            }
            if (!l.isEmpty()){
                for (AbstractCard c : l){
                    p.discardPile.moveToExhaustPile(c);
                }
            }
        }
        this.isDone = true;
    }
}
