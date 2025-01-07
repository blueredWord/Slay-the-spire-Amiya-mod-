package Amiyamod;

import Amiyamod.action.AddLineAction;
import Amiyamod.action.MyAutoPlayAction;
import Amiyamod.action.SActino;
import Amiyamod.action.cards.ChoseTempToHandAction;
import Amiyamod.cards.*;
import Amiyamod.cards.Memory.*;
import Amiyamod.cards.RedSky.*;
import Amiyamod.cards.CiBeI.*;
import Amiyamod.cards.YCard.*;
import Amiyamod.cards.Yzuzhou.*;
import Amiyamod.character.Amiya;
import Amiyamod.event.dream;
import Amiyamod.event.story;
import Amiyamod.patches.*;
import Amiyamod.potions.AngryPotion;
import Amiyamod.potions.LovePotion;
import Amiyamod.potions.YPotion;
import Amiyamod.power.*;
import Amiyamod.relics.*;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.helpers.RelicType;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


@SpireInitializer
public class Amiyamod implements
        EditCardsSubscriber,
        EditStringsSubscriber,
        AddAudioSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,
        PotionGetSubscriber,
        OnStartBattleSubscriber,
        EditCharactersSubscriber,
        OnPlayerTurnStartSubscriber,
        OnCardUseSubscriber,
        PostInitializeSubscriber
{
    public static final Logger logger = LogManager.getLogger(Amiyamod.class.getSimpleName());
    public static String MOD_ID = "AmiyaMod";
    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }
    public static final Color ColorOut = new Color(0x89643fff);
    public static ArrayList<CustomCard> Yzuzhou = new ArrayList<>();
    public static ArrayList<CustomCard> Yzuzhou2 = new ArrayList<>();
    public static final String DESCRIPTION = "Amiya Mod.";
    public static String Amiya_bgImg = "img/character/Amiya/AmiyaBG1.png"; //选人界面？
    public static ArrayList<AbstractCard> YZcard = new ArrayList<>();
    public static boolean addonRelic = true;    //是否读取mod遗物
    public static Properties AmiyaModDefaults = new Properties();
    public int value = 0;
    public static ArrayList<CustomCard> Rcard = new ArrayList<>();
    public static ArrayList<CustomCard> Mcard = new ArrayList<>();
    public static final Color Amiya_Color = new Color(0x89643fff);
    public static boolean LoseHPthisturn = false;
    public static int Shp = 0;
    public static int playerLine = 0;
    public static boolean Tor;

    public Amiyamod(){
        logger.debug("Constructor started.");
        BaseMod.subscribe(this);
        //BaseMod.addSaveField(makeID(MOD_ID+":save_field"), );
        //CaseMod.subscribe(this);
        BaseMod.addColor(CardColorEnum.AMIYA,
                Amiya_Color,  Amiya_Color, Amiya_Color,Amiya_Color, Amiya_Color, Amiya_Color,Amiya_Color,
                "img/cardui/512/bg_attack_lime.png",
                "img/cardui/512/bg_skill_lime.png",
                "img/cardui/512/bg_power_lime.png",
                "img/cardui/512/card_lime_orb.png",
                "img/cardui/1024/bg_attack_lime.png",
                "img/cardui/1024/bg_skill_lime.png",
                "img/cardui/1024/bg_power_lime.png",
                "img/cardui/1024/card_lime_orb.png",
                "img/cardui/512/card_lime_small_orb.png"
        );
        logger.debug("Constructor finished.");
    }

    public static void initialize() {
        logger.info("========================= 开始初始化 =========================");
        new Amiyamod();
        logger.info("========================= 初始化完成 =========================");
    }

    public static AbstractCard MakeMemoryCard(AbstractCard A){
        if (!A.hasTag(YCardTagClassEnum.MEMORY)){
            String NAME = "MemoryPower";
            String POWER_ID = Amiyamod.makeID(NAME);
            PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
            String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
/*
            if (!A.isEthereal && !A.exhaust){
                A.rawDescription = A.rawDescription+ DESCRIPTIONS[2];
            } else if (!A.exhaust) {
                //原本只虚无
                A.rawDescription = A.rawDescription+ DESCRIPTIONS[4];
            } else {
                A.rawDescription = A.rawDescription+ DESCRIPTIONS[3];
            }

 */

            //添加虚无和消耗
            A.exhaust = true;
            A.isEthereal = true;
            A.selfRetain = false;
            A.keywords.add("Memory");

            if (!A.hasTag(YCardTagClassEnum.MEMORY)){
                A.tags.add(YCardTagClassEnum.MEMORY);
            }
            if (!A.rawDescription.contains(DESCRIPTIONS[8])){
                A.rawDescription = DESCRIPTIONS[8] + A.rawDescription;
            }
            if (!A.name.contains(DESCRIPTIONS[1])){
                A.name = DESCRIPTIONS[1]+A.name;
            }

            A.initializeDescription();
        }
        return A;
    }

    public static void LineLose(int damageAmount){
        playerLine = Math.max(0,playerLine - damageAmount);
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：丝线记录：受伤减少：{},剩余：{}", damageAmount,playerLine
        );
        for (AbstractCard card : AbstractDungeon.player.hand.group){
            if (card instanceof OnLoseTempHpPower){
                ((OnLoseTempHpPower)card).onLoseTempHp(new DamageInfo(AbstractDungeon.player,damageAmount), damageAmount);
            }
        }
        for (AbstractCard card : AbstractDungeon.player.drawPile.group){
            if (card instanceof OnLoseTempHpPower){
                ((OnLoseTempHpPower)card).onLoseTempHp(new DamageInfo(AbstractDungeon.player,damageAmount), damageAmount);
            }
        }
        for (AbstractCard card : AbstractDungeon.player.discardPile.group){
            if (card instanceof OnLoseTempHpPower){
                ((OnLoseTempHpPower)card).onLoseTempHp(new DamageInfo(AbstractDungeon.player,damageAmount), damageAmount);
            }
        }
    }
    //  回合开始接口
    @Override
    public void receiveOnPlayerTurnStart() {
        LoseHPthisturn = false;
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：成功触发回合开始 开始判定丝线减半，playline："+playerLine
        );
        AbstractPlayer p =AbstractDungeon.player;
        //不减半
        if (p.hasPower(SadMindPower.POWER_ID)){
            AbstractPower po = p.getPower(SadMindPower.POWER_ID);
            po.flash();
            if (po.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(po.owner, po.owner, po.ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(po.owner, po.owner, po.ID, 1));
            }
        } else{
            int i = TempHPField.tempHp.get(p);
            int var = playerLine;
            i -= var;
            if (var>0){
                int t = var/2;//减半后的数值
                int t2 = var-t;//减少的量
                i += t;
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：丝线记录：回合开始的减半,剩余：{}",t
                );

                playerLine = t;

                if(!p.powers.isEmpty()){
                    for (AbstractPower po : p.powers){
                        if (po instanceof MindBubblePower){
                            po.flash();
                            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                    "情感泡泡：回合开始减半"
                            );
                            po.onLoseHp(t2);
                        }
                    }
                }
/*

                if (t== 0 && p.hasPower(CRingPower.POWER_ID)){
                    //触发荆棘环的效果
                    ((OnLoseTempHpPower)p.getPower(CRingPower.POWER_ID)).onLoseTempHp(new DamageInfo(p, 1,DamageInfo.DamageType.NORMAL),1);
                }

 */
                TempHPField.tempHp.set(p,i);
            }

            //怪物的丝线 不存在了
            /*
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying) {
                    var = TempHPField.tempHp.get(mo);
                    if (var > 0 ){
                        TempHPField.tempHp.set(mo,var/2);
                    }
                }
            }

             */
        }
    }

    //  源石技艺卡接口
    @Override
    public void receiveCardUsed(AbstractCard Card) {
        if (Card.hasTag(YCardTagClassEnum.YCard)){
            UseYcard(Card);
        }
    }
    public static void UseYcard(AbstractCard Card){
        addY(1);
        if(!Card.purgeOnUse) {
            if (!Card.isInAutoplay) {
                HenJi(1, Card, null);
            }
        }
    }

    //  调动遗物记录源石感染的接口
    public static void addY(int n) {
        AbstractPlayer p = AbstractDungeon.player;
        if (n > 0 && p.hasRelic(YRing.ID) && p.getRelic(YRing.ID).counter>0){
            p.getRelic(YRing.ID).onTrigger();
        } else if ( n > 0 && p.hasRelic(YRing2.ID) && p.getRelic(YRing2.ID).counter>0) {
            p.getRelic(YRing2.ID).onTrigger();
        } else if ( n>0 && p.hasPower(HopePower.POWER_ID) && TempHPField.tempHp.get(p) > 0 ) {
            p.getPower(HopePower.POWER_ID).flash();
            BurnSelf(1);
        } else {
            //if  (p.hasRelic(BurnSkirt.ID)){p.getRelic(BurnSkirt.ID).onTrigger();}
            if (p.hasRelic(Yill.ID)){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：增加感染进度" + n
                );
                CYrelic a =  (CYrelic)p.getRelic(Yill.ID);
                a.addC(n);
            }else {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：增加感染进度：检测到没有源石病，添加遗物ing"
                );
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new Yill(n));
                //AbstractDungeon.actionManager.addToBottom(new addYAction(n));
            }
        }
    }

    //急性感染接口
    public static void HenJi(int number, AbstractCard c, AbstractMonster m,AbstractGameAction act){
        if (!c.purgeOnUse && !c.isInAutoplay){
            HenJi(number,c,m);
            AbstractDungeon.actionManager.addToBottom(act);
        }
    }
    public static void HenJi(int number, AbstractCard c, AbstractMonster m){
        if(!c.purgeOnUse) {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：触发来自{}:{}的急性感染。", c.name, c.uuid
            );
            AbstractPlayer p = AbstractDungeon.player;
            ArrayList<AbstractCard> G = new ArrayList<>();

            if ( !p.hand.isEmpty()){
                for (AbstractCard card : p.hand.group) {
                    if (card.hasTag(YCardTagClassEnum.YZuZhou) && card != c) {
                        G.add(card);
                    }
                }
            }

            //感染爆发时至少触发一次
            if (G.isEmpty() ) {
                if (p.hasPower(YSayPower.POWER_ID)){
                    //BurnSelf(1);

                    AbstractCard tmp = c.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);

                    tmp.current_x = c.current_x;
                    tmp.current_y = c.current_y;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float) Settings.HEIGHT / 2.0F;

                    tmp.freeToPlayOnce = true;
                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addToBottom(new MyAutoPlayAction(tmp,AbstractDungeon.player.limbo));
                    //AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true), true);
                }
            }

            //丢弃诅咒，每丢弃一张触发一次
            for (int i = 0; !G.isEmpty() && i != number; i++) {
                logger.info("模组核心：触发来急性感染,丢弃第"+(i+1)+"张牌");

                if (!p.hasPower(YSayPower.POWER_ID)){
                    BurnSelf(1);
                }

                int aa = AbstractDungeon.cardRng.random(G.size() - 1);
                AbstractCard card = G.get(aa);

                if (card instanceof Ydead){
                    card.triggerOnManualDiscard();
                }
                p.hand.moveToDiscardPile(card);

                //if (p.hasPower(LittleTePower.ID1)){p.getPower(LittleTePower.ID1).onExhaust(card);}
                if (p.hasRelic(BurnSkirt.ID)){p.getRelic(BurnSkirt.ID).onExhaust(card);}

                G.remove(aa);
                AbstractCard tmp;
                if (c instanceof FirstSayA){
                    tmp = new FirstSayA(c.baseDamage,c.baseBlock,c.baseMagicNumber,c.baseDraw,c.baseHeal,0,0,0);
                } else {
                    tmp = c.makeSameInstanceOf();
                }

                AbstractDungeon.player.limbo.addToBottom(tmp);

                tmp.current_x = c.current_x;
                tmp.current_y = c.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;

                tmp.freeToPlayOnce = true;
                tmp.purgeOnUse = true;

                AbstractDungeon.actionManager.addToBottom(new MyAutoPlayAction(tmp,AbstractDungeon.player.limbo));
            }
        }
    }
    //获得丝线接口
    public static void LinePower(int number,AbstractCreature p,boolean isbot){
        boolean sex = false;
        boolean sexU = false;
        if (p.isPlayer){
            for (AbstractCard c:AbstractDungeon.player.hand.group){
                //如果是玩家要获得丝线，检查手里有没有活性化诅咒
                if (c instanceof Ymust){
                    sex = true;
                    if (c.upgraded){
                        sexU = true;
                        break;
                    }
                }
            }
            if (sex){//有活性化诅咒的话改为获得格挡，否则直接获得丝线
                if (sexU){
                    number /= 5;
                    number = Math.max(1,number*5);
                }
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：检测到活性化体制，获得丝线改为获得{}格挡。",number
                );
                if (isbot){
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p,number));
                }else {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p,number));
                }
            }else{
                //记录来自阿米娅mod的丝线量
                playerLine = Math.max(playerLine,0);
                playerLine += number;
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：丝线记录：获得{}丝线,剩余：{}", number,playerLine
                );

                if (isbot){
                    AbstractDungeon.actionManager.addToBottom(new AddLineAction(p,p,number));
                }else {
                    AbstractDungeon.actionManager.addToTop(new AddLineAction(p,p,number));
                }
            }
        } else {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：啥？我没看错吧，怪物获得丝线了"
            );
            if (isbot){
                AbstractDungeon.actionManager.addToBottom(new AddLineAction(p,p,number));
            }else {
                AbstractDungeon.actionManager.addToTop(new AddLineAction(p,p,number));
            }
        }
    }
    //  施加丝线的接口
    public static void LinePower(int number){
        LinePower(number,AbstractDungeon.player,true);//获得丝线的默认对象为玩家
    }
    public static void LinePower(int number,boolean isbot){
        LinePower(number,AbstractDungeon.player, isbot);//获得丝线的默认对象为玩家
    }
    public static void LinePower(int number,AbstractCreature p){
        LinePower(number,p,true);//获得丝线的默认对象为玩家
    }
    //  获取随机的下一张诅咒的接口
    public static AbstractCard GetNextYcard(boolean isTemp) {
        if (isTemp){
            CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            G.group.addAll(Yzuzhou2);
            //Collections.shuffle(Yzuzhou2);
            return G.getRandomCard(true).makeCopy();
        }else {
            ArrayList<AbstractCard> List = new ArrayList<>(Yzuzhou2);
            ArrayList<AbstractCard> List2 = new ArrayList<>();
            ArrayList<AbstractCard> List3 = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group){
                if (c instanceof YCard){
                    List2.add(c);
                }
            }
            if (!List2.isEmpty()){
                for (AbstractCard c : List){
                    boolean ok = true;
                    for (AbstractCard n : List2){
                        if (Objects.equals(n.cardID, c.cardID)){
                            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                    "模组核心：卡组中有了，不加入抽选：{}", c.name
                            );
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                "模组核心：卡组中没有，加入抽选：{}", c.name
                        );
                        List3.add(c.makeCopy());
                    }
                }
            }
            if (List3.isEmpty()){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：发现已经集齐诅咒，开始完全随机获得"
                );
                List3.addAll(Yzuzhou2);
            }
            int i = AbstractDungeon.cardRng.random(List3.size() - 1);
            AbstractCard c = List3.get(i).makeCopy();

            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：获取下一张随机源石诅咒：{}", c.name
            );
            return c;
        }
    }
    //  燃己的接口
    public static void BurnSelf(int number) {
        AbstractPlayer p =AbstractDungeon.player;
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：准备进行"+number+"燃己"
        );
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.addAll(p.hand.group);

        for(int i=0;i < number;i++){
            AbstractDungeon.actionManager.addToTop(
                    new LoseHPAction(p, p, 1)
            );
        }
    }
    //  获得赤霄的接口
    public static void getRedSky(int number) {
        getRedSky(number,true,0);
    }
    public static void getRedSky(int number,boolean isbot) {
        getRedSky(number,isbot,0);
    }
    public static void getRedSky(int number,boolean isbot,int b) {
        int n = number;
        AbstractPlayer p =AbstractDungeon.player;
        if(p.hasPower(ShadowSkyOpenPower.POWERID)){


            AbstractPower po = p.getPower(ShadowSkyOpenPower.POWERID);
            if (po.amount > 0){
                po.flash();
                n += po.amount;
            }
        }
        AbstractCard card = new RedSky(n);
        if (b != 0){
            card.selfRetain = true;
            card.rawDescription = RedSky.CARD_STRINGS.UPGRADE_DESCRIPTION + card.rawDescription;
            card.applyPowers();
            card.initializeDescription();
        }
        if (isbot){
            AbstractDungeon.actionManager.addToBottom(new ChoseTempToHandAction(card));
        }else{
            AbstractDungeon.actionManager.addToTop(new ChoseTempToHandAction(card));
        }
    }
    //  抽到诅咒卡时的通用处理
    public static void WhenYcardDrawn() {

    }
    public static void S(){
        S(1);
    }
    public static void S(int n){
        while (n>0){
            AbstractDungeon.actionManager.addToBottom(new SActino());
            n--;
        }
    }

    //出入鞘相关判定接口
    public static void Sword(boolean isOut,ArrayList<AbstractGameAction> l,ArrayList<AbstractGameAction> l2) {
        AbstractPlayer p = AbstractDungeon.player;
        if(isOut){
            if (p.hasRelic(RhodesSword.ID)){
                ((RhodesSword)(p.getRelic(RhodesSword.ID))).onTrigger(isOut);
            }
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：进入出鞘流程"
            );
            // if isOut  出鞘相关处理
            if (p.hasPower(RedSkyPower.POWER_ID)) {
                //如果已经处于赤霄
                if (l != null && !l.isEmpty()){
                    for (AbstractGameAction a :l){
                        //逐个触发l1列表中的特殊效果
                        AbstractDungeon.actionManager.addToBottom(a);
                    }
                }
            } else {
                //如果不处于赤霄状态，进入赤霄状态
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RedSkyPower()));
                for (AbstractGameAction a :l2){
                    //逐个触发l2列表中的特殊效果
                    AbstractDungeon.actionManager.addToBottom(a);
                }
            }
        }else{
            // ! if isOut  入鞘相关处理
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：进入入鞘流程"
            );
            if (p.hasPower(RedSkyPower.POWER_ID)) {
                if (p.hasRelic(RhodesSword.ID)){
                    ((RhodesSword)(p.getRelic(RhodesSword.ID))).onTrigger(isOut);
                }
                //如果已经处于赤霄，推出赤霄状态
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,RedSkyPower.POWER_ID));
                //并逐个触发特殊效果
                if (l != null && !l.isEmpty()){

                    for (AbstractGameAction a :l){
                        //逐个触发l1列表中的特殊效果
                        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                "模组核心：进入入鞘流程，执行："+ a
                        );
                        AbstractDungeon.actionManager.addToBottom(a);
                    }
                }
            }else{
                for (AbstractGameAction a :l2){
                    //逐个触发l2列表中的特殊效果
                    LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                            "模组核心：进入入鞘流程失败，执行："+ a
                    );
                    AbstractDungeon.actionManager.addToBottom(a);
                }
            }
        }
    }
    public static void Sword(boolean isOut,ArrayList<AbstractGameAction> l) {
        ArrayList<AbstractGameAction> l2 = new ArrayList<AbstractGameAction>();
        Sword(isOut,l,l2);
    }
    //出入鞘相关判定接口的重构，只有一个行动
    public static void Sword(boolean isOut,AbstractGameAction act) {
        ArrayList<AbstractGameAction> l = new ArrayList<AbstractGameAction>();
        l.add(act);
        Sword(isOut,l);
    }
    public static void Sword(boolean isOut,AbstractGameAction act,AbstractGameAction act2) {
        ArrayList<AbstractGameAction> l = new ArrayList<AbstractGameAction>();
        l.add(act);
        ArrayList<AbstractGameAction> l2 = new ArrayList<AbstractGameAction>();
        l2.add(act2);
        Sword(isOut,l,l2);
    }
    public static void Sword(boolean isOut) {
        ArrayList<AbstractGameAction> l = new ArrayList<AbstractGameAction>();
        Sword(isOut,l);
    }
    private static final String CARD_STRING_ZH = "localization/zhs/CardStrings.json";
    private static final String RELIC_STRING_ZH = "localization/zhs/RelicStrings.json";
    private static final String POWER_STRING_ZH = "localization/zhs/PowerStrings.json";
    private static final String POTION_STRING_ZH = "localization/zhs/PotionStrings.json";
    private static final String KEYWORD_STRING_ZH = "localization/zhs/KeywordStrings.json";
    private static final String EVENT_PATH_ZHS = "localization/zhs/EventStrings.json";

    private static final String CARD_STRING_EN = "localization/eng/CardStrings.json";
    private static final String RELIC_STRING_EN = "localization/eng/RelicStrings.json";
    private static final String POWER_STRING_EN = "localization/eng/PowerStrings.json";
    private static final String POTION_STRING_EN = "localization/eng/PotionStrings.json";
    private static final String KEYWORD_STRING_EN = "localization/eng/KeywordStrings.json";
    private static final String EVENT_PATH_EN = "localization/eng/EventStrings.json";

    //############################################################
    // @ 设置自定义角色
    //############################################################
    @Override
    public void receiveEditCharacters() {
        logger.info("========================= Amiya Start Loading =========================");
        BaseMod.addCharacter(
                new Amiya("Amiya"),
                "img/character/Amiya/AmiyaButton.png",
                Amiya_bgImg,AmiyaClassEnum.AMIYA
        );
    }



    @Override
    public void receiveAddAudio() {
        //导入音频
        //BaseMod.addAudio(this.makeID("VO_Kael_Intimidate"), "/audio/sound/Kael/VO/嘲讽2.wav"));
    }


    @Override
    public void receiveEditCards() {
        List<CustomCard> cards = new ArrayList<>();
    //加入卡牌

        cards.add(new AmiyaStrike());

        cards.add(new AmiyaDefend());
        cards.add(new AmiyaMagic());
        cards.add(new PlanA());
        //cards.add(new AmiyaPower());
        cards.add(new MindEat());
        cards.add(new StoneSword());//结晶化剑
        cards.add(new BurnMark());//灼痕
        cards.add(new Memory());
        cards.add(new Hate());
        cards.add(new Finding());
        cards.add(new Punch());
        //源石技艺
        cards.add(new KingType2());
        //cards.add(new MagicPC());
        cards.add(new ChiMeRa());
        cards.add(new MindBreak());
        cards.add(new MindLink());
        //cards.add(new ZuZhouMagic());
        //cards.add(new SuperYPotion()); //强效源石爆发剂
        //cards.add(new StoneBlock());
        cards.add(new PainMagic());
        cards.add(new FastSing());
        cards.add(new LoseWish());
        //cards.add(new OldDoki());//亘古的脉动
        cards.add(new KKZ());
        //cards.add(new MagicBook());
        cards.add(new SoulBurn());
        cards.add(new WhoKnowOne());
        cards.add(new BadMagic());
        cards.add(new TikaziMagic());
        //cards.add(new BreakHug());
        cards.add(new FirstSay());
        //cards.add(new EatZuzhou());//螯合诅咒
        cards.add(new HelpMagic());
        //cards.add(new Ymagic());
        //cards.add(new MagicYuJin());
        cards.add(new DefendMagic());
        cards.add(new SeeMe());
        //cards.add(new NotNow());
        cards.add(new DrBlood());
        cards.add(new CopyMagic());
        //cards.add(new YOpen());//结晶绽放
        cards.add(new Ghost());
        //cards.add(new KingSay());//魔王律令
        //cards.add(new KingSay2());//魔王律令
        //cards.add(new YBBS());

        //源石诅咒
        cards.add(new Ytiruo());
        cards.add(new Ymust());
        cards.add(new Yjiejin());
        cards.add(new Ysnake());
        cards.add(new Yjianwang());
        cards.add(new Ysex());
        cards.add(new Yangry());
        cards.add(new Ychengyin());
        cards.add(new Ydead());

        //慈悲愿景

        cards.add(new FinalSong());

        cards.add(new Hope());
        cards.add(new Horn());
        cards.add(new Wish());
        cards.add(new MindBubble());
        cards.add(new BadZhufu());
        cards.add(new BeautifulLife());
        //cards.add(new BForB());
        cards.add(new Solo());
        cards.add(new BloodPotion());
        //cards.add(new BreakRing());//极限施法
        cards.add(new KingSee());
        cards.add(new CNoC());
        cards.add(new Echo());
        cards.add(new HelBox());
        cards.add(new HELP());
        cards.add(new HerDo());
        cards.add(new HerSee());
        cards.add(new LifeMade());
        //cards.add(new LineBody()); 身上衣
        //cards.add(new LineDefender());
        //cards.add(new LittleTe());
        cards.add(new LittleTe2());//忧她所忧
        cards.add(new DA());
        cards.add(new Mercy());
        //cards.add(new Open());绽放
        //cards.add(new BurnMark());
        cards.add(new SadMind());
        cards.add(new SoulDefend());
        //cards.add(new CRing()); 荆棘环
        cards.add(new LineHand()); //手中线

        cards.add(new MindRoll());
        cards.add(new MakeLine());
        cards.add(new SoulMilk());
        cards.add(new BlackKing());

        //赤霄
        cards.add(new RedSky(true));
        //cards.add(new BloodNo());
        cards.add(new ShadowOut());
        cards.add(new BloodSword());
        cards.add(new AngryForever());
        cards.add(new ShadowRunNight());
        //cards.add(new SoMuchSworld());//无限剑制
        cards.add(new ShadowBack());
        cards.add(new ShadowYangMei());
        //cards.add(new ShadowSkill());//剑诀
        cards.add(new ShadowCry());
        cards.add(new ShadowUpgrade());
        cards.add(new ShadowDefend());
        cards.add(new ShadowTwo());
        cards.add(new ShadowWaterMusic());
        cards.add(new ShadowBlood());
        cards.add(new ShadowSkyOpen());
        cards.add(new SwordHeard());
        cards.add(new ShadowNoShadow());
        cards.add(new ShadowCloudBreak2());
        cards.add(new Bonk());

        //cards.add(new Scout());
        //cards.add(new Scout1());
        //cards.add(new FireAgain());
        //cards.add(new BigNotWork());
        //cards.add(new ShadowBlueFire());
        //cards.add(new ShadowChange());//变式费
        //cards.add(new CloudBreakIn());
        cards.add(new ShadowBreak());//断剑
        //cards.add(new Shadow15());//剑15
        //cards.add(new NoName());
        cards.add(new ShadowBackWindy()); //回风

        //cards.add(new ACE());
        //追忆卡
        Mcard.add(new ACE());
        Mcard.add(new Outcast());
        Mcard.add(new Scout());
        Mcard.add(new Whitemith());
        Mcard.add(new Her());
        Mcard.add(new CountryLover());
        Mcard.add(new ICEStar());
        Mcard.add(new BreakBone());

        //cards.addAll(Mcard);

        List<CustomCard> cYcard =new ArrayList<>();
        for (CustomCard card : cards) {
            logger.debug("{} is loading", card.cardID);
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
            if(card.hasTag(YCardTagClassEnum.YCard)){
                cYcard.add(card);
                YZcard.add(card);
            } else if (card.hasTag(YCardTagClassEnum.RedSky1)) {
                Rcard.add(card);
            }
        }

        Yzuzhou2.add(new Ychengyin());
        Yzuzhou2.add(new Yjiejin());
        Yzuzhou2.add(new Yangry());
        Yzuzhou2.add(new Ydead());
        Yzuzhou2.add(new Yjianwang());
        Yzuzhou2.add(new Ysex());
        Yzuzhou2.add(new Ymust());
        Yzuzhou2.add(new Ysnake());
        Yzuzhou2.add(new Ytiruo());

        Number(cards,"阿米娅卡牌");
        Number(cYcard,"其中的源石技艺牌");
        Number(Rcard,"其中的赤霄相关牌");

        logger.debug("Amiyamod Cards finished.");
    }

    private void Number(List<CustomCard> list,String type){
        int sn =0;
        int an =0;
        int pn =0;

        int wn =0;
        int bn =0;
        int gn =0;

        int pwn =0;
        int pbn =0;
        int pgn =0;

        int swn =0;
        int sbn =0;
        int sgn =0;

        int awn =0;
        int abn =0;
        int agn =0;
        for (AbstractCard card : list){
            if (card.type == AbstractCard.CardType.ATTACK){
                an++;
                if (card.rarity == AbstractCard.CardRarity.COMMON){
                    awn++;
                    wn++;
                } else if (card.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    abn++;
                    bn++;
                } else if (card.rarity == AbstractCard.CardRarity.RARE) {
                    agn++;
                    gn++;
                }
            } else if (card.type == AbstractCard.CardType.SKILL){
                sn++;
                if (card.rarity == AbstractCard.CardRarity.COMMON){
                    swn++;
                    wn++;
                } else if (card.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    sbn++;
                    bn++;
                } else if (card.rarity == AbstractCard.CardRarity.RARE) {
                    sgn++;
                    gn++;
                }
            } else if (card.type == AbstractCard.CardType.POWER) {
                pn++;
                if (card.rarity == AbstractCard.CardRarity.COMMON){
                    pwn++;
                    wn++;
                } else if (card.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    pbn++;
                    bn++;
                } else if (card.rarity == AbstractCard.CardRarity.RARE) {
                    pgn++;
                    gn++;
                }
            }
        }
        logger.info(
                "#已统计来自{}的卡牌共{}张:", type,list.size()
        );
        logger.info(
                "   其中白卡{}张，蓝卡{}张，金卡{}张;攻击牌{}张，技能牌{}张，能力牌{}张", wn, bn, gn, an, sn, pn
        );
        logger.info(
                "   攻击牌中白卡{}张，蓝卡{}张，金卡{}张", awn,abn,agn
        );
        logger.info(
                "   技能牌中白卡{}张，蓝卡{}张，金卡{}张", swn,sbn,sgn
        );
        logger.info(
                "   能力牌中白卡{}张，蓝卡{}张，金卡{}张", pwn,pbn,pgn
        );
    }

    @Override
    public void receivePotionGet(AbstractPotion abstractPotion) {

        //    BaseMod.addPotion(ReserveRunePotion.class,DeathKnight_Color,DeathKnight_Color,DeathKnight_Color,WarlordEmblem.makeID("ReserveRunePotion"),AbstractPlayerEnum.DeathKnight);
    }

//载入遗物
    @Override
    public void receiveEditRelics() {
        logger.debug("Amiyamod relic load start.");
        BaseMod.addRelicToCustomPool(new TheTen(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new TenRelic2(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new YRing(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new YRing2(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new Violin(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new HealCard(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new GoldenStone(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new RhodesSword(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new BurnSkirt(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new BurnSkirt2(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new TenRelic(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new Crystal(),CardColorEnum.AMIYA);
        //BaseMod.addRelicToCustomPool(new MusicBook(),CardColorEnum.AMIYA);
        BaseMod.addRelic(new BottedIce(),RelicType.SHARED);
        BaseMod.addRelic(new Ending(),RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new BreakBuilding(),CardColorEnum.AMIYA);
        BaseMod.addRelic(new UnEndStory(),RelicType.SHARED);
        BaseMod.addRelic(new Yill(),RelicType.SHARED);
        logger.debug("Amiyamod relic load finish.");

        logger.info("准备导入事件");
        BaseMod.addEvent(dream.ID, dream.class, Exordium.ID);
        BaseMod.addEvent(story.ID, story.class, TheBeyond.ID);
        logger.info("应该导入好了？");

        logger.info(
                "尝试加入药水"
        );
        BaseMod.addPotion(
                YPotion.class,
                Color.ORANGE.cpy(),
                Color.RED.cpy(),
                Color.WHITE.cpy(),
                YPotion.ID,
                AmiyaClassEnum.AMIYA
        );
        BaseMod.addPotion(
                LovePotion.class,
                Color.WHITE.cpy(),
                Color.PINK.cpy(),
                Color.CYAN.cpy(),
                LovePotion.ID,
                AmiyaClassEnum.AMIYA
        );

        BaseMod.addPotion(
                AngryPotion.class,
                Color.BLACK.cpy(),
                Color.RED.cpy(),
                Color.BLUE.cpy(),
                AngryPotion.ID,
                AmiyaClassEnum.AMIYA
        );
    }



    private Settings.GameLanguage languageSupport() {
        return Settings.language;
        /*
        switch (Settings.language) {
            case ZHS:
                return Settings.language;
            //case JPN:
            //    return Settings.language;
            default:
                return Settings.GameLanguage.ENG;
        }

         */
    }

    @Override
    public void receiveEditStrings()
    {
        Settings.GameLanguage language = languageSupport();
        String relicStrings,
                cardStrings,
                powerStrings,
                potionStrings,
                eventStrings,

                relic,
                card,
                power,

                potion,
                event;

        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            logger.info("lang == zh");
            card = CARD_STRING_ZH;
            relic = RELIC_STRING_ZH;
            power = POWER_STRING_ZH;
            potion = POTION_STRING_ZH;
            event = EVENT_PATH_ZHS;

        }
        else {
            /*
            logger.info("lang == eng");
            card = CARD_STRING_EN;
            relic = RELIC_STRING_EN;
            power = POWER_STRING_EN;
            potion = POTION_STRING_EN;
            event = EVENT_PATH_EN;

             */
            logger.info("lang == en");
            card = CARD_STRING_EN;
            relic = RELIC_STRING_ZH;
            power = POWER_STRING_ZH;
            potion = POTION_STRING_ZH;
            event = EVENT_PATH_ZHS;
        }

        relicStrings = Gdx.files.internal(relic).readString(
                String.valueOf(StandardCharsets.UTF_8)
        );
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        cardStrings = Gdx.files.internal(card).readString(
                String.valueOf(StandardCharsets.UTF_8)
        );


        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        powerStrings = Gdx.files.internal(power).readString(
                String.valueOf(StandardCharsets.UTF_8)
        );
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        potionStrings = Gdx.files.internal(potion).readString(
                String.valueOf(StandardCharsets.UTF_8)
        );
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);

        eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);

    }


    @Override
    public void receiveEditKeywords() {
        logger.info("阿米娅MOD:导入关键词");

        String keywordsPath;

        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            keywordsPath = KEYWORD_STRING_ZH;
        }else {
            keywordsPath = KEYWORD_STRING_EN;
        }



        Gson gson = new Gson();
        Keywords keywords;
        keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
        for (Keyword key : keywords.keywords) {
            logger.info("阿米娅MOD读取关键词文件 : {}", key.NAMES[0]);
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
        }
        logger.info("阿米娅MOD关键词读取完毕");
    }

    @Override
    //战斗开始接口
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        playerLine = 0;
        Shp = AbstractDungeon.player.currentHealth;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c instanceof OnCombatStartInterface){
                ((OnCombatStartInterface) c).OnCombatStartInterface();
            }
        }
    }
    @Override
    public void receivePostInitialize() {
        try {
            // 设置默认值
            Properties defaults = new Properties();
            defaults.setProperty("torbol", "false");
            defaults.setProperty("skin", "1");

            // 第一个字符串输入你的modid
            SpireConfig config = new SpireConfig(MOD_ID, "save_field", defaults);

            // 如果之前有数据，则读取本地保存的数据，没有就使用上面设置的默认数据
            Tor = config.getBool("torbol");
            Amiya.Skin = config.getInt("skin");

            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：Tor读档：" + Amiyamod.Tor
            );

        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }
    public static void saveData(){
        try {
            SpireConfig config = new SpireConfig(MOD_ID, "save_field");

            if (Tor){
                config.setBool("torbol", true);
            }

            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：Tor存档：" + Amiyamod.Tor
            );
            config.setInt("skin",Amiya.Skin);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Keywords { Keyword[] keywords;}
    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

}