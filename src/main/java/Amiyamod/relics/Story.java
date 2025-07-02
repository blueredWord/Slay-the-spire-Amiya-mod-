package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Before;
import Amiyamod.cards.Only;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;

public class Story extends CustomRelic {
    public static final String NAME = "Story";
    public static final String ID = Amiyamod.makeID(NAME);
    public boolean cardsSelected;
    private static final int Max = 5;
    public Story() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/HealCard.png"),
                ImageMaster.loadImage("img/relics/HealCard_out.png"),
                RelicTier.SPECIAL,
                LandingSound.SOLID
        );
    }

    public void onEquip() {
        this.cardsSelected = false;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.getPurgeableCards().group.iterator();

        while(var2.hasNext()) {
            AbstractCard card = (AbstractCard)var2.next();
            tmp.addToTop(card);
        }

        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            if (!AbstractDungeon.isScreenUp) {
                AbstractDungeon.gridSelectScreen.open(tmp, Max,true, "Story");
            } else {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
                AbstractDungeon.gridSelectScreen.open(tmp, Max,true, "Story");
            }

        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !AbstractDungeon.isScreenUp) {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info("触发update"+AbstractDungeon.gridSelectScreen.selectedCards);
            this.giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
        }
    }

    public void giveCards(ArrayList<AbstractCard> group) {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        Iterator<AbstractCard> i = group.iterator();

        while(i.hasNext()) {
            AbstractCard card = (AbstractCard)i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.player.masterDeck.addToTop(new Before(card));
        }

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
    }
    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStartPreDraw(){
        this.addToBot(new MakeTempCardInHandAction(new Only()));
    }


    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new Story();
    }
}
