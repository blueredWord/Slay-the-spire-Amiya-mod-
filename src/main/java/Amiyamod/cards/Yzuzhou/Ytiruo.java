package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.patches.YZCardInterface;
import Amiyamod.power.FirstSayPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;
//身虚体弱
//不可被打出 当你打出耗能至少为2的卡牌后，获得 !M! 层 虚弱 。
public class Ytiruo extends CustomCard implements YZCardInterface {
    private static final String NAME = "Ytiruo";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图
    private static final int COST = -2;//卡片费用 -2为诅咒
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.CURSE;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColor.CURSE;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.CURSE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;//无法选择
    public Ytiruo() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.misc = 2;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(YCardTagClassEnum.YZuZhou);
    }

    public void triggerWhenDrawn() {
        //源石诅咒被抽到时共通效果
        Amiyamod.WhenYcardDrawn();
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FirstSayPower.POWER_ID)){
            return true;
        }
        return super.canUse(p,m);
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        //如果耗能大于等于misc的卡
        AbstractPlayer p = AbstractDungeon.player;
        if (c.costForTurn >= this.misc || ((c.cost == -1)&&(!c.freeToPlayOnce)&&(EnergyPanel.totalCount>=this.misc))){
            //施加虚弱
            this.addToBot(new ApplyPowerAction(p,p,new WeakPower(p,this.magicNumber,false)));
        }
    }


    /*
    public void triggerOnEndOfPlayerTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        Iterator var2 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        //遍历本回合打出过的卡
        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            //如果打出过源石卡
            if (c.hasTag(YCardTagClassEnum.YCard)) {
                //向玩家施加易伤
                this.addToTop(new ApplyPowerAction(p, p, new VulnerablePower(p, this.magicNumber, false), this.magicNumber));
                /* 暂时封印深度感染的效果
                if(满足 && a){
                    Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                    while(var3.hasNext()) {
                        AbstractMonster mo = (AbstractMonster)var3.next();
                        this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                        this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }

                break;
            }
        }
    }
    */



    public void use(AbstractPlayer p, AbstractMonster m) {}
    public void upgrade() {}
    public AbstractCard makeCopy() {return new Ytiruo();}

    @Override
    public void YZupgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
            this.upgradeMagicNumber(-1);
            //this.upgradeBaseCost(0);
            // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
