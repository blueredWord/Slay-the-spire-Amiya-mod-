package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.DisToHandAction;
import Amiyamod.action.DrawToHandAction;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.KingSayPower;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class HelpMagic extends CustomCard implements OnLoseTempHpPower {
    //=================================================================================================================
    //@ 【护身咒文】 源石技艺 。 急性发作 。 NL 获得 !M! 点 格挡 。 NL 失去生命时将此卡拿到手中。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "HelpMagic";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public HelpMagic() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 8;
        this.baseBlock = this.block = 18;
        this.baseDraw = this.draw =  this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        this.misc = 1;
        this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(8);
            //this.exhaust = true;
            //this.isEthereal = false;
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.baseDraw += 1;
            //this.draw = this.baseDraw;
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.target = CardTarget.ENEMY;
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public int onLoseTempHp(DamageInfo damageInfo, int i) {
        int tem=(Integer) TempHPField.tempHp.get(AbstractDungeon.player);
        if ( i > 0 && tem >= i){
            this.tookDamage();
        }
        return i;
    }
    public void tookDamage() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> list = new ArrayList<>();
        //监测抽牌
        if (!p.drawPile.isEmpty()){
            for (AbstractCard c : p.drawPile.group){
                if (c.uuid == this.uuid){
                    this.addToBot(new DrawToHandAction(this));
                    //list.add(c);
                    break;
                }
            }
            /*
            if (!list.isEmpty()){
                for (AbstractCard c : list){
                    this.addToBot(new DrawToHandAction(this));
                }
            }

             */
        }

        //监测弃牌
        if (!p.discardPile.isEmpty()){
            for (AbstractCard c : p.discardPile.group){
                if (c.uuid == this.uuid){
                    this.addToBot(new DisToHandAction(this));
                    //list.add(c);
                    break;
                }
            }
            /*
            if (!list.isEmpty()){
                for (AbstractCard c : list) {

                }
            }
             */
        }

        list.clear();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Amiyamod.addY(this.misc);
        Amiyamod.HenJi(this.magicNumber,this,m);
        this.addToBot(new GainBlockAction(p,p,this.block));
    }
    public AbstractCard makeCopy() {return new HelpMagic();}
}
