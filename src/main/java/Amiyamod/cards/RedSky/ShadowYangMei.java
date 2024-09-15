package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ShadowYangMeiAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.CardBackPower;
import Amiyamod.power.RedSkyPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ShadowYangMei extends CustomCard {
    private static final String NAME = "ShadowYangMei";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    //private static final int COST = 1;//卡片费用
    private static final int COST = 1;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.SKILL;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public ShadowYangMei() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.UPGRADE_DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 6;
        this.baseBlock = this.block = 7;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.tags.add(YCardTagClassEnum.RedSky1);
        this.magicNumber = this.baseMagicNumber = 1;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
            this.upgradeBlock(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p,p,this.block)
        );
        Amiyamod.Sword(true,new ShadowYangMeiAction());
        /*
        int i = this.magicNumber;
        if (p.hasPower(RedSkyPower.POWER_ID)){ i = 99;}
        Amiyamod.Sword(true,new ArrayList<>());
        AbstractDungeon.actionManager.addToBottom(
                new ShadowYangMeiAction(i)
        );

         */
    }
    public AbstractCard makeCopy() {return new ShadowYangMei();}
}
