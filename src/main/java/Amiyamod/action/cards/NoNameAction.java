package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.YCard.KingSay;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class NoNameAction extends AbstractGameAction {


    public NoNameAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }


    public void update() {
        String NAME = "MemoryPower";
        String POWER_ID = Amiyamod.makeID(NAME);
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hand.isEmpty()){
            for (AbstractCard c : p.hand.group){
                if (!c.isEthereal) {
                    c.isEthereal = true;
                    c.rawDescription += DESCRIPTIONS[3];
                }
            }
        }
        this.isDone = true;
    }
}
