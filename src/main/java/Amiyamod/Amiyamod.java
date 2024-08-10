package Amiyamod;

import Amiyamod.cards.AmiyaStrike;
import Amiyamod.cards.Yzuzhou.Ychengyin;
import Amiyamod.cards.Yzuzhou.Ytiruo;
import Amiyamod.character.Amiya;
import Amiyamod.patches.LibraryTypeEnum;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.AbstractCardEnum;
import basemod.interfaces.EditKeywordsSubscriber;
import Amiyamod.patches.AmiyaClassEnum;
import Amiyamod.relics.TheTen;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.sun.org.apache.xpath.internal.compiler.Keywords;
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
        PostInitializeSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        AddAudioSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,
        PotionGetSubscriber,
        EditCharactersSubscriber{
    public static final Logger logger = LogManager.getLogger(Amiyamod.class.getSimpleName());

    public static String MOD_ID = "AmiyaMod";
    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }



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
        //CaseMod.subscribe(this);
        BaseMod.addColor(CardColorEnum.Amiyathecolor,
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

    public static void BurnSelf(int number) {
        AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, number));
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
    public void receivePostInitialize() {
            /*
        Texture badgeTexture = new Texture("img/badge.png");
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        ModLabeledToggleButton addonRelicSwitch = new ModLabeledToggleButton(CardCrawlGame.languagePack.getUIString(makeID("RelicFilter")).TEXT[0],400.0f, 720.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,addonRelic, settingsPanel,
                (label) -> {}, (button) -> {addonRelic = button.enabled;saveConfig();});

        settingsPanel.addUIElement(addonRelicSwitch);



        BaseMod.addPotion(ReserveRunePotion.class,DeathKnight_Color,RuneShadow_Color,Transparent_Color,WarlordEmblem.makeID("ReserveRunePotion"),AbstractPlayerEnum.DeathKnight);
        BaseMod.addPotion(RuneExpandPotion.class,DeathKnight_Color,RuneShadow_Color,Transparent_Color,WarlordEmblem.makeID("RuneExpandPotion"),AbstractPlayerEnum.DeathKnight);
        BaseMod.addPotion(RealmPotion.class,BloodRealm_Color,EvilRealm_Color,IceRealm_Color,WarlordEmblem.makeID("RealmPotion"),AbstractPlayerEnum.DeathKnight);
        BaseMod.addPotion(DKPoisonPotion.class,BaseMod.getPotionLiquidColor("Poison Potion"),BaseMod.getPotionHybridColor("Poison Potion"),BaseMod.getPotionSpotsColor("Poison Potion"),WarlordEmblem.makeID("DKPoisonPotion"),AbstractPlayerEnum.DeathKnight);
        */

    }


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
        //BaseMod.addDynamicVariable(new RealmMagicNumber());
        //BaseMod.addDynamicVariable(new SecondRealmMagicNumber());
        //BaseMod.addDynamicVariable(new SecondMagicNumber());

        logger.debug("Amiyamod Cards started.");
        List<CustomCard> cards = new ArrayList<>();
//加入卡牌
        cards.add(new AmiyaStrike());

        for (CustomCard card : cards) {
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
        if(addonRelic){

           BaseMod.addRelic(new TheTen(), RelicType.SHARED);

        }
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
    class Keywords { Keyword[] keywords;}
    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

}