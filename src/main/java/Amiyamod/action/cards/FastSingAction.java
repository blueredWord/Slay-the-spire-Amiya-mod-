package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FastSingAction extends AbstractGameAction {
    public static final String[] TEXT;
    private final AbstractPlayer player = AbstractDungeon.player;
    private final int numberOfCards;

    private CardGroup G = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public FastSingAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.numberOfCards = numberOfCards;
        for (AbstractCard c : this.player.drawPile.group){
            if (c.hasTag(YCardTagClassEnum.YCard)){
                this.G.addToBottom(c);
            }
        }
    }

    public void update() {
        if (this.duration == this.startDuration) {
            //第一动
            if (!this.G.isEmpty() && this.numberOfCards > 0) {
                AbstractDungeon.gridSelectScreen.open( this.G , this.numberOfCards,true, TEXT[5]);
                this.tickDuration();
            } else {
                this.isDone = true;
            }
        } else {
            //第二动
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                //只要选到了东西
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                    if (c.costForTurn>0){
                        c.setCostForTurn(c.costForTurn-1);
                        //c.costForTurn -= 1;
                    }
                    AbstractDungeon.player.drawPile.moveToHand(c);
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}
