package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MindBreak extends CustomCard {
    //=================================================================================================================
    //@ 【精神爆发】 急性发作9 手牌中存在 源石诅咒 时自动打出 随机造成D点伤害M次，结束你的回合。
    //=================================================================================================================
    private static final String NAME = "MindBreak";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 2;//【卡片费用】
    private static final CardType TYPE = CardType.ATTACK;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.UNCOMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.ALL;//【是否指向敌人】

    public MindBreak() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 4;
        this.magicNumber = this.baseMagicNumber = 6;
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
        //this.exhaust = true;
        //this.heal = 15;
        //this.misc = 20;
        //源石卡牌tag
        this.tags.add(YCardTagClassEnum.YCard);
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                AbstractDungeon.player.limbo.addToBottom(this);
                this.current_x = c.current_x;
                this.current_y = c.current_y;
                this.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                this.target_y = (float)Settings.HEIGHT / 2.0F;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(this, null,this.energyOnUse, true, true), true);
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        //造成多次伤害
        for(int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        //感染进度
        Amiyamod.addY(1);
        //Amiyamod.HenJi(9,this,m,new PressEndTurnButtonAction());
        this.addToBot(new PressEndTurnButtonAction());
        //结束回合
    }
    public AbstractCard makeCopy() {return new MindBreak();}
}
