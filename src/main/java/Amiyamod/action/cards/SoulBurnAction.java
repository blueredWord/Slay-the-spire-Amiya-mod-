package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.power.RedSkyPower;
import Amiyamod.power.SoulBurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class SoulBurnAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final boolean notchip;
    private final int number;

    public SoulBurnAction(int i) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = true;
        this.number = i;
        //boolean upg = source.upgraded;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.gridSelectScreen.open(this.p.discardPile, this.number, true, TEXT[0]);
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            int i = AbstractDungeon.gridSelectScreen.selectedCards.size();
            if (i>0){
                for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                    this.p.discardPile.moveToExhaustPile(c);
                }
                this.addToTop(new ApplyPowerAction(p,p,new SoulBurnPower(i)));
            }
            this.isDone=true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
        TEXT = uiStrings.TEXT;
    }
}
