package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.power.CardBackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;

public class OnlyAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;

    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    AbstractCard card ;

    public OnlyAction(AbstractCard c) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.card = c;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;

        //this.up = uptime;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {

            var1 = this.p.hand.group.iterator();
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (!(c instanceof RedSky)) {
                    this.cannotUpgrade.add(c);
                }
            }

            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.cannotUpgrade);

            AbstractDungeon.handCardSelectScreen.open(TEXT[9], 1, true, true, false, false);
            this.tickDuration();

        } else if ( !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved ) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                this.work(c);
                //this.p.hand.addToTop(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.p.hand.refreshHandLayout();
        this.tickDuration();
    }

    void work(AbstractCard c){
        p.hand.moveToExhaustPile(c);
        this.addToBot(new ApplyPowerAction(p,p,new CardBackPower(this.card)));
    }

    private void returnCards() {

        for (AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}
