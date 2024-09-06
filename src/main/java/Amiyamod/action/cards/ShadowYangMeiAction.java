package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ShadowYangMeiAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional = false;

    public ShadowYangMeiAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.hand.isEmpty() && this.numberOfCards > 0) {
                if (this.player.hand.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();

                    AbstractCard c;
                    Iterator var5;
                    for(var5 = this.player.hand.group.iterator(); var5.hasNext(); c.retain = true) {
                        c = (AbstractCard)var5.next();
                        cardsToMove.add(c);
                    }

                    this.isDone = true;
                } else {
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, true, TEXT[1]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, TEXT[1], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, true, TEXT[1]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, TEXT[1], false);
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
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    c.retain = true;
                    c.lighten(false);
                    c.unhover();
                }

                for(var1 = this.player.hand.group.iterator(); var1.hasNext(); c.target_y = 0.0F) {
                    c = (AbstractCard)var1.next();
                    c.unhover();
                    c.target_x = (float) CardGroup.DISCARD_PILE_X;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.isDone = true;
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}
