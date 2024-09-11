package Amiyamod.action.cards;

import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.EatZuzhouPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.UUID;

public class ExAnyWhereCardAction extends AbstractGameAction {

    private final UUID uid;

    public ExAnyWhereCardAction(UUID uuid) {
        this.uid = uuid;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> list = new ArrayList<>();
        //检测手牌
        for (AbstractCard c : p.hand.group){
            if (c.uuid == this.uid){
                list.add(c);
            }
        }
        if (!list.isEmpty()){
            for (AbstractCard c : list){
                p.hand.moveToExhaustPile(c);
            }
        }
        list.clear();

        //监测抽牌
        for (AbstractCard c : p.drawPile.group){
            if (c.uuid == this.uid){
                list.add(c);
            }
        }
        if (!list.isEmpty()){
            for (AbstractCard c : list){
                p.drawPile.moveToExhaustPile(c);
            }
        }
        list.clear();

        //监测弃牌
        for (AbstractCard c : p.discardPile.group){
            if (c.uuid == this.uid){
                list.add(c);
            }
        }
        if (!list.isEmpty()){
            for (AbstractCard c : list){
                p.discardPile.moveToExhaustPile(c);
            }
        }
        list.clear();

        this.isDone = true;
    }
}
