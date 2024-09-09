package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTemCardAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.LibraryTypeEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MagicBook extends CustomCard {
    //=================================================================================================================
    //@ 【亘古的脉动】 失去等同于你手牌数量的生命。 NL 抽 !M! 张牌。 NL 虚无 。
    //=================================================================================================================
    private static final String NAME = "MagicBook";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public MagicBook() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 2;
        //this.heal = 15;
        //this.misc = 20;

        this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.upgradeDamage(4);
            this.upgradeMagicNumber(2);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> GG = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getCardList(LibraryTypeEnum.AMIYA)) {
            if (!card.hasTag(AbstractCard.CardTags.HEALING)&& card.hasTag(YCardTagClassEnum.YCard)) {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "巫术典籍：gg添加卡片{}", card
                );
                GG.add(card);
            }
        }
        boolean dupe = false;

        CardGroup chose = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int i = 0;
        while(chose.size() < this.magicNumber) {
            i++;
            if (i==1000){break;}
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "巫术典籍：尝试添加卡片"
            );
            Collections.shuffle(GG);
            AbstractCard card = GG.get(0);
            GG.remove(0);
            for (AbstractCard c : chose.group) {
                if (c.cardID.equals(card.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe) {
                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                        "巫术典籍：增加备选{}", card
                );
                chose.addToBottom(card.makeCopy());
            }
        }
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "巫术典籍：即将传递{}", chose
        );
        this.addToBot(new ChoseTemCardAction(chose));
        //this.addToBot(new DrawCardAction(this.magicNumber));
    }
    public AbstractCard makeCopy() {return new MagicBook();}
}
