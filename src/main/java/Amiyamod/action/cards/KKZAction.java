package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KKZAction extends AbstractGameAction {
    private static final String[] DESCRIPTIONS;
    public KKZAction() {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        if (!DrawCardAction.drawnCards.isEmpty()){
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (!c.hasTag(YCardTagClassEnum.YCard) && !c.hasTag(YCardTagClassEnum.YZuZhou)){
                    c.tags.add(YCardTagClassEnum.YCard);
                    c.tags.add(YCardTagClassEnum.YZuZhou);
                    c.rawDescription += DESCRIPTIONS[2];

                } else if (!c.hasTag(YCardTagClassEnum.YCard)) {
                    c.tags.add(YCardTagClassEnum.YCard);
                    c.rawDescription += DESCRIPTIONS[0];

                } else if (!c.hasTag(YCardTagClassEnum.YZuZhou)){
                    c.tags.add(YCardTagClassEnum.YZuZhou);
                    c.rawDescription += DESCRIPTIONS[1];
                }
                c.initializeDescription();
            }
        }
        DrawCardAction.drawnCards.clear();
        this.isDone = true;
    }
    static {
        DESCRIPTIONS = CardCrawlGame.languagePack.getCardStrings("AmiyaMod:KKZ").EXTENDED_DESCRIPTION;
    }
}
