package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.power.RedSkyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class ShadowTwoAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final boolean notchip;
    private final int number;

    public ShadowTwoAction(AbstractCard source) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = true;
        this.number = source.magicNumber;
        //boolean upg = source.upgraded;
        this.p = AbstractDungeon.player;
    }

    public ShadowTwoAction(AbstractCard source, boolean notChip) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = notChip;
        this.number = source.magicNumber;
        this.p = AbstractDungeon.player;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (this.notchip) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[1], this.number, true, true);
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.number, true, true);
            }
            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                    if(p.hasPower(RedSkyPower.POWER_ID)){
                        Amiyamod.Sword(false,new ArrayList<>());
                        for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                            AbstractDungeon.player.hand.moveToDiscardPile(c);
                            GameActionManager.incrementDiscard(false);
                            c.triggerOnManualDiscard();
                            if (c.type == AbstractCard.CardType.ATTACK){
                                AbstractDungeon.player.discardPile.moveToHand(c);
                            }
                        }
                    }else{
                        for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                            AbstractDungeon.player.hand.moveToDiscardPile(c);
                            GameActionManager.incrementDiscard(false);
                            c.triggerOnManualDiscard();
                        }
                    }
                    this.addToTop(new DrawCardAction(this.p, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
        TEXT = uiStrings.TEXT;
    }
}
