package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
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
public class CNoC extends CustomCard implements OnLoseTempHpPower {
    private static final String NAME = "CNoC";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 1;//卡片费用

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.ALL_ENEMY;//是否指向敌人

    public CNoC() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 3;
        //this.isMultiDamage = true;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(2); // 将该卡牌的伤害提高。
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for(int i = 0; i < this.magicNumber+this.misc; ++i) {
            this.addToBot(new AttackDamageRandomEnemyAction(this));
        }
        this.misc = 0;
        //Amiyamod.BurnSelf(1);
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }
    public AbstractCard makeCopy() {return new CNoC();}

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        int tem=(Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if ( i > 0 && tem >= i){
            upg();
        }
        return i;
    }
    public void tookDamage() {
        upg();
    }
    public void upg(){
        this.misc += 1;
        this.rawDescription = CARD_STRINGS.DESCRIPTION+CARD_STRINGS.EXTENDED_DESCRIPTION[0]+this.misc+CARD_STRINGS.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }
}
