package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class WhitemithAction extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private  boolean up ;

    public WhitemithAction(AbstractCard c) {
        this.multiDamage = c.multiDamage;
        this.damageType = c.damageTypeForTurn;
        this.p = AbstractDungeon.player;
        this.freeToPlayOnce = c.freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.up = c.upgraded;
    }

    public void update() {
        //设置花费不能超过magicnumber
        int effect = EnergyPanel.totalCount ;
        //花费能量

        if (effect > 0 && !this.freeToPlayOnce) {
            this.p.energy.use(effect);
        }

        if (this.up){
            effect ++;
        }

        //X药剂+2次
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        this.addToTop(new DrawCardAction(effect,new DrawCost0Action()));

        this.isDone = true;
    }
}
