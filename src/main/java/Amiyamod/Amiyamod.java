package Amiyamod;

import Amiyamod.cards.AmiyaDefend;
import Amiyamod.cards.AmiyaMagic;
import Amiyamod.cards.AmiyaStrike;
import Amiyamod.cards.RedSky.*;
import Amiyamod.cards.CiBeI.*;
import Amiyamod.cards.Yzuzhou.*;
import Amiyamod.character.Amiya;
import Amiyamod.patches.AmiyaClassEnum;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.BigNotWorkPower;
import Amiyamod.power.RedSkyPower;
import Amiyamod.power.SnakePower;
import Amiyamod.relics.CYrelic;
import Amiyamod.relics.Yill;
import basemod.abstracts.CustomSavableRaw;
import basemod.interfaces.EditKeywordsSubscriber;
import Amiyamod.relics.TheTen;
import basemod.helpers.RelicType;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


@SpireInitializer
public class Amiyamod implements
        PostInitializeSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        AddAudioSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,
        PotionGetSubscriber,
        EditCharactersSubscriber,OnPlayerTurnStartSubscriber{
    public static final Logger logger = LogManager.getLogger(Amiyamod.class.getSimpleName());

    public static String MOD_ID = "AmiyaMod";
    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }


    public static ArrayList<CustomCard> Yzuzhou = new ArrayList();

    public static final String MODNAME = "Amiya Mod";
    public static final String AUTHOR = "A";
    public static final String DESCRIPTION = "Amiya Mod.";
    public static String Amiya_bgImg = "img/character/Amiya/AmiyaBG.png"; //选人界面？

    public static boolean addonRelic = true;    //是否读取mod遗物
    /*
    public static boolean ringRelic = true;
    public static boolean mantleRelic = true;
    public static boolean RuneCountDisplay = true;
    public static boolean baseGameRelic2DK = true;


    public static final Color BloodRealm_Color = new Color(0.835f,0.25f,0.187f,1.0f);
    public static final Color IceRealm_Color = new Color(0.125F,0.219F,0.633F,1.0F);
    public static final Color EvilRealm_Color = new Color(0.043F,0.875F,0.195F,1.0F);
    public static final Color RuneShadow_Color = new Color(0.0F,0.3F,0.35F,0.8F);
    public static final Color Transparent_Color = new Color(0.0F,0.0F,0.0F,0.0F);
     */


    public static Properties AmiyaModDefaults = new Properties();
    public int value = 0;
    public static final Color Amiya_Color = new Color(0.171F,0.722F,0.722F,1.0F);
    public Amiyamod(){
        logger.debug("Constructor started.");
        BaseMod.subscribe(this);
        //BaseMod.addSaveField(makeID(MOD_ID+":save_field"), );
        //CaseMod.subscribe(this);
        BaseMod.addColor(CardColorEnum.Amiya,
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

        loadConfig();
        logger.debug("Constructor finished.");
    }

    public static void initialize() {
        logger.info("========================= 开始初始化 =========================");
        new Amiyamod();
        logger.info("========================= 初始化完成 =========================");
    }

    //  回合开始接口
    @Override
    public void receiveOnPlayerTurnStart() {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：成功触发回合开始 开始判定丝线减半"
        );
        int var = TempHPField.tempHp.get(AbstractDungeon.player);
        TempHPField.tempHp.set(AbstractDungeon.player,var/2);
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDead && !mo.isDying) {
                var = TempHPField.tempHp.get(mo);
                TempHPField.tempHp.set(mo,var/2);
            }
        }
    }

    //  调动遗物记录源石感染的接口
    public static void addY(int n) {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasRelic(Yill.ID)){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：增加感染进度：检测到没有源石病，添加遗物ing"
            );
            //AbstractDungeon.getCurrRoom().spawnBlightAndObtain(300,300,new YuanIll(n));
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(300, 300, new Yill(n));
        }else {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：增加感染进度" + n
            );
            CYrelic a =  (CYrelic)p.getRelic(Yill.ID);
            a.addC(n);
        }
    }
    //急性感染接口
    public static void HenJi(int number, AbstractCard c, AbstractMonster m){
        if(!c.purgeOnUse) {
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：触发来自"+c.name+":"+c.uuid+"的急性感染。"
            );
            AbstractPlayer p = AbstractDungeon.player;
            ArrayList<AbstractCard> G = new ArrayList<AbstractCard>();
            for (AbstractCard card : p.hand.group) {
                if (card.hasTag(YCardTagClassEnum.YZuZhou)) {
                    G.add(card);
                }
            }

            for (int i = 0; !G.isEmpty() && i != number; i++) {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：触发来急性感染,丢弃第"+(i+1)+"张牌"
                );
                Collections.shuffle(G);
                AbstractCard card = G.get(0);
                p.hand.moveToDiscardPile(card);
                G.remove(0);
                AbstractCard tmp = c.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                /*
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;

                 */

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
        if (p.isPlayer){
            for (AbstractCard c:AbstractDungeon.player.hand.group){
                //如果是玩家要获得丝线，检查手里有没有活性化诅咒
                if (c instanceof Ysex){
                    sex = true;
                    break;
                }
            }
            if (sex){//有活性化诅咒的话改为获得格挡，否则直接获得丝线
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：检测到活性化体制，获得丝线改为获得格挡。"
                );
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p,number));
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
        Collections.shuffle(Yzuzhou);
        AbstractCard c = Yzuzhou.get(0).makeCopy();
        if (!isTemp){
            Yzuzhou.remove(0);
        }
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：获取下一张随机源石诅咒："+c.name
        );
        return c;
    }
    //  燃己的接口
    public static void BurnSelf(int number) {
        AbstractPlayer p =AbstractDungeon.player;
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：准备进行"+number+"燃己"
        );
        for (AbstractCard c: AbstractDungeon.player.hand.group){
            if(c instanceof Ysnake){
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "模组核心：认知偏差 要被烫出个很唐的效果了"
                );
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SnakePower(p,1)));
            }
        }
        for(int i=0;i < number;i++){
            AbstractDungeon.actionManager.addToTop(
                    new LoseHPAction(p, p, 1)
            );
        }
    }
    //  获得赤霄的接口
    public static void getRedSky(int number) {
        int n = number;
        AbstractPlayer p =AbstractDungeon.player;
        if(p.hasPower(BigNotWorkPower.POWER_ID)){
            if (p.getPower(BigNotWorkPower.POWER_ID).amount>BigNotWorkPower.cardsDoubledThisTurn){
                BigNotWorkPower.cardsDoubledThisTurn++;
                n++;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new RedSky(n)));
    }
    //  抽到诅咒卡时的通用处理
    public static void WhenYcardDrawn() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof RollFood) {
                p.drawPile.addToHand(c);
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c instanceof RollFood) {
                p.discardPile.addToHand(c);
                //AbstractDungeon.actionManager.addToTop(new DiscardToHandAction(c));
            }
        }
        p.hand.refreshHandLayout();
    }

    //出入鞘相关判定接口
    public static void Sword(boolean isOut,ArrayList<AbstractGameAction> l) {
        AbstractPlayer p = AbstractDungeon.player;
        if(isOut){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：进入出鞘流程"
            );
            // if isOut  出鞘相关处理
            if (p.hasPower(RedSkyPower.POWER_ID)) {
                //如果已经处于赤霄
                if (l != null && !l.isEmpty()){
                    for (AbstractGameAction a :l){
                        //逐个触发列表中的特殊效果
                        AbstractDungeon.actionManager.addToBottom(a);
                    }
                }
            } else {
                //如果不处于赤霄状态，进入赤霄状态
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RedSkyPower()));
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
                        //逐个触发列表中的特殊效果
                        AbstractDungeon.actionManager.addToBottom(a);
                    }
                }
            } else {
                //否则无事发生
            }
        }
    }
    //出入鞘相关判定接口的重构，只有一个行动
    public static void Sword(boolean isOut,AbstractGameAction act) {
        ArrayList<AbstractGameAction> l = new ArrayList<AbstractGameAction>();
        l.add(act);
        Sword(isOut,l);
    }

    public static void loadConfig() {
        logger.debug("===徽章读取设置======");
        try {
            SpireConfig config = new SpireConfig("AmiyaMod", "AmiyaModSaveData", AmiyaModDefaults);
            config.load();
            addonRelic = config.getBool("addonRelic");
        } catch (Exception e) {
            e.printStackTrace();
            clearConfig();
        }
        logger.debug("===徽章读取设置完成======");
    }

    public static void saveConfig() {
        logger.debug("===徽章存储设置======");
        try {
            SpireConfig config = new SpireConfig("AmiyaMod", "AmiyaModSaveData", AmiyaModDefaults);
            config.setBool("addonRelic", addonRelic);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("===徽章存储设置完成======");
    }

    public static void clearConfig() {
        saveConfig();
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


    @Override
    public void receiveEditCharacters() {
        logger.info("========================= Amiya Start Loading =========================");
        BaseMod.addCharacter(new Amiya("Amiya"),"img/character/Amiya/AmiyaButton.png",Amiya_bgImg,AmiyaClassEnum.Amiya);
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
        //源石诅咒
        cards.add(new Ytiruo());
        cards.add(new Ymust());
        cards.add(new Ysnake());
        cards.add(new Yjianwang());
        cards.add(new Ytiruo());
        cards.add(new Ysex());
        cards.add(new Yangry());
        cards.add(new Ychengyin());
        cards.add(new Ydead());
        //慈悲愿景
        cards.add(new BadZhufu());
        cards.add(new BeautifulLife());
        cards.add(new BForB());
        cards.add(new BloodPotion());
        cards.add(new BreakRing());
        cards.add(new CNoC());
        cards.add(new Echo());
        cards.add(new HelBox());
        cards.add(new HELP());
        cards.add(new HerDo());
        cards.add(new HerSee());
        cards.add(new LifeMade());
        cards.add(new LineBody());
        cards.add(new LineDefender());
        cards.add(new LittleTe());
        cards.add(new Mercy());
        cards.add(new NoFeng());
        cards.add(new Open());
        cards.add(new RollFood());
        cards.add(new SadMind());
        cards.add(new SoulDefend());
        cards.add(new CRing());
        //赤霄
        cards.add(new RedSky(-2));
        cards.add(new ShadowOut());
        cards.add(new AngryForever());
        cards.add(new FireAgain());
        cards.add(new SoMuchSworld());
        cards.add(new BigNotWork());
        cards.add(new ShadowBack());
        cards.add(new ShadowYangMei());

        for (CustomCard card : cards) {
            logger.debug(card.cardID+" is loading");
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }

        logger.debug("Amiyamod Cards finished.");
    }

    @Override
    public void receivePotionGet(AbstractPotion abstractPotion) {
        //    BaseMod.addPotion(ReserveRunePotion.class,DeathKnight_Color,DeathKnight_Color,DeathKnight_Color,WarlordEmblem.makeID("ReserveRunePotion"),AbstractPlayerEnum.DeathKnight);
    }

//载入遗物
    @Override
    public void receiveEditRelics() {
        logger.debug("Amiyamod relic load start.");
        BaseMod.addRelicToCustomPool(new TheTen(),CardColorEnum.Amiya);
        BaseMod.addRelic(new Yill(),RelicType.SHARED);
        logger.debug("Amiyamod relic load finish.");
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

    // 保存
    public Integer onSave() {
        return value;
    }

    // 读取
    public void onLoad(Integer save) {
        this.value = save;
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
            logger.info("阿米娅MOD读取关键词文件 : " + key.NAMES[0]);
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
        }
        logger.info("阿米娅MOD关键词读取完毕");
    }

    // 为模组核心添加新的字段
    public static boolean saveField;

    @Override
    public void receivePostInitialize() {
        try {
            // 设置默认值
            Properties defaults = new Properties();
            defaults.setProperty("save_field", "false");
            // defaults.setProperty("save_field_2", "false");

            // 第一个字符串输入你的modid
            SpireConfig config = new SpireConfig(MOD_ID, MOD_ID+":save_field", defaults);
            // 如果之前有数据，则读取本地保存的数据，没有就使用上面设置的默认数据
            saveField = config.getBool("save_field");
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }


    class Keywords { Keyword[] keywords;}
    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

}