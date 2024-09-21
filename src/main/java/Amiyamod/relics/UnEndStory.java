package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.action.FindExAction;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class UnEndStory extends CustomRelic {
    public static final String NAME = "UnEndStory";
    public static final String ID = Amiyamod.makeID(NAME);
    public  UnEndStory() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.BOSS,
                LandingSound.SOLID
        );
        this.counter = -1;
        this.cost = 1;
    }

    public void onTrigger() {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        G.group.addAll(p.exhaustPile.group);
        G.group.removeAll(AbstractDungeon.actionManager.cardsPlayedThisCombat);
        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard C : G.group){
            boolean OK = true;
            for (AbstractCard card : p.masterDeck.group){
                if (C.hasTag(YCardTagClassEnum.MEMORY) || C.hasTag(AbstractCard.CardTags.HEALING)){
                    OK = false;
                    break;
                }
            }
            if (OK){
                list.addToTop(C);
            }
        }
        if (!list.isEmpty()) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(p,this));
            this.addToBot(new FindExAction(list));
        }
        /*

        CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard C : G.group){
            boolean OK = true;
            for (AbstractCard card : p.masterDeck.group){
                if (C.uuid == card.uuid || C.hasTag(AbstractCard.CardTags.HEALING)){
                    OK = false;
                    break;
                }
            }
            if (OK){
                list.addToTop(C);
            }
        }
        if (!list.isEmpty()) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(p,this));
            this.addToBot(new FindExAction(list));
        }
         */

    }
    public void atTurnStart() {
        this.onTrigger();
    }

    // 返回遗物的描述
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+1+DESCRIPTIONS[1];
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new UnEndStory();
    }

}
