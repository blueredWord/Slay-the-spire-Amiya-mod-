package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Yzuzhou.Yjianwang;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.HerSeePower;
import Amiyamod.power.LineBow;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Iterator;
//见她所见
//消耗 获得等于当前 丝线 层数的能量，本回合每消耗1点能量 燃己1。
public class HerSee extends CustomCard {
    private static final String NAME = "HerSee";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 1;//卡片费用

    private static final AbstractCard.CardType TYPE = CardType.SKILL;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.UNCOMMON;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人

    public HerSee() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new GainEnergyAction(4));
        //获得等同于丝线层数的能量
        //if (AbstractDungeon.player.hasPower(LineBow.POWER_ID)){
        //this.addToTop(new GainEnergyAction(AbstractDungeon.player.getPower(LineBow.POWER_ID).amount));
        //}
        /*
        另一种写法 要遍历 感觉很唐
        Iterator var2 = AbstractDungeon.player.powers.iterator();
        while (var2.hasNext()){
            AbstractPower power = (AbstractPower)var2.next();
            if (power.ID == LineBow.POWER_ID){
                this.addToTop(new GainEnergyAction(power.amount));
            }
        }
        */
        //获得状态：每花费1能量燃己2
        this.addToTop(new ApplyPowerAction(p, p, new HerSeePower(p, this.magicNumber),this.magicNumber));
    }
    public AbstractCard makeCopy() {return new HerSee();}
}
