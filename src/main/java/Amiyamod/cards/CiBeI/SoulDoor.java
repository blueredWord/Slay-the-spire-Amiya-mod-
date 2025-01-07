package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.action.KingSeeAction;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SoulDoor extends CustomCard {
    private static final String NAME = "SoulDoor";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = 1;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;//是否指向敌人
    //=================================================================================================================
    //@ 【幽影门扉】  对随机敌人造成 !D! 点伤害 。 NL 当此牌被 消耗 时，失去 !M! 点生命。 （由于闪烁衍生卡）
    //=================================================================================================================
    public SoulDoor() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.baseDamage = this.damage = 10;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.isEthereal = true;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.isEthereal = false;
            this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING));
    }
    public void triggerOnExhaust() {
        Amiyamod.BurnSelf(this.magicNumber);
    }
    public AbstractCard makeCopy() {
        return new SoulDoor();
    }
}
