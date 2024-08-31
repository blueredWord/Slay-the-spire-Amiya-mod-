package Amiyamod.action.cards;

import Amiyamod.power.CardBackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

public class ShadowUpgradeAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional = true;
    private AbstractCard card;
    private boolean ifb = false;

    public ShadowUpgradeAction(AbstractCard numberOfCards,boolean ifa) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards.magicNumber;
        this.card = numberOfCards;
        this.ifb = ifa;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.hand.isEmpty() && this.numberOfCards > 0) {
                if (this.player.hand.size() <= this.numberOfCards && !this.optional) {
                    for (AbstractCard card : this.player.hand.group) {
                        if (card.canUpgrade()) {
                            card.upgrade();
                            card.superFlash();
                            card.applyPowers();
                        }
                        this.addToTop(new ApplyPowerAction(this.player, this.player, new CardBackPower(card)));
                        this.player.hand.moveToDiscardPile(card);
                    }
                    AbstractDungeon.player.hand.refreshHandLayout();
                    this.isDone = true;
                } else {
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(this.player.hand, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }
                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                    if (c.canUpgrade()){
                        c.upgrade();
                        c.superFlash();
                        c.applyPowers();
                    }
                    this.addToTop(new ApplyPowerAction(this.player,this.player,new CardBackPower(c)));
                    this.player.hand.moveToDiscardPile(c);
                }
                AbstractDungeon.player.hand.refreshHandLayout();
            }
            if(this.card.upgraded && this.ifb){
                this.addToTop(new ApplyPowerAction(this.player,this.player,new CardBackPower(this.card)));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
