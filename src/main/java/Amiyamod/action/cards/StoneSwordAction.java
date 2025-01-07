package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.cards.Yzuzhou.YCard;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class StoneSwordAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    int up;
    private int no = 0;
    public StoneSwordAction(int u) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.up=u;
        //this.ok = this.p.hasPower(RedSkyPower.POWER_ID);
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
/*
            var1 = this.p.hand.group.iterator();
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                if (!(c instanceof RedSky)) {
                    this.cannotUpgrade.add(c);
                }
            }


 */
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[9], 1, false, false, false, false);
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
                //this.p.hand.addToTop(c);
            }
            this.no += this.up;
            Amiyamod.getRedSky(this.no);
            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.p.hand.refreshHandLayout();
        this.tickDuration();
    }
    void work(AbstractCard c){
        AbstractDungeon.player.hand.moveToExhaustPile(c);
        int i = 0;
        if (c.cost == -1 ){
            i = Math.max(i,EnergyPanel.totalCount) ;
        } else if (c.hasTag(YCardTagClassEnum.YZuZhou)) {
            i = 3 ;
        } else {
            i = Math.max(i,c.costForTurn) ;
        }
        this.no += i;
        //Amiyamod.getRedSky(i);
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
