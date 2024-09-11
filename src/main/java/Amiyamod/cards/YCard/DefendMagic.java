package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.MagicYuJinAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VaultPower;

public class DefendMagic extends CustomCard {
    //=================================================================================================================
    //@ 【防护巫咒】 源石技艺 。 NL 源石技艺 。 去除你的 脆弱 效果。 NL 获得 !B! 点格挡。
    //=================================================================================================================
    private static final String NAME = "DefendMagic";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public DefendMagic() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 9;
        this.baseBlock = this.block =9;
        //this.magicNumber = this.baseMagicNumber = 2;
        //this.heal = 15;
        //this.misc = 20;
        //this.exhaust = true;
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
            //this.upgradeBlock(6);
            //this.exhaust = false;
            this.upgradeBlock(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new RemoveSpecificPowerAction(p,p, VulnerablePower.POWER_ID));
        //this.addToTop(new RemoveSpecificPowerAction(p,p, FrailPower.POWER_ID));
        this.addToBot(new GainBlockAction(p,p,this.block));
        Amiyamod.addY(1);
    }
    public AbstractCard makeCopy() {return new DefendMagic();}
}
