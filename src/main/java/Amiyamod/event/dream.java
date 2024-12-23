package Amiyamod.event;

import Amiyamod.Amiyamod;
import Amiyamod.cards.YCard.DrBlood;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class dream extends AbstractImageEvent {
    public static final String ID = Amiyamod.makeID("Dream");
    private static final EventStrings EventString = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DES = EventString.DESCRIPTIONS;
    public static final String[] OP = EventString.OPTIONS;
    private static final int heal = 30 + ((AbstractDungeon.ascensionLevel >= 15)? 30:10);
    private AbstractCard card = new DrBlood().makeCopy();
    public dream() {
        super(EventString.NAME, DES[0], null);

        card.upgrade();
        this.imageEventText.setDialogOption(OP[0],false,card);
        this.imageEventText.setDialogOption(OP[1]+heal+OP[2]);

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
        this.hasDialog = true;
        this.hasFocus = true;

    }

    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                //第一层选择
                switch(buttonPressed) {
                    //选项1 推门（拿卡）
                    case 0:
                        this.imageEventText.updateBodyText(DES[1]);
                        AbstractCard card = new DrBlood().makeCopy();
                        card.upgrade();
                        AbstractEvent.logMetricObtainCard(ID, OP[0], card.makeSameInstanceOf());
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        this.screenNum++;
                        break;

                    //选项2 离开（回血）
                    case 1:
                        this.imageEventText.updateBodyText(DES[3]);
                        AbstractEvent.logMetricHeal(ID,OP[1]+heal+OP[2],heal);
                        AbstractDungeon.player.heal(heal);
                        this.screenNum=99;
                        break;
                    default:
                        //this.imageEventText.updateBodyText(DES[3]);
                        this.screenNum=99;
                        this.openMap();
                        break;
                }

                this.imageEventText.updateDialogOption(0, OP[3]);
                this.imageEventText.clearRemainingOptions();
                break;

//第二层（选择推门的后续）
            case 1:
                this.imageEventText.updateBodyText(DES[2]);
                this.imageEventText.updateDialogOption(0, OP[3]);
                this.imageEventText.clearRemainingOptions();
                this.screenNum=99;
                break;

            default:
                this.openMap();
        }
    }

}
