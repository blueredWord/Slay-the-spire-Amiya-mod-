package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.power.CardBackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;

public class ShadowUpgradeAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;

    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    boolean up ;

    public ShadowUpgradeAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
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
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, true, true, false, true);
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

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.p.hand.refreshHandLayout();
        this.tickDuration();
    }
    void work(AbstractCard c){
        /*
        if(!c.selfRetain ){
            c.selfRetain = true;
            c.retain = true;
            c.rawDescription = RedSky.CARD_STRINGS.UPGRADE_DESCRIPTION + c.rawDescription;
        }

         */
        //c.upgrade();
        StringBuffer s = new StringBuffer(c.rawDescription);
        c.exhaust = false;
        int f = s.indexOf("消耗 。");

        int p = " NL 消耗 。".length();
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "消耗词条"+p
        );
        while (f != -1){
            for (int z= 0 ;z < 4;z++){
                if (f!= s.length()) {
                    s.deleteCharAt(f);
                }
            }
            f = s.indexOf("消耗 。");
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "消耗词条"+f
            );
        }
        c.rawDescription = s.toString();
        c.superFlash();
        c.applyPowers();
        c.initializeDescription();
        AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        AbstractCard caa = c.makeStatEquivalentCopy();
        caa.rawDescription = c.rawDescription;
        caa.initializeDescription();
        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(caa));
        this.p.hand.moveToDiscardPile(c);
        c.triggerOnManualDiscard();
        GameActionManager.incrementDiscard(false);


        this.addToTop(new ApplyPowerAction(this.p,this.p,new CardBackPower(c)));
    }

    private void returnCards() {

        for (AbstractCard c : this.cannotUpgrade) {
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }

/*
    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.hand.isEmpty() && this.numberOfCards > 0) {
                CardGroup CC = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                CC.group.addAll(this.player.hand.group);
                AbstractDungeon.handCardSelectScreen.open(TEXT[0],this.numberOfCards,true);
                this.tickDuration();
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                    for (int i = 0; i< this.up;i++){
                        if (c.canUpgrade()){
                            c.upgrade();
                            c.superFlash();
                            c.applyPowers();
                        }
                    }
                    StringBuffer s = new StringBuffer(c.rawDescription);


                    if (c.exhaust){
                        c.exhaust = false;
                        int f = s.indexOf("消耗 。");

                        int p = " NL 消耗 。".length();
                        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                "消耗词条"+p
                        );
                        while (f != -1){
                            for (int z= 0 ;z < 4;z++){
                                if (f!= s.length()) {
                                    s.deleteCharAt(f);
                                }
                            }
                            f = s.indexOf("消耗 。");
                            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                    "消耗词条"+f
                            );
                        }
                        c.rawDescription = s.toString();
                    }

                    c.initializeDescription();
                    //this.player.hand.moveToDeck(c, false);
                    //this.addToTop(new ApplyPowerAction(this.player,this.player,new CardBackPower(c)));
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    this.player.hand.moveToDiscardPile(c);
                    //this.addToTop(new ApplyPowerAction(this.player,this.player,new CardBackPower(c)));
                }
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            this.isDone=true;

        }
    }


 */
    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}
