package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShadowRunNightAction extends AbstractGameAction {

    public ShadowRunNightAction(AbstractMonster mo) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.target = mo;
        this.source = AbstractDungeon.player;
    }

    public void update() {
        AbstractMonster m = (AbstractMonster) this.target;
        if (!m.isDead && m.getIntentBaseDmg() > 0) {
            this.addToTop(new GainBlockAction(this.source,this.source,m.getIntentBaseDmg()/2));
        }
        this.isDone = true;
    }
}
