package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;

public class MagicPCAction extends AbstractGameAction {
    public static final String[] TEXT;
    AbstractPlayer p ;
    final int number;
    public MagicPCAction(int a) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        number = a;
        this.p = AbstractDungeon.player;
    }

    public void update() {

            Iterator var1;
            AbstractCard c;

            if (this.duration == Settings.ACTION_DUR_FAST) {
                if (this.p.hand.group.size() > 1) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0],number, true, true, false, false);
                    this.tickDuration();
                    return;
                } else if (this.p.hand.group.size() == 1) {
                    this.work(this.p.hand.getTopCard());
                    this.isDone = true;
                }
            } else if ( !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved ) {
                var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    this.work(c);
                    this.p.hand.addToTop(c);
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
            }

        this.p.hand.refreshHandLayout();
        this.tickDuration();
    }

    void work(AbstractCard c){
        if (!c.hasTag(YCardTagClassEnum.YCard) && !c.selfRetain ){
            c.rawDescription = TEXT[3] + c.rawDescription;
        } else if (!c.selfRetain){
            c.rawDescription = TEXT[2] + c.rawDescription;
        } else if (!c.hasTag(YCardTagClassEnum.YCard)) {
            c.rawDescription = TEXT[1] + c.rawDescription;
        }
        c.selfRetain = true;
        c.tags.add(YCardTagClassEnum.YCard);
        c.applyPowers();
        c.initializeDescription();
        c.superFlash();
    }


    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI2").DESCRIPTIONS;
    }
}
