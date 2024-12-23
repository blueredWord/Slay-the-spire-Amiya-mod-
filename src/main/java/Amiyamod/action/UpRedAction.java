package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;

public class UpRedAction extends AbstractGameAction {
    private AbstractPlayer p;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    private final boolean notchip;
    boolean all;
    boolean Redonly = false;
    public UpRedAction(boolean a) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = true;
        this.all = a;
        this.duration = Settings.ACTION_DUR_FAST;
        //boolean upg = source.upgraded;
        this.p = AbstractDungeon.player;
    }
    public UpRedAction(boolean a,boolean b) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = true;
        this.all = a;
        this.duration = Settings.ACTION_DUR_FAST;
        //boolean upg = source.upgraded;
        this.p = AbstractDungeon.player;
        this.Redonly = b;
    }

    public void update() {
        if (this.all){

            AbstractPlayer p =AbstractDungeon.player;
            for(AbstractCard c : p.hand.group){
                if (c instanceof RedSky){
                    c.upgrade();
                    c.applyPowers();
                    c.initializeDescription();
                    c.superFlash();
                }
            }

            this.isDone = true;
        }else {
            Iterator var1;
            AbstractCard c;
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "开始update"
            );
            if (this.duration == Settings.ACTION_DUR_FAST) {

                var1 = this.p.hand.group.iterator();
                while(var1.hasNext()) {

                    c = (AbstractCard)var1.next();
                    if ( !c.canUpgrade() ) {
                        this.cannotUpgrade.add(c);
                    } else if (this.Redonly && !(c instanceof RedSky)) {
                        this.cannotUpgrade.add(c);
                    }
                }

                if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                    this.isDone = true;
                    return;
                }


                this.p.hand.group.removeAll(this.cannotUpgrade);

                if (this.p.hand.group.size() > 1) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0],1, true, true, false, true);
                    this.tickDuration();
                    return;
                } else if (this.p.hand.group.size() == 1) {
                    this.work(this.p.hand.getTopCard());
                    this.returnCards();
                    this.isDone = true;
                }
            } else if ( !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved ) {
                var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    this.work(c);
                    this.p.hand.addToTop(c);
                }

                this.returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
            }
        }
        this.p.hand.refreshHandLayout();
        this.tickDuration();
    }
    void work(AbstractCard c){

        if (c.canUpgrade()){
            c.upgrade();
        }
        c.superFlash();
        c.applyPowers();
        c.initializeDescription();
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
