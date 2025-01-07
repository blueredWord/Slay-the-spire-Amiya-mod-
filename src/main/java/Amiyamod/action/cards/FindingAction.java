package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Memory.Memory;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class FindingAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
    int number;
    public FindingAction(int number) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.number=number;
    }

    public void update() {
        Iterator var1;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            this.p.hand.group.removeAll(this.cannotUpgrade);
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[9], this.number, true, false, false, false);
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
        for (AbstractCard caa: AbstractDungeon.player.masterDeck.group) {
            if (caa.uuid == c.uuid) {
                if(caa.type != AbstractCard.CardType.CURSE){
                    if(caa.canUpgrade()){
                        caa.upgrade();
                        AbstractDungeon.player.bottledCardUpgradeCheck(caa);
                    }
                    if (c.canUpgrade()){
                        c.upgrade();
                        if (c.hasTag(YCardTagClassEnum.MEMORY)){
                            Amiyamod.MakeMemoryCard(c);
                        }
                    }
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                }else {
                    caa.untip();
                    caa.unhover();
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(caa, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(caa);
                }
                break;
            }
        }
        AbstractDungeon.player.hand.moveToExhaustPile(c);
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
