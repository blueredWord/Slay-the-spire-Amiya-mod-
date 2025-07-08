package Amiyamod.cards.Yzuzhou;

import Amiyamod.Amiyamod;
import Amiyamod.action.UpRedAction;
import Amiyamod.cards.RedSky.SwordHeard;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.BallAPower;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.TorchHead;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BallCard extends CustomCard {
    //=================================================================================================================
    //@ 【仅剩的创意】 虚无 。 NL 给予阿米娅　!M! 层 仅剩的创意 。 NL 此牌被 消耗 时 打出，同时召唤特殊敌人。
    //=================================================================================================================
    private static final String NAME = "BallCard";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColor.COLORLESS;//卡牌颜色
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/BlackKing.png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.POWER;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.SPECIAL;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】

    public BallCard() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 5;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        //this.heal = 15;
        //this.misc = 1;
        //this.exhaust = true;
        this.isEthereal = true;
        //this.selfRetain = true;

        //源石卡牌tag
        //this.tags.add(YCardTagClassEnum.RedSky1);
        //this.tags.add(CardTags.STARTER_STRIKE);
        //this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBlock(6);
            //this.exhaust = false;
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(1);
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.isEthereal = false;
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    public void triggerOnExhaust() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo instanceof SlimeBoss){
                this.addToBot(new ApplyPowerAction(mo,AbstractDungeon.player,new BallAPower(mo,this.magicNumber)));
                //int key;
                //HashMap<Integer, AbstractMonster> enemySlots = new HashMap();
                //Iterator var3 = enemySlots.entrySet().iterator();
                //Map.Entry<Integer, AbstractMonster> m = (Map.Entry)var3.next();
                TorchHead newMonster;
                newMonster = new TorchHead(mo.hb_x + -185.0F , MathUtils.random(-5.0F, 25.0F));
                //key = (Integer)m.getKey();
                //enemySlots.put(key, newMonster);
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(newMonster, true));
                break;
            }
        }

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            //if (mo instanceof SlimeBoss){
                this.addToBot(new DiscardAction(p,p,this.magicNumber,false));
                this.addToBot(new ApplyPowerAction(mo,p,new BallAPower(mo,this.magicNumber)));
                break;
           //}
        }
    }
    public AbstractCard makeCopy() {return new BallCard();}
}
