package Amiyamod.action.cards;

import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class BloodSwordAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();


    public BloodSwordAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
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
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, true);
                this.tickDuration();
                return;
            } else if (this.p.hand.group.size() == 1) {
                this.addToTop(new LoseHPAction(this.p,this.p,this.p.hand.getTopCard().timesUpgraded));

                this.p.hand.getTopCard().upgrade();
                this.p.hand.getTopCard().applyPowers();
                this.p.hand.getTopCard().superFlash();


                this.returnCards();
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();

                this.addToTop(new LoseHPAction(this.p,this.p,c.timesUpgraded));

                c.upgrade();
                c.superFlash();
                c.applyPowers();
                this.p.hand.addToTop(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    private void returnCards() {

        for (AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
        TEXT = uiStrings.TEXT;
    }
}
