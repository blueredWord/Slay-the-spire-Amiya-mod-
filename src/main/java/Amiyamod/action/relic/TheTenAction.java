package Amiyamod.action.relic;

import Amiyamod.Amiyamod;
import Amiyamod.relics.CYrelic;
import Amiyamod.relics.TenRelic2;
import Amiyamod.relics.Yill;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;

public class TheTenAction extends AbstractGameAction {
    public TheTenAction() {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.source = AbstractDungeon.player;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasRelic(Yill.ID)){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：增加感染进度"
            );
            CYrelic a =  (CYrelic)p.getRelic(Yill.ID);
            a.addC(Yill.MAXY);
        }else {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：增加感染进度：检测到没有源石病，添加遗物ing"
            );
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(300, 300, new Yill());
            AbstractDungeon.actionManager.addToBottom(new addYAction(Yill.MAXY));
        }
        this.isDone = true;
    }
}

