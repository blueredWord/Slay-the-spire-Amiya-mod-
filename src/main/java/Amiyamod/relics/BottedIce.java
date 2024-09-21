package Amiyamod.relics;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;

import java.util.Collections;
import java.util.Iterator;

public class BottedIce extends CustomRelic implements CustomSavable<Integer> {
    public static final String NAME = "BottedIce";
    private boolean cardSelected = true;
    public static final String ID = Amiyamod.makeID(NAME);
    public AbstractCard card = null;
    public BottedIce() {
        super(
                ID,
                ImageMaster.loadImage("img/relics/"+NAME+".png"),
                ImageMaster.loadImage("img/relics/"+NAME+"_out.png"),
                RelicTier.UNCOMMON,
                LandingSound.SOLID
        );
    }
    // 保存
    @Override
    public Integer onSave() {
        return  AbstractDungeon.player.masterDeck.group.indexOf(this.card);
    }

    // 读取
    @Override
    public void onLoad(Integer cd) {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.masterDeck.isEmpty()){
            this.card = AbstractDungeon.player.masterDeck.group.get(cd);
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info("瓶装冰霜：开始读取{}", this.card);
            //this.card.inBottleTornado = true;
            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractCard getCard() {
        return this.card.makeCopy();
    }

    public void onEquip() {
        AbstractPlayer p = AbstractDungeon.player;
        CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (!p.masterDeck.isEmpty()){
            for (AbstractCard c : p.masterDeck.group){
                if (c.hasTag(YCardTagClassEnum.YCard)){
                    G.addToBottom(c);
                }
            }
        }
        if (!G.isEmpty()) {
            this.cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(G, 1, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }

    }


    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }

    }

    public void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void atBattleStart() {
        this.flash();
        AbstractPlayer p  = AbstractDungeon.player;
        this.addToTop(new RelicAboveCreatureAction(p, this));

        if (!p.drawPile.isEmpty()){
            for (AbstractCard c : p.drawPile.group){
                if (c.uuid == this.card.uuid){
                    p.drawPile.removeCard(c);
                    p.drawPile.addToTop(c);
                    c.setCostForTurn(c.costForTurn-1);
                    break;
                }
            }
        }
    }

    public boolean canSpawn() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        do {
            if (!var1.hasNext()) {
                return false;
            }

            c = (AbstractCard)var1.next();
        } while(!c.hasTag(YCardTagClassEnum.YCard) || c.rarity == AbstractCard.CardRarity.BASIC);

        return true;
    }

    public AbstractRelic makeCopy() {
        return new BottedIce();
    }
}
