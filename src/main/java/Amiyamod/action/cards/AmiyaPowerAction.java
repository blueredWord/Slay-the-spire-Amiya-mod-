package Amiyamod.action.cards;

import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmiyaPowerAction extends AbstractGameAction {
    private int upi = 0 ;
    public AmiyaPowerAction(int up) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.source = AbstractDungeon.player;
        this.upi = up;
    }

    public void update() {

        this.isDone = true;
    }

}
