package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.action.SEAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.HatePower;
import Amiyamod.power.RedSkyPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;
import java.util.Random;

public class Hate extends CustomCard {
    //=================================================================================================================
    //@ 【恨火蔓延】 持有 影霄 时才能打出。 NL 造成 !D! 点伤害 。 NL 保留 影霄1回合。 NL 蕴剑 !M! 。
    //=================================================================================================================
    private static final String NAME = "Hate";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//【是否指向敌人】

    public Hate() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 4;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.isEthereal = true;
        this.tags.add(YCardTagClassEnum.RedSky1);
        //源石卡牌tag
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeMagicNumber(1);
            this.upgradeDamage(2);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
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
            if (p.hasPower(RedSkyPower.POWER_ID)) {
                return  true ;
            }
            if (new Random().nextBoolean()){
                this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
            } else {
                this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
            }
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SEAction(NAME,true));

        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(
                                p,
                                damage,
                                this.damageTypeForTurn
                        )
                )
        );
        this.addToBot(new ApplyPowerAction(p,p,new HatePower(this.magicNumber)));
    }

    public AbstractCard makeCopy() {return new Hate();}
}
