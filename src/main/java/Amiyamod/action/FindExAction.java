package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTempToHandAction;
import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class FindExAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private CardGroup exhumes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public FindExAction(CardGroup G) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.G = G ;
    }

    public void update() {

        AbstractCard derp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
            } else if (this.p.exhaustPile.isEmpty() || this.G.isEmpty()) {
                this.isDone = true;
            } else {
                AbstractDungeon.gridSelectScreen.open(this.G, 1, TEXT[0], false);
                this.tickDuration();

            }
        } else {
            Iterator c;
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.addToBot(new ChoseTempToHandAction(Amiyamod.MakeMemoryCard(card.makeStatEquivalentCopy())));
                }
            }
            this.isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
        TEXT = uiStrings.TEXT;
    }
}
