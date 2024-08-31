package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
//消耗手中所有无法打出的卡牌，然后随机升级等量次数的手牌
public class ShadowBloodAction extends AbstractGameAction {
    private int n ;
    public ShadowBloodAction(int i) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.source = AbstractDungeon.player;
        this.n = i;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup a = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : p.hand.group){
            if (c.cost == -2){
            //if (!c.canUse(p,null)){
                a.addToTop(c);
            }
        }
        if (!a.isEmpty()){
            int i = 0;
            for (AbstractCard c : a.group){
                i += this.n;
                p.hand.moveToExhaustPile(c);
            }
            for (int n = 0;n<i;n++){
                a.clear();
                for (AbstractCard c : p.hand.group){
                    if (c.canUpgrade()){
                        a.addToTop(c);
                    }
                }
                if (a.isEmpty()){
                    break;
                }else {
                    AbstractCard c = a.getRandomCard(true);
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                }
            }
        }
        this.isDone = true;
    }
}
