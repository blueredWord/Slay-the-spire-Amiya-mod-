package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.DrBloodAction;
import Amiyamod.action.cards.ExAnyWhereCardAction;
import Amiyamod.cards.Yzuzhou.*;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.EatZuzhouPower;
import Amiyamod.power.MemoryPower;
import Amiyamod.power.YSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;

import java.util.Iterator;
import java.util.Objects;

public class EatZuzhou extends CustomCard {
    //=================================================================================================================
    //@ 【螯合诅咒】
    //=================================================================================================================
    private static final String NAME = "EatZuzhou";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.RARE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public EatZuzhou() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        this.misc = 1;
        this.isInnate = true;
        this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            for (AbstractCard c : p.masterDeck.group) {
                if (c.hasTag(YCardTagClassEnum.YZuZhou)) {
                    return  true ;
                }
            }
            this.cantUseMessage = CARD_STRINGS.UPGRADE_DESCRIPTION;
            return false;
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Amiyamod.addY(1);
        //Amiyamod.HenJi(1,this,m);
        for (int i = 0;i<this.magicNumber;i++){
            CardGroup G = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            for (AbstractCard card : p.masterDeck.group){
                if (card.hasTag(YCardTagClassEnum.YZuZhou) && !(card instanceof FirstSayA) ){
                    boolean add = true;
                    if (!G.isEmpty()){
                        for (AbstractCard c : G.group){

                            if (Objects.equals(c.cardID, this.cardID + card.cardID)){

                                add = false;
                                break;
                            }
                        }
                    }
                    if (add){
                        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                "螯合诅咒：添加 {} 。",card
                        );
                        String ID = this.cardID+card.cardID;
                        String Des;
                        if (card instanceof Yjianwang){
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
                        } else if (card instanceof Ydead) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
                        } else if (card instanceof Ytiruo) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[2];
                        } else if (card instanceof Yjiejin) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[3];
                        } else if (card instanceof Yangry) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[4];
                        }  else if (card instanceof Ysex) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[5];
                        }  else if (card instanceof Ysnake) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[6];
                        }  else if (card instanceof Ychengyin) {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[7];
                        }  else {
                            Des = CARD_STRINGS.EXTENDED_DESCRIPTION[8];
                        }

                        String Na = CARD_STRINGS.NAME + ":"+ card.name;
                        int COST = -2;
                        AbstractPower pow = new EatZuzhouPower(card,m);

                        CustomCard CC = new CustomCard(ID,Na,IMG_PATH, COST,Des, CardType.SKILL, p.getCardColor(), AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.NONE) {
                            @Override
                            public void upgrade() {}
                            @Override
                            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}
                            @Override
                            public void onChoseThisOption() {
                                this.addToBot(new ExAnyWhereCardAction(card.uuid));
                                this.addToBot(new ApplyPowerAction(m,m,pow));
                            }
                            @Override
                            public boolean canUse(AbstractPlayer p, AbstractMonster m) {
                                return false;
                            }
                        };
                        CC.purgeOnUse = true;
                        G.addToBottom(CC);
                    }
                }
            }
            if (!G.isEmpty()){
                this.addToBot(new ChooseOneAction(G.group));
            }
        }
    }
    public AbstractCard makeCopy() {return new EatZuzhou();}
}
