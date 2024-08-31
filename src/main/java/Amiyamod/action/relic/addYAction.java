package Amiyamod.action.relic;
import Amiyamod.relics.CYrelic;
import Amiyamod.relics.Yill;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class addYAction extends AbstractGameAction {
    private int n ;
    public addYAction(int mo) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.source = AbstractDungeon.player;
        this.n = mo;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic(Yill.ID)){
            CYrelic a =  (CYrelic)p.getRelic(Yill.ID);
            a.addC(this.n);
        }
        this.isDone = true;
    }
}
