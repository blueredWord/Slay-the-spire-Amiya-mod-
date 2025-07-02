package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class FindMenmoryAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private CardGroup exhumes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private boolean UP;
    public FindMenmoryAction(boolean up) {
        this.p = AbstractDungeon.player;
        this.UP = up;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {

        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            } else {
                Iterator c;
                c = this.p.exhaustPile.group.iterator();
                while(c.hasNext()) {
                    derp = (AbstractCard)c.next();
                    if (derp.hasTag(YCardTagClassEnum.MEMORY)){
                        this.exhumes.addToBottom(derp);
                    }
                }
                if (this.exhumes.isEmpty()) {
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.exhumes, 1, TEXT[0],false);
                    this.tickDuration();
                }
            }
        } else {
            Iterator c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.UP){
                        card.upgrade();
                        Amiyamod.MakeMemoryCard(card);
                    }
                    card.retain = true;
                    this.p.exhaustPile.moveToHand(card);
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
