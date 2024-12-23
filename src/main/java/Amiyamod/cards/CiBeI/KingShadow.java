package Amiyamod.cards.CiBeI;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KingShadow extends CustomCard {
    //=================================================================================================================
    //@ 【尊主之影】 保留 。 NL 出鞘 : 造成 !D! 点伤害。 NL 每当此卡被 保留 ，耗能减少 [E] 。 NL 消耗 。
    //=================================================================================================================
    private static final String NAME = "KingShadow";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 3;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;//【是否指向敌人】

    public KingShadow() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage =  9;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.baseDraw = this.draw = 2;
        //this.misc = 0;
        this.exhaust = true;
        //this.isEthereal = true;
        this.selfRetain = true;
        this.isMultiDamage = true;
        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.YCard);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.exhaust = true;
            //this.isEthereal = false;
            this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void onRetained() {
        this.modifyCostForCombat(-this.magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
                new DamageAllEnemiesAction(p,this.multiDamage,this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY)
        );

        int i = 0;
        if (!this.isInAutoplay && !p.hand.isEmpty()){
            for (AbstractCard c : p.hand.group){
                if (c instanceof KingShadow && c != this ){
                    i++;
                    c.setCostForTurn(0);
                    this.addToBot(new AutoplayCardAction(c,p.hand));
                }
            }
        }


    }

    public AbstractCard makeCopy() {return new KingShadow();}
}
