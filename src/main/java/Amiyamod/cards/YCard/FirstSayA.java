package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.patches.YZCardInterface;
import Amiyamod.power.KingSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

public class FirstSayA extends CustomCard{
    //=================================================================================================================
    //@ 【源石结晶】
    //=================================================================================================================
    private static final String NAME = "FirstSayA";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private int Y = 0;
    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.CURSE;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.CURSE;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】
    public FirstSayA() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
        this.selfRetain = true;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YZuZhou);
    }


    public FirstSayA(int D,int B,int M,int H,int Draw,int mic,int cost,int y) {
        super(ID, CARD_STRINGS.NAME, IMG_PATH,COST, CARD_STRINGS.UPGRADE_DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.Y = y;
        this.exhaust = true;
        this.selfRetain = true;
        double level = 1;

        if (cost >=15){
            this.upgradeBaseCost(0);
        }

        if(cost < 5) {
        } else if (cost < 10) {
            level = 1.25;
        } else if (cost < 20) {
            level = 1.5;
        } else if (cost < 30) {
            level = 1.75;
        } else {
            level = 2;
        }

        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "源石结晶生成: cost:{},level:{}",cost,level
        );

        this.damage = this.baseDamage =(int)(D*level);
        this.baseBlock = this.block = (int)(B*level);
        this.magicNumber = this.baseMagicNumber = (int)((M+mic)*level);
        this.baseDraw = this.draw = (int)(Draw*level);
        this.baseHeal = this.heal = (int)(H*level);
        this.misc = mic;

        if (this.damage > 0){
            this.target = CardTarget.ENEMY;
            this.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[2];
        }
        if (this.block > 0){
            this.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[3];
        }
        if (this.magicNumber > 0){
            this.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[4];
        }
        if (this.draw > 0){
            this.rawDescription = this.rawDescription+CARD_STRINGS.EXTENDED_DESCRIPTION[5]+this.draw+CARD_STRINGS.EXTENDED_DESCRIPTION[6];
        }
        if (this.heal > 0){
            this.tags.add(CardTags.HEALING);
            this.rawDescription = this.rawDescription+CARD_STRINGS.EXTENDED_DESCRIPTION[7]+this.heal+CARD_STRINGS.EXTENDED_DESCRIPTION[8];
        }
        this.rawDescription += CARD_STRINGS.EXTENDED_DESCRIPTION[9];
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YZuZhou);
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.damage > 0){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage,this.damageTypeForTurn)));
        }
        if (this.block > 0){
            this.addToBot(new GainBlockAction(p,p,this.block));
        }
        if (this.magicNumber > 0){
            Amiyamod.LinePower(this.magicNumber);
        }
        if (this.draw > 0){
            this.addToBot(new DrawCardAction(this.draw));
        }
        if (this.heal > 0){
            this.addToBot(new HealAction(p,p,this.heal));
        }

        /*
        if (this.misc > 0){
            Amiyamod.HenJi(this.misc,this,m);
        }

         */
    }

    public AbstractCard makeCopy() {return new FirstSayA(this.baseDamage,this.baseBlock,this.baseMagicNumber,this.baseHeal,this.baseDraw,this.misc,this.cost,this.Y);}

}
