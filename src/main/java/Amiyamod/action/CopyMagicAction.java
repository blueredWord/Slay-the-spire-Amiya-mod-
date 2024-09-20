package Amiyamod.action;

import Amiyamod.action.cards.ChoseTempToHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

public class CopyMagicAction extends AbstractGameAction {
    public static final String[] TEXT;
    private float startingDuration;
    private final boolean up;
    public CopyMagicAction(int numCards,boolean cost0) {
        this.amount = numCards;
        this.up = cost0;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            Iterator var1;
            AbstractCard c;
            if (this.duration == this.startingDuration) {

                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }


                if (this.amount != -1) {
                    for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                        tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                    }
                } else {
                    Iterator var5 = AbstractDungeon.player.drawPile.group.iterator();

                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        tmpGroup.addToBottom(c);
                    }
                }
                AbstractDungeon.gridSelectScreen.open(tmpGroup,1, true, TEXT[8]);

            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    AbstractCard ca = c.makeStatEquivalentCopy();
                    if(this.up){
                        ca.setCostForTurn(0);
                    }
                    this.addToTop(new ChoseTempToHandAction(ca));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}

