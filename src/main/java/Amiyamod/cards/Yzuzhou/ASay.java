package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.cards.YCard.FirstSay;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.LibraryTypeEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.FirstSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Collections;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER;

public class ASay extends CustomCard {
    //=================================================================================================================
    //@ 【本源呢喃】 源石诅咒 。 NL 随机施展一张 源石技艺 。 NL 虚无 。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "ASay";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.CURSE;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.CURSE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//【是否指向敌人】

    public ASay() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        //this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        //this.misc = 20;
        this.isEthereal = true;
        this.exhaust = true;
        //this.selfRetain = true;

        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YZuZhou);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.isInnate = true;
            this.upgradeBaseCost(0);
            //this.target = CardTarget.ALL_ENEMY;
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup GG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED) ;
        for (AbstractCard card : CardLibrary.getCardList(LibraryTypeEnum.AMIYA)) {
            if (!card.hasTag(AbstractCard.CardTags.HEALING)&& card.hasTag(YCardTagClassEnum.YCard)) {

                GG.addToBottom(card);
            }
        }
        AbstractCard card = GG.getRandomCard(true).makeCopy();

        AbstractDungeon.player.limbo.addToBottom(card);
        card.current_x = this.current_x + ((float) Settings.WIDTH / 16.0F);
        card.current_y = this.current_y + ((float) Settings.HEIGHT / 16.0F);
        card.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        card.target_y = (float) Settings.HEIGHT / 2.0F;

        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        if (mo != null) {
            card.calculateCardDamage(mo);
        }
        card.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, mo, card.energyOnUse, true, true), true);
    }
    public AbstractCard makeCopy() {return new ASay();}
}
