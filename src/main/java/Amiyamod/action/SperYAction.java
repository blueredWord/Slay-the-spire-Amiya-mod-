package Amiyamod.action;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;


public class SperYAction extends AbstractGameAction {
    private AbstractPlayer p;
    private CardGroup exhumes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public SperYAction() {
        this.p = AbstractDungeon.player;
    }

    public void update() {
        CardGroup Z = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (!p.drawPile.isEmpty()){
            for (
                    AbstractCard c : p.drawPile.group){
                if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                    Z.addToTop(c);
                }
            }
        }

        int n = Math.min(11 - p.hand.size(),Z.size());
        n = Math.max(n,0);
        for(int i = 0; i < n ; i++ ){
            AbstractCard c = Z.getRandomCard(true);
            p.drawPile.moveToHand(c);
            Z.removeCard(c);
        }

        this.isDone = true;
    }
}
