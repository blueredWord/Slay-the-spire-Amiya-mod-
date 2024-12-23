package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class YjiejinAction extends AbstractGameAction {
    final boolean up;
    final AbstractCard c;
    public YjiejinAction(AbstractCard c) {
        this.actionType = ActionType.DRAW;
        this.up = c.upgraded;
        this.c = c;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.up){
            c.superFlash();
            p.increaseMaxHp(-c.magicNumber,true);
            this.addToTop(new DamageAction(p, new DamageInfo(null, c.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }else {
            this.addToTop(new DamageAction(p, new DamageInfo(null, c.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }

        this.isDone = true;
    }
}

