package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ChoseTemCardAction extends AbstractGameAction {
    public static final String[] TEXT;
    private final boolean upgraded;
    private CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private boolean retrieveCard = false;
    private int n = 1;
    private int i1 ;
    private int i2 ;
    public ChoseTemCardAction(CardGroup List, int number, boolean costbocome0) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.upgraded = costbocome0;
        this.list = List;
        this.n = number;
    }
    public ChoseTemCardAction(CardGroup List, int number) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.upgraded = false;
        this.list = List;
        this.n = number;
    }
    public ChoseTemCardAction(CardGroup List) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.upgraded = false;
        this.list = List;
    }
    public ChoseTemCardAction(int n,int n2,boolean b) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.upgraded = b;
        this.i1=n;
        this.i2=n2;
    }

    public void update() {
        ArrayList<AbstractCard> list = this.generateCardChoices();
        if (list.isEmpty()){
            this.isDone = true;
        }else if (list.size() <= this.i2){
            for (AbstractCard c : list){
                this.addToTop(new ChoseTempToHandAction(c));
            }
            this.isDone = true;
        }else {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), TEXT[6], true);
                this.tickDuration();
            } else {
                if (!this.retrieveCard) {
                    if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                        disCard.setCostForTurn(0);
                        disCard.current_x = -1000.0F * Settings.xScale;
                        if (AbstractDungeon.player.hand.size() < 10) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        }

                        AbstractDungeon.cardRewardScreen.discoveryCard = null;
                    }

                    this.retrieveCard = true;
                }

                this.tickDuration();
            }
        }
    }



    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> derp = new ArrayList<>();

        while(derp.size() != this.i1) {
            boolean dupe = false;

            AbstractCard tmp ;;
            Iterator var6 = derp.iterator();
            CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            G.group.addAll(Amiyamod.YZcard);
            tmp = G.getRandomCard(true).makeCopy();

            while(var6.hasNext()) {
                AbstractCard c = (AbstractCard)var6.next();
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                tmp = tmp.makeCopy();
                if (this.upgraded && tmp.canUpgrade()){
                    tmp.upgrade();
                    tmp.applyPowers();
                }
                derp.add(tmp);
            }
        }

        return derp;
    }


    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}