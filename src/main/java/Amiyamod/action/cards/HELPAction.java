package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

// 紧急救援
// 从弃牌堆中选择1（2）张牌放入你的手牌，受到与它们耗能相同的真实伤害。
public class HELPAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional = false;

    public HELPAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.discardPile.isEmpty() && this.numberOfCards > 0) {
                if (this.player.discardPile.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();

                    AbstractCard c;
                    Iterator var5;
                    for(var5 = this.player.discardPile.group.iterator(); var5.hasNext(); c.retain = true) {
                        c = (AbstractCard)var5.next();
                        cardsToMove.add(c);
                    }

                    for(var5 = cardsToMove.iterator(); var5.hasNext(); c.lighten(false)) {
                        c = (AbstractCard)var5.next();
                        if (this.player.hand.size() < 10) {
                            this.player.hand.addToHand(c);
                            this.player.discardPile.removeCard(c);
                        }
                    }

                    this.isDone = true;
                } else {
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                AbstractCard c;
                int cost = 0;
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (this.player.hand.size() < 10) {
                        cost += Math.max(0,c.cost);
                        this.player.hand.addToHand(c);
                        this.player.discardPile.removeCard(c);
                    }
                    c.lighten(false);
                    c.unhover();
                }

                for(var1 = this.player.discardPile.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
                if (cost >0){
                    Amiyamod.BurnSelf(cost);
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
