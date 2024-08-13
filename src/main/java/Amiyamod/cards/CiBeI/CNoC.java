package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

//  有刺无刺
//  对所有敌人造成2次3（4）点伤害，本回合你每受到1次伤害，攻击次数+1。燃己1（2）
public class CNoC extends CustomCard {
    private static final String NAME = "CNoC";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 1;//卡片费用

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.Amiyathecolor;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;//是否指向敌人

    public CNoC() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 3;
        this.misc = 2;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 1;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }
    public void tookDamage() {
        this.misc += 1;
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(1); // 将该卡牌的伤害提高。
            this.upgradeMagicNumber(1);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0;i < this.misc;i++){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(
                            p,
                            this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE,true
                    )
            );
        }
        this.misc = 2;
        Amiyamod.BurnSelf(this.magicNumber);
    }
    public AbstractCard makeCopy() {return new CNoC();}
}
