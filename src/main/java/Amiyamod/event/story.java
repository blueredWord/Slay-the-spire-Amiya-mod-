package Amiyamod.event;

import Amiyamod.Amiyamod;
import Amiyamod.cards.AmiyaPower;
import Amiyamod.cards.AmiyaStrike;
import Amiyamod.cards.YCard.DrBlood;
import Amiyamod.cards.Yzuzhou.FullCry;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.relics.Ending;
import Amiyamod.relics.UnEndStory;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class story extends AbstractImageEvent {
    public static final String ID = Amiyamod.makeID("Story");
    private static final EventStrings EventString = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DES = EventString.DESCRIPTIONS;
    public static final String[] OP = EventString.OPTIONS;

    private static final int Agold = 30 + ((AbstractDungeon.ascensionLevel >= 15)? 10:30);
    private AbstractCard Acard = null;

    private static final int Bdamage = 20 + ((AbstractDungeon.ascensionLevel >= 15)? 10:0);
    private AbstractCard Ccard = null ;

    private AbstractCard Dcard2 = new FullCry();//诅咒
    //private static final Ab =

    //private AbstractCard card;
    public story() {
        super(EventString.NAME, DES[0], null);

        this.imageEventText.setDialogOption(OP[0]);
        this.imageEventText.setDialogOption(OP[1]);
        this.imageEventText.setDialogOption(OP[2]+Agold+OP[3]);

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.EVENT;
        this.hasDialog = true;
        this.hasFocus = true;
    }
    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                //第一层选择 赤金的国王
                this.screenNum ++;
                switch(buttonPressed) {
                    //选项1 前进
                    case 0:
                        this.imageEventText.updateBodyText(DES[1]);
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;
                    //选项2 一起 拿牌
                    case 1:
                        this.imageEventText.updateBodyText(DES[2]);
                        this.Acard = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                        this.Acard.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(this.Acard, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        AbstractEvent.logMetricObtainCard(EventString.NAME,"Together",this.Acard.makeSameInstanceOf());
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项3 拿钱结束
                    default:
                        this.screenNum=99;
                        this.imageEventText.updateBodyText(DES[3]);
                        this.imageEventText.updateDialogOption(0, OP[16]);

                        AbstractDungeon.effectList.add(new RainingGoldEffect(Agold));
                        AbstractDungeon.player.gainGold(Agold);
                        AbstractEvent.logMetricGainGold(EventString.NAME,"Take Money",Agold);

                        break;
                }
                this.imageEventText.clearRemainingOptions();
                break;

            //第一层 - 第二层
            case 1:
                this.screenNum ++;
                this.imageEventText.updateBodyText(DES[4]);

                //选项1                          “[战斗] 小布人不会退缩！ #r受到 #r" +数字+ " #r点伤害。"
                this.imageEventText.updateDialogOption(0,OP[4]+Bdamage+OP[5]);

                if (HasLine()) {
                    //有丝线卡时候的选项2         “[缝合] 那么，小布人能不能治好他们？”
                    this.imageEventText.setDialogOption(OP[6]+this.Ccard.name+OP[14],false);
                } else {
                    //没有丝线卡时候的选项2        “[锁定] 需要: 丝线类卡牌。”
                    this.imageEventText.setDialogOption(OP[7],true);
                }
                //选项3                        “[逃离] 小布人不可能是他们的对手！"
                this.imageEventText.setDialogOption(OP[8]);
                break;

            //第二层 黑影
            case 2:
                this.screenNum++;
                switch(buttonPressed) {
                    //选项1 战斗（掉血前进）
                    case 0:
                        this.imageEventText.updateBodyText(DES[5]);
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("BLUNT_FAST");
                        AbstractDungeon.player.damage(new DamageInfo(null,Bdamage, DamageInfo.DamageType.HP_LOSS));
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项2 治疗黑影 无伤前进
                    case 1:
                        this.imageEventText.updateBodyText(DES[6]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.Ccard));
                        AbstractDungeon.player.masterDeck.removeCard(this.Ccard);
                        logMetricCardRemoval("Falling", "Removed Skill", this.Ccard.makeCopy());
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项3 结束事件
                    default:
                        this.imageEventText.updateBodyText(DES[7]);
                        this.screenNum=99;
                        this.imageEventText.updateDialogOption(0, OP[16]);
                        break;
                }
                this.imageEventText.clearRemainingOptions();
                break;

            //第二层 - 第三层
            case 3:
                this.screenNum++;
                this.imageEventText.updateBodyText(DES[8]);
                //选项                          “[前进] 独自尝试过河。 获得10最大生命值 被诅咒 浸泪。"
                this.imageEventText.updateDialogOption(0,OP[9]);

                if (AbstractDungeon.player.masterDeck.size() > 29) {
                    //卡组数量大于 的选项         “[前进] 相信朋友们。 获得10最大生命值”
                    this.imageEventText.setDialogOption(OP[10],false);
                } else {
                    //卡组数量没有大于 的选项         “[锁定] 需要: 卡组大小不小于30。”
                    this.imageEventText.setDialogOption(OP[11],true);
                }

                //选项                          “[离开] 布做的小布人会淹死的！获得遗物 未完的故事"
                this.imageEventText.setDialogOption(OP[12],false);
                break;

            case 4:
                this.screenNum++;
                switch(buttonPressed) {
                    //选项1 前进
                    case 0:
                        this.imageEventText.updateBodyText(DES[9]);
                        //AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(Dcard2.makeCopy(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        this.imageEventText.updateDialogOption(0, OP[17]);
                        break;

                    //选项2 卡组数量足够 回血
                    case 1:
                        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                        this.imageEventText.updateBodyText(DES[10]);
                        this.imageEventText.updateDialogOption(0, OP[17]);
                        break;

                    //选项3 拿未完的故事 结束事件
                    default:
                        this.imageEventText.updateBodyText(DES[11]);

                        AbstractRelic aa;
                        if (AbstractDungeon.player.hasRelic(UnEndStory.ID)) {
                            aa = new Circlet();
                        }else {
                            aa = new UnEndStory();
                        }
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, aa);
                        this.imageEventText.updateDialogOption(0, OP[16]);
                        this.screenNum=99;
                        break;
                }
                this.imageEventText.clearRemainingOptions();
                break;

            //第四层 希望平原 Dcard1
            case 5:
                this.imageEventText.updateBodyText(DES[12]);
                this.screenNum++;
                this.imageEventText.removeDialogOption(0);
                //选项                          “[可喜可贺] 故事的最后 所有人都获得了幸福。 获得遗物 希望的结局"
                this.imageEventText.setDialogOption(OP[17],true,new Ending());

                if (buttonPressed == 0) {

                    AbstractRelic aa;
                    if (AbstractDungeon.player.hasRelic(Ending.ID)) {
                        aa = new Circlet();
                    }else {
                        aa = new Ending();
                    }

                    this.imageEventText.updateDialogOption(0, OP[13]);
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, aa);

                    //选项3 拿未完的故事 结束事件
                }
                break;

            //结束事件
            case 6:
                this.screenNum=99;
                this.openMap();
                break;
            default:
                this.openMap();
        }
    }

/*
    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                //第一层选择 赤金的国王
                switch(buttonPressed) {
                    //选项1 前进
                    case 0:
                        this.imageEventText.updateBodyText(DES[1]);
                        this.screenNum++;
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项2 一起 拿牌
                    case 1:
                        this.imageEventText.updateBodyText(DES[2]);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(Acard.makeCopy(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        AbstractEvent.logMetricObtainCard(EventString.NAME,"Together",Acard.makeCopy());
                        this.screenNum++;
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项3 拿钱结束
                    default:
                        this.imageEventText.updateBodyText(DES[3]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(Agold));
                        AbstractDungeon.player.gainGold(Agold);
                        this.imageEventText.updateDialogOption(0, OP[16]);
                        AbstractEvent.logMetricGainGold(EventString.NAME,"Take Money",Agold);
                        this.screenNum=99;
                        break;

                }
                this.imageEventText.clearRemainingOptions();
                break;

            //第二层 黑影
            case 1:
                this.imageEventText.updateBodyText(DES[4]);

                //选项1                          “[战斗] 小布人不会退缩！ #r受到 #r" +数字+ " #r点伤害。"
                this.imageEventText.updateDialogOption(0,OP[4]+Bdamage+OP[5]);

                if (HasLine()) {
                    //有丝线卡时候的选项2         “[缝合] 那么，小布人能不能治好他们？”
                    this.imageEventText.updateDialogOption(1,OP[6]+this.Ccard.name+OP[13],false);
                } else {
                    //没有丝线卡时候的选项2        “[锁定] 需要: 丝线类卡牌。”
                    this.imageEventText.updateDialogOption(1,OP[7],true);
                }
                //选项3                        “[逃离] 小布人不可能是他们的对手！"
                this.imageEventText.updateDialogOption(2,OP[8]);

                this.imageEventText.clearRemainingOptions();

                switch(buttonPressed) {
                    //选项1 战斗（掉血前进）
                    case 0:
                        this.imageEventText.updateBodyText(DES[5]);
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("BLUNT_FAST");
                        AbstractDungeon.player.damage(new DamageInfo(null,Bdamage, DamageInfo.DamageType.HP_LOSS));
                        this.screenNum++;
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项2 治疗黑影 无伤前进
                    case 1:
                        this.imageEventText.updateBodyText(DES[6]);
                        AbstractDungeon.effectList.add(new PurgeCardEffect(this.Ccard));
                        AbstractDungeon.player.masterDeck.removeCard(this.Ccard);
                        logMetricCardRemoval("Falling", "Removed Skill", this.Ccard.makeCopy());
                        this.screenNum++;
                        this.imageEventText.updateDialogOption(0, OP[15]);
                        break;

                    //选项3 结束事件
                    default:
                        this.imageEventText.updateBodyText(DES[7]);
                        this.screenNum=99;
                        this.imageEventText.updateDialogOption(0, OP[16]);
                        break;
                }
                break;

            //第三层 泪之河
            case 2:
                this.imageEventText.updateBodyText(DES[8]);
                this.imageEventText.clearRemainingOptions();
                //选项                          “[前进] 小布人试试渡过这条河 获得10最大生命值 被诅咒 浸泪。"
                this.imageEventText.setDialogOption(OP[9],false,Dcard2.makeCopy());

                if (AbstractDungeon.player.masterDeck.size() > 29) {
                    //卡组数量大于 的选项         “[前进] 相信朋友们 获得10最大生命值”
                    this.imageEventText.setDialogOption(OP[10],false);
                } else {
                    //卡组数量没有大于 的选项         “[锁定] 需要: 卡组大小不小于30。”
                    this.imageEventText.setDialogOption(OP[11],true);
                }

                //选项                          “[离开] 布做的小布人会淹死的！获得遗物 未完的故事"
                this.imageEventText.setDialogOption(OP[12],false);

                switch(buttonPressed) {
                    //选项1 被诅咒 前进
                    case 0:
                        this.imageEventText.updateBodyText(DES[9]);
                        AbstractDungeon.player.increaseMaxHp(10, true);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(Dcard2.makeCopy(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        this.screenNum++;
                        break;

                    //选项2 卡组数量足够 无伤前进
                    case 1:
                        AbstractDungeon.player.increaseMaxHp(10, true);
                        this.imageEventText.updateBodyText(DES[10]);
                        this.screenNum++;
                        break;

                    //选项3 拿未完的故事 结束事件
                    default:
                        this.imageEventText.updateBodyText(DES[11]);

                        AbstractRelic aa;
                        if (AbstractDungeon.player.hasRelic(UnEndStory.ID)) {
                            aa = new Circlet();
                        }else {
                            aa = new UnEndStory();
                        }
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, aa);

                        this.screenNum=99;
                        break;
                }

                break;

            //第四层 希望平原 Dcard1
            case 3:
                this.imageEventText.updateBodyText(DES[12]);
                this.imageEventText.clearRemainingOptions();

                //选项                          “[可喜可贺] 故事的最后 所有人都获得了幸福。 获得遗物 希望的结局"
                this.imageEventText.setDialogOption(OP[9],true,Dcard1.makeCopy());

                if (buttonPressed == 0) {
                    this.imageEventText.updateBodyText(DES[13]);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(Dcard1.makeCopy(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));

                    this.screenNum = 99;
                    //选项3 拿未完的故事 结束事件
                } else {
                    this.screenNum = 99;
                }
                break;

            //结束事件
            default:
                this.openMap();
        }
    }

 */

    boolean HasLine(){
        //遍历玩家卡组
        CardGroup g = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group){
            //找到丝线卡
            if (card.hasTag(YCardTagClassEnum.LINE)){
                g.addToTop(card);
            }
        }
        if (!g.isEmpty()){
            this.Ccard = g.getRandomCard(true);
        }
        //如果没找到丝线卡 return false
        return !g.isEmpty();
    }
}
