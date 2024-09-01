package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.MindBubblePower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;

public class MindBubble extends CustomCard {
    //=================================================================================================================
    //@ 【情感泡泡】 不能被打出 。 NL 抽到此牌时获得!M!点丝线，本回合你不能打出攻击牌。 NL 虚无 。
    //=================================================================================================================
    private static final String NAME = "MindBubble";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = -2;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.NONE;//【是否指向敌人】

    public MindBubble() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 15;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 12;
        //this.heal = 15;
        //this.misc = 20;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        //this.exhaust = true;
        this.isEthereal = true;
        //this.selfRetain = true;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(6);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void triggerWhenDrawn() {
        AbstractPlayer p = AbstractDungeon.player;
        Amiyamod.LinePower(this.magicNumber);
        if(this.upgraded) {
            this.addToBot(new ApplyPowerAction(p, p, new MindBubblePower()));
        }else {
            this.addToBot(new ApplyPowerAction(p, p, new EntanglePower(p)));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}
    public AbstractCard makeCopy() {return new MindBubble();}
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return  false;
    }
}
