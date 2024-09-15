package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ShadowBackAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.CardBackPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

//影霄*溯洄
//丢弃手牌中所有的非攻击牌，获得与它们耗能相等的能量，并令手牌中的攻击牌本回合耗能+1。
public class ShadowBack extends CustomCard {
    private static final String NAME = "ShadowBack";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 0;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.SKILL;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//是否指向敌人


    public ShadowBack() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 4;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.baseBlock = this.block = 5;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.RedSky1);
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
            //this.upgradeBlock(5);
            //this.upgradeBaseCost(0);
            this.upgradeMagicNumber(1);
            //this.exhaust = false;
            //this.selfRetain = true;
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //this.addToBot(new GainBlockAction(p,p,this.block));
        this.addToBot(new GainEnergyAction(1));
        this.addToBot(new ShadowBackAction(this));
        ArrayList<AbstractGameAction> list = new ArrayList<>();
        for(int i = 0; i< this.magicNumber;i++){
            list.add(new UpgradeRandomCardAction());
        }
        Amiyamod.Sword(true,list);
        //Amiyamod.Sword(true,new GainEnergyAction(this.magicNumber));
    }
    public AbstractCard makeCopy() {return new ShadowBack();}
}
