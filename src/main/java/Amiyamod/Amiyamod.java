package Amiyamod;

import Amiyamod.action.relic.addYAction;
import Amiyamod.cards.*;
import Amiyamod.cards.Memory.*;
import Amiyamod.cards.RedSky.*;
import Amiyamod.cards.CiBeI.*;
import Amiyamod.cards.YCard.*;
import Amiyamod.cards.Yzuzhou.*;
import Amiyamod.character.Amiya;
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
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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
        EditCharactersSubscriber,OnPlayerTurnStartSubscriber{
    public static final Logger logger = LogManager.getLogger(Amiyamod.class.getSimpleName());

    public static String MOD_ID = "AmiyaMod";
    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }
    public static final Color ColorOut = new Color(0x89643fff);
    public static ArrayList<CustomCard> Yzuzhou = new ArrayList<>();
    public static ArrayList<CustomCard> Yzuzhou2 = new ArrayList<>();
    public static final String DESCRIPTION = "Amiya Mod.";
    public static String Amiya_bgImg = "img/character/Amiya/AmiyaBG.png"; //选人界面？
    public static ArrayList<AbstractCard> YZcard = new ArrayList<>();
    public static boolean addonRelic = true;    //是否读取mod遗物
    public static Properties AmiyaModDefaults = new Properties();
    public int value = 0;
    public static ArrayList<CustomCard> Rcard = new ArrayList<>();
    public static ArrayList<CustomCard> Mcard = new ArrayList<>();
    public static final Color Amiya_Color = new Color(0x89643fff);
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
        String NAME = "MemoryPower";
        String POWER_ID = Amiyamod.makeID(NAME);
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

        if (!A.isEthereal && !A.exhaust){
            A.rawDescription = A.rawDescription+ DESCRIPTIONS[2];
        } else if (!A.exhaust) {
            //原本只虚无
            A.rawDescription = A.rawDescription+ DESCRIPTIONS[4];
        } else {
            A.rawDescription = A.rawDescription+ DESCRIPTIONS[3];
        }
        //添加虚无和消耗
        A.exhaust = true;
        A.isEthereal = true;
        //A.purgeOnUse = true;

        A.tags.add(YCardTagClassEnum.MEMORY);
        A.keywords.add("Memory");

        //A.purgeOnUse = true;
        A.name = DESCRIPTIONS[1]+A.name;
        A.initializeDescription();

        return A;
    }

    //  回合开始接口
    @Override
    public void receiveOnPlayerTurnStart() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：成功触发回合开始 开始判定丝线减半"
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
            int var = TempHPField.tempHp.get(p);
            if (var>0){
                int t = var/2;
                if (t== 0 && p.hasPower(CRingPower.POWER_ID)){
                    //触发荆棘环的效果
                    ((OnLoseTempHpPower)p.getPower(CRingPower.POWER_ID)).onLoseTempHp(new DamageInfo(p, 1,DamageInfo.DamageType.NORMAL),1);
                }
                TempHPField.tempHp.set(p,var/2);
            }

            //怪物的丝线
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying) {
                    var = TempHPField.tempHp.get(mo);
                    if (var > 0 ){
                        TempHPField.tempHp.set(mo,var/2);
                    }
                }
            }
        }
    }

    //  调动遗物记录源石感染的接口
    public static void addY(int n) {
        AbstractPlayer p = AbstractDungeon.player;
        if (n > 0 && p.hasRelic(YRing.ID) && p.getRelic(YRing.ID).counter>0){
            p.getRelic(YRing.ID).onTrigger();
        } else if (n > 0 && p.hasRelic(YRing2.ID) && p.getRelic(YRing2.ID).counter>0) {
            p.getRelic(YRing2.ID).onTrigger();
        } else {
            if  (p.hasRelic(BurnSkirt.ID)){
                p.getRelic(BurnSkirt.ID).onTrigger();
            }
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
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(300, 300, new Yill());
                AbstractDungeon.actionManager.addToBottom(new addYAction(n));
            }
        }
    }
    //急性感染接口
    public static void HenJi(int number, AbstractCard c, AbstractMonster m,AbstractGameAction act){
        if (!c.purgeOnUse){
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
                    if (card.hasTag(YCardTagClassEnum.YZuZhou)) {
                        G.add(card);
                    }
                }
            }

            //感染爆发时至少触发一次
            if (G.isEmpty() ) {
                if (p.hasPower(YSayPower.POWER_ID)){
                    AbstractCard tmp = c.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = c.current_x;
                    tmp.current_y = c.current_y;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                    if (m != null) {
                        tmp.calculateCardDamage(m);
                    }
                    tmp.freeToPlayOnce = true;
                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true), true);
                }
            }

            //丢弃诅咒，每丢弃一张触发一次
            for (int i = 0; !G.isEmpty() && i != number; i++) {
                logger.info("模组核心：触发来急性感染,丢弃第"+(i+1)+"张牌");

                int aa = AbstractDungeon.cardRng.random(G.size() - 1);
                AbstractCard card = G.get(aa);
                p.hand.moveToDiscardPile(card);

                //if (p.hasPower(LittleTePower.ID1)){p.getPower(LittleTePower.ID1).onExhaust(card);}
                if (p.hasRelic(BurnSkirt.ID)){p.getRelic(BurnSkirt.ID).onExhaust(card);}

                G.remove(aa);
                AbstractCard tmp;
                if (c instanceof FirstSayA){
                    tmp = new FirstSayA(c.baseDamage,c.baseBlock,c.baseMagicNumber,c.baseDraw,c.baseHeal,0,0,0);
                }else {
                    tmp = c.makeSameInstanceOf();
                }

                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = c.current_x;
                tmp.current_y = c.current_y;
                tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float)Settings.HEIGHT / 2.0F;

                if (m != null) {
                    tmp.calculateCardDamage(m);
                }
                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m,tmp.energyOnUse, true, true), true);
            }
        }
    }
    //获得丝线接口
    public static void LinePower(int number,AbstractCreature p){
        boolean sex = false;
        boolean sexup = false;
        if (p.isPlayer){
            for (AbstractCard c:AbstractDungeon.player.hand.group){
                //如果是玩家要获得丝线，检查手里有没有活性化诅咒
                if (c instanceof Ymust){
                    sex = true;
                    if (c.upgraded){
                        sexup =true;
                        number /= 2;
                        break;
                    }
                    //break;
                }
            }
            if (sex){//有活性化诅咒的话改为获得格挡，否则直接获得丝线
                int i = number / 5;
                i = Math.max(1,i*5);
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：检测到活性化体制，获得丝线改为获得{}格挡。",i
                );
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p,i));
            }else{
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：获得"+number+"点丝线"
                );
                AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p,p,number));
            }
        } else {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：啥？我没看错吧，怪物获得丝线了"
            );
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p,p,number));
        }
    }
    //  施加丝线的接口
    public static void LinePower(int number){
        LinePower(number,AbstractDungeon.player);//获得丝线的默认对象为玩家
    }
    //  获取随机的下一张诅咒的接口
    public static AbstractCard GetNextYcard(boolean isTemp) {
        if (isTemp){
            CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            G.group.addAll(Yzuzhou2);
            //Collections.shuffle(Yzuzhou2);
            return G.getRandomCard(true).makeCopy();
        }else {
            if (Yzuzhou.isEmpty()){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：源石诅咒给完一轮了，重新填装"
                );
                Yzuzhou.add(new Ychengyin());
                Yzuzhou.add(new Yjiejin());
                Yzuzhou.add(new Yangry());
                Yzuzhou.add(new Ydead());
                Yzuzhou.add(new Yjianwang());
                Yzuzhou.add(new Ysex());
                Yzuzhou.add(new Ymust());
                Yzuzhou.add(new Ysnake());
                Yzuzhou.add(new Ytiruo());
            }
            int i = AbstractDungeon.cardRng.random(Yzuzhou.size() - 1);
            AbstractCard c = Yzuzhou.get(i).makeCopy();
            Yzuzhou.remove(i);
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：获取下一张随机源石诅咒：{}", c.name
            );
            return c.makeCopy();
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
        /*
        for (AbstractCard c: list){
            if(c instanceof Ysnake){
                /*
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：认知偏差 要被烫出个很唐的效果了"
                );
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SnakePower(p,1)));


            }else if(c instanceof BurnMark){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：灼痕效果触发"
                );
                p.hand.moveToExhaustPile(c);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c.makeStatEquivalentCopy(), 1));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c.makeStatEquivalentCopy(), 1, false, true));
            }
        }

         */
        for(int i=0;i < number;i++){
            AbstractDungeon.actionManager.addToTop(
                    new LoseHPAction(p, p, 1)
            );
        }
    }
    //  获得赤霄的接口
    public static void getRedSky(int number) {
        getRedSky(number,true);
    }
    public static void getRedSky(int number,boolean isbot) {
        int n = number;
        AbstractPlayer p =AbstractDungeon.player;
        if(p.hasPower(ShadowSkyOpenPower.POWERID)){
            /*
            if (p.getPower(BigNotWorkPower.POWER_ID).amount>BigNotWorkPower.cardsDoubledThisTurn){
                p.getPower(BigNotWorkPower.POWER_ID).flash();
                BigNotWorkPower.cardsDoubledThisTurn++;
                n++;
            }
             */
            AbstractPower po = p.getPower(ShadowSkyOpenPower.POWERID);
            if (po.amount > 0){
                po.flash();
                n += po.amount;
            }
        }

        if (isbot){
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new RedSky(n)));
        }else{
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new RedSky(n)));
        }

    }
    //  抽到诅咒卡时的通用处理
    public static void WhenYcardDrawn() {

    }

    //出入鞘相关判定接口
    public static void Sword(boolean isOut,ArrayList<AbstractGameAction> l,ArrayList<AbstractGameAction> l2) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic(RhodesSword.ID)){
            ((RhodesSword)(p.getRelic(RhodesSword.ID))).onTrigger(isOut);
        }
        if(isOut){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：进入出鞘流程"
            );
            if (p.hasPower(SoMuchSworldPower.POWER_ID) ){
                p.getPower(SoMuchSworldPower.POWER_ID).triggerMarks(null);
            }
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
                //如果已经处于赤霄，推出赤霄状态
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,RedSkyPower.POWER_ID));
                //并逐个触发特殊效果
                if (l != null && !l.isEmpty()){
                    for (AbstractGameAction a :l){
                        //逐个触发l1列表中的特殊效果
                        AbstractDungeon.actionManager.addToBottom(a);
                    }
                }
            }else{
                for (AbstractGameAction a :l2){
                    //逐个触发l2列表中的特殊效果
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

        //cards.add(new AmiyaPower());
        cards.add(new MindEat());
        cards.add(new StoneSword());
        cards.add(new BurnMark());//灼痕
        cards.add(new Memory());
        cards.add(new Hate());


        //源石技艺
        cards.add(new ChiMeRa());
        cards.add(new MindBreak());
        cards.add(new MindLink());
        //cards.add(new ZuZhouMagic());
        cards.add(new SuperYPotion());
        cards.add(new StoneBlock());
        cards.add(new PainMagic());
        cards.add(new FastSing());
        cards.add(new LoseWish());
        cards.add(new OldDoki());
        cards.add(new MagicBook());
        cards.add(new SoulBurn());
        cards.add(new WhoKnowOne());
        cards.add(new BadMagic());
        cards.add(new TikaziMagic());
        cards.add(new BreakHug());
        cards.add(new FirstSay());
        cards.add(new EatZuzhou());

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
        cards.add(new KingSay());//魔王律令
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
        cards.add(new LineDefender());
        cards.add(new LittleTe());
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
        cards.add(new BloodNo());
        cards.add(new ShadowOut());
        cards.add(new BloodSword());
        cards.add(new AngryForever());
        //cards.add(new FireAgain());
        cards.add(new ShadowRunNight());
        cards.add(new SoMuchSworld());
        //cards.add(new BigNotWork());
        cards.add(new ShadowBack());
        cards.add(new ShadowYangMei());
        cards.add(new ShadowSkill());
        cards.add(new ShadowCry());
        cards.add(new ShadowUpgrade());
        //cards.add(new ShadowBlueFire());
        cards.add(new ShadowDefend());
        cards.add(new ShadowTwo());
        cards.add(new ShadowWaterMusic());
        //cards.add(new ShadowChange());//变式费
        cards.add(new ShadowBlood());
        //cards.add(new ShadowBackWindy()); //回风
        cards.add(new ShadowNoShadow());
        cards.add(new ShadowCloudBreak2());
        //cards.add(new CloudBreakIn());
        //cards.add(new ShadowBreak());//断剑
        //cards.add(new Shadow15());//剑15
        cards.add(new ShadowSkyOpen());
        //cards.add(new NoName());
        cards.add(new SwordHeard());

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
        BaseMod.addRelicToCustomPool(new TenRelic(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new Crystal(),CardColorEnum.AMIYA);
        //BaseMod.addRelicToCustomPool(new BottedIce(),CardColorEnum.AMIYA);
        BaseMod.addRelic(new BottedIce(),RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new BreakBuilding(),CardColorEnum.AMIYA);
        BaseMod.addRelicToCustomPool(new UnEndStory(),CardColorEnum.AMIYA);
        BaseMod.addRelic(new Yill(),RelicType.SHARED);
        logger.debug("Amiyamod relic load finish.");
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



    private Settings.GameLanguage languageSupport()
    {
        switch (Settings.language) {
            case ZHS:
                return Settings.language;
            //case JPN:
            //    return Settings.language;
            default:
                return Settings.GameLanguage.ENG;
        }
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
            logger.info("lang == eng");
            card = CARD_STRING_EN;
            relic = RELIC_STRING_EN;
            power = POWER_STRING_EN;
            potion = POTION_STRING_EN;
            event = EVENT_PATH_EN;

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
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if(c instanceof OnCombatStartInterface){
                ((OnCombatStartInterface) c).OnCombatStartInterface();
            }
        }
    }

    static class Keywords { Keyword[] keywords;}
    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

}