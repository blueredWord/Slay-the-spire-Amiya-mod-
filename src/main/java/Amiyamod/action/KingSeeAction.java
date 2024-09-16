package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTemCardAction;
import Amiyamod.action.cards.ChoseTempToHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class KingSeeAction extends AbstractGameAction {
    public static final String[] TEXT;
    private boolean retrieveCard = false;
    private boolean upgraded;
    private int I ;

    public KingSeeAction(int n,boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
        this.I = n;
    }

    public void update() {
        ArrayList<AbstractCard> list = this.generateCardChoices();
        if (list.isEmpty()){
            this.isDone = true;
        }else if (list.size() == 1){
            this.addToTop(new ChoseTempToHandAction(list.get(0)));
            this.isDone = true;
        }else {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                AbstractDungeon.cardRewardScreen.customCombatOpen(this.generateCardChoices(), TEXT[7], true);
                this.tickDuration();
            } else {
                if (!this.retrieveCard) {
                    if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                        AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
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

        while(derp.size() != this.I) {
            boolean dupe = false;
            int roll = AbstractDungeon.cardRandomRng.random(99);
            AbstractCard.CardRarity cardRarity;
            if (roll < 40) {
                cardRarity = AbstractCard.CardRarity.COMMON;
            } else if (roll < 70) {
                cardRarity = AbstractCard.CardRarity.UNCOMMON;
            } else if (roll < 90){
                cardRarity = AbstractCard.CardRarity.RARE;
            } else {
                cardRarity = AbstractCard.CardRarity.SPECIAL;
            }

            AbstractCard tmp ;;
            Iterator var6 = derp.iterator();
            if (cardRarity != AbstractCard.CardRarity.SPECIAL){
                tmp = CardLibrary.getAnyColorCard(cardRarity);
            } else {
                CardGroup CG = new  CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                CG.group.addAll(Amiyamod.Mcard);
                tmp = CG.getRandomCard(true).makeCopy();
            }

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
                derp.add(Amiyamod.MakeMemoryCard(tmp));
            }
        }

        return derp;
    }
    static {
        TEXT = CardCrawlGame.languagePack.getPowerStrings("AmiyaMod:UI").DESCRIPTIONS;
    }
}
