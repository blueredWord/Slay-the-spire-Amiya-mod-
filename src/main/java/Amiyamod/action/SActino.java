package Amiyamod.action;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Iterator;

public class SActino extends AbstractGameAction {

    public SActino() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractPlayer p =AbstractDungeon.player;
        boolean ok = false;

        if (!p.hand.isEmpty()){
            for(AbstractCard c : p.hand.group){
                if (c instanceof RedSky){
                    ok = true;
                    c.upgrade();
                    c.applyPowers();
                    c.initializeDescription();
                    c.superFlash();
                }
            }
        }

        if (!ok){
            Amiyamod.getRedSky(0,false);
        }
        this.isDone = true;
    }
}
