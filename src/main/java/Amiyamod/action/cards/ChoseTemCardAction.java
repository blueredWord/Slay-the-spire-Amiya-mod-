package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class ChoseTemCardAction extends AbstractGameAction {
    private final boolean upgraded;
    private final CardGroup list;
    private int n = 1;
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

    public void update() {
        if (this.duration == this.startDuration) {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "巫术典籍：从{}张源石技艺中做出选择", this.list.size()
            );
            //AbstractDungeon.gridSelectScreen.open(this.list, this.numberOfCards, true, TEXT[0]);
            AbstractDungeon.gridSelectScreen.open(this.list, this.n, CardRewardScreen.TEXT[1], false);
            this.tickDuration();
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard disCard :AbstractDungeon.gridSelectScreen.selectedCards){
                    if (this.upgraded) {
                        disCard.setCostForTurn(0);
                    }
                    disCard.current_x = -1000.0F * Settings.xScale;
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            this.isDone = true;
        }
    }
}