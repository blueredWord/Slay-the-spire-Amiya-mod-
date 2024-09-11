package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.EatZuzhouPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EatZuzhouAction extends AbstractGameAction {
    public static final String[] TEXT;
    private final AbstractPlayer player = AbstractDungeon.player;
    private final int numberOfCards;

    private final boolean anyNumber = true;
    private CardGroup G = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    private int tag ;


    public EatZuzhouAction(int numberOfCards, AbstractMonster mo) {
        this.target = mo;
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
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == this.startDuration) {
            //第一动
            if (!this.G.isEmpty() && this.numberOfCards > 0) {
                AbstractDungeon.gridSelectScreen.open( this.G , this.numberOfCards, this.anyNumber, TEXT[5]);
                this.tickDuration();
            } else {
                this.isDone = true;
            }
        } else {
            //第二动
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                //只要选到了东西
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                    this.addToBot(new ApplyPowerAction(this.target,this.target,new EatZuzhouPower(c,this.target)));
                    for (AbstractCard card : p.hand.group){
                        if (card.uuid == c.uuid){
                            p.hand.moveToExhaustPile(card);
                            break;
                        }
                    }

                    c.moveToDiscardPile();
                    AbstractDungeon.player.drawPile.moveToHand(c);
                }
            }
            this.isDone = true;
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}
