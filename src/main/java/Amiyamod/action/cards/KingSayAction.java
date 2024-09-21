package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.YCard.KingSay;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class KingSayAction extends AbstractGameAction {

    private final boolean OK;
    private final static CardStrings CARD_STRINGS ;
    private final AbstractCard c;
    private final int N;
    public KingSayAction(AbstractCard card,boolean addZ) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.OK = addZ;
        this.c = card;
        this.N = card.magicNumber;
    }


    public void update() {
        CardGroup CG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CG.group.addAll(Amiyamod.YZcard);
        ((KingSay)this.c).reset();

        int i = 10 - AbstractDungeon.player.hand.size();
        if (i > 0){
            this.c.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[0];
            this.c.rawDescription += "*";
            AbstractCard card = CG.getRandomCard(true).makeCopy();
            if (this.c.upgraded && card.canUpgrade()){
                card.upgrade();
                card.applyPowers();
            }
            ((KingSay) this.c).list.add(card);
            this.c.cardsToPreview = card.makeSameInstanceOf();
            this.c.rawDescription += card.name;
            this.addToTop(new ChoseTempToHandAction(card));
            this.c.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[2];
            this.c.initializeDescription();
        }
        if(this.OK){
            i = Math.min(this.N,i-1);
            for (int n = 0;n<i;n++){
                this.addToTop(new MakeTempCardInHandAction(Amiyamod.GetNextYcard(true)));
            }
        }
        this.isDone = true;
    }
    static {
        CARD_STRINGS = KingSay.CARD_STRINGS;
    }
}
