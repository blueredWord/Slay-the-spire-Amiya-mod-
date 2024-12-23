package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.PunchAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.OnCombatStartInterface;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Punch extends CustomCard implements OnCombatStartInterface {
    private static final String NAME = "Punch";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/Punch.png";//卡图
    private static final int COST = 2;//卡片费用
    private static final CardType TYPE = CardType.ATTACK;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.COMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//是否指向敌人

    public Punch() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 0;
        this.baseMagicNumber = this.magicNumber = 3;

        //this.cardsToPreview = new RedSky(true);
        //源石卡牌tag
        this.exhaust = true;
        //this.tags.add(YCardTagClassEnum.RedSky1);
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    //public boolean canUpgrade() {return true;}
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            //this.upgradeMagicNumber(5+this.timesUpgraded);
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeMagicNumber(-1);
            //++this.timesUpgraded;
            //this.upgraded = true;
            //this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }
    }

    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        int i = (p.currentHealth +  TempHPField.tempHp.get(p)) / this.magicNumber;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += i;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPlayer p = AbstractDungeon.player;
        int i = (p.currentHealth +  TempHPField.tempHp.get(p)) / this.magicNumber;
        int realBaseDamage = this.baseDamage;
        this.baseDamage += i;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = (p.currentHealth +  TempHPField.tempHp.get(p)) / this.magicNumber;
        this.damage += i;
        this.calculateCardDamage(m);
        this.addToBot(new PunchAction(this,m));
    }
    public AbstractCard makeCopy() {return new Punch();}

    @Override
    public void OnCombatStartInterface() {
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }
}
