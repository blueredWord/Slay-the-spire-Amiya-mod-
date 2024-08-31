package Amiyamod.cards.RedSky;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class Shadow15 extends CustomCard implements OnLoseTempHpPower {
    private static final String NAME = "Shadow15";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = 15;//卡片费用
    //private static final String DESCRIPTION = "造成 !D! 点伤害。";//卡片描述
    private static final CardType TYPE = CardType.ATTACK;//卡片类型
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardRarity RARITY = CardRarity.RARE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ENEMY;//是否指向敌人

    public Shadow15() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 15;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        this.heal = 15;
        this.magicNumber = this.baseMagicNumber = 1;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    //抽到时 燃己5
    public void triggerWhenDrawn() {
        Amiyamod.BurnSelf(5);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            this.selfRetain = true;
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //造成多次伤害
        for(int i = 0 ; i < 5 ; i++){
            this.addToBot(new DamageAction(m,new DamageInfo(p,this.damage,this.damageTypeForTurn)));
        }
        //入鞘：回血
        Amiyamod.Sword(false,new HealAction(p,p,this.heal));
    }
    public AbstractCard makeCopy() {return new Shadow15();}

    //失去生命、临时生命时-1费
    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int damageAmount) {
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "劍15 失去零時生命 觸發。"
        );
        int tem=(Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if ( damageAmount > 0 && tem >= damageAmount){
            this.updateCost(-1);
        }
        return damageAmount;
    }
    public void tookDamage() {
        this.updateCost(-1);
    }
}
