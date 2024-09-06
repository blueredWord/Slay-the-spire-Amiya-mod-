package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;

import Amiyamod.action.cards.ShadowNoShadowAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.power.CardBackPower;
import Amiyamod.power.RedSkyPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import org.apache.logging.log4j.LogManager;

public class ShadowNoShadow extends CustomCard {
    private static final String NAME = "ShadowNoShadow";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 3;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.ATTACK;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.RARE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ALL;//是否指向敌人

    public ShadowNoShadow() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 3;
        this.baseMagicNumber = this.magicNumber = 1;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(1); // 将该卡牌的伤害提高3点。
            this.selfRetain = true;
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i<10 ; i++){
            AbstractDungeon.actionManager.addToBottom(
                    new ShadowNoShadowAction(this)
            );
        }

        Amiyamod.Sword(false,new ApplyPowerAction(p,p,new IntangiblePlayerPower(p,this.magicNumber)));
    }
    public AbstractCard makeCopy() {return new ShadowNoShadow();}
}
