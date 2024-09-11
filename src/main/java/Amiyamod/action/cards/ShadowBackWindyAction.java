package Amiyamod.action.cards;

import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ShadowBackWindyAction extends AbstractGameAction {
    private int i ;
    public ShadowBackWindyAction(int i) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.source = AbstractDungeon.player;
        this.i=i;
    }

    public void update() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo,this.i, false),this.i, true, AbstractGameAction.AttackEffect.NONE));
        }
        this.isDone = true;
    }
}
