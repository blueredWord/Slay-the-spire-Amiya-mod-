package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.relics.Yill;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YOpen extends CustomCard {
    //=================================================================================================================
    //@ 【结晶绽放】 急性发作 9。 NL 造成 !D! 点伤害。 NL 获得相当于你 感染进度 的 格挡 。
    //=================================================================================================================
    private static final String NAME = "YOpen";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public YOpen() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 13;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 2;
        //this.heal = 15;
        this.misc = 1 ;
        //this.exhaust = true;
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
            //this.exhaust = false;
            this.upgradeDamage(6);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, damage,this.damageTypeForTurn)
                )
        );

        if (p.hasRelic(Yill.ID) &&  p.getRelic(Yill.ID).counter >0 ){
            this.addToBot( new GainBlockAction(p,p,(p.getRelic(Yill.ID).counter)*this.magicNumber));
            //this.addToBot(new GainBlockAction(p,p,p.));
        }
       // Amiyamod.addY(this.misc);
        Amiyamod.HenJi(this.magicNumber,this,m);
    }
    public AbstractCard makeCopy() {return new YOpen();}
}
