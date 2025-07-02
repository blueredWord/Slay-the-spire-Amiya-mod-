package Amiyamod.cards;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.FindingAction;
import Amiyamod.cards.CiBeI.SoulDoor;
import Amiyamod.patches.CardColorEnum;
import Amiyamod.patches.YCardTagClassEnum;
import Amiyamod.power.FindingPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class Finding extends CustomCard {
    private static final String NAME = "Finding";//卡片名字
    public static final String ID = Amiyamod.makeID(NAME);//卡片ID

    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/" + NAME + ".png";//卡图

    private static final int COST = 1;//卡片费用
    private static final AbstractCard.CardType TYPE = CardType.POWER;//卡片类型
    private static final AbstractCard.CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;//卡片稀有度，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;//是否指向敌人
    //=================================================================================================================
    //@ 【阿纳萨之觉】  虚无 。失去所有 金币 。 NL 消耗 一张牌 ，若为 诅咒 将其从卡组中移除。否则将其永久 升级 。 NL (此牌使用 !M! 次后移除。)
    //=================================================================================================================
    public Finding() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        //源石卡牌tag
        this.misc = 1;
        this.magicNumber = this.baseMagicNumber = 3;
        this.isEthereal = true;
        //this.tags.add(YCardTagClassEnum.YCard);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            //this.magicNumber = this.baseMagicNumber = 3;
            this.upgradeBaseCost(0);
            //this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.player.loseGold(50);
        //this.addToBot(new FindingAction(this.misc));
        //if (!this.upgraded){
            this.upgradeMagicNumber(-1);
            this.applyPowers();
            this.addToBot(new ApplyPowerAction(p,p,new FindingPower(p,1)));
            for (AbstractCard caa: AbstractDungeon.player.masterDeck.group) {
                if (caa.uuid == this.uuid) {
                    caa.baseMagicNumber = caa.magicNumber = this.magicNumber;
                    this.upgradedMagicNumber = true;
                    if (caa.magicNumber == 0){
                        caa.untip();
                        caa.unhover();
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(caa, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(caa);
                    }else {
                        caa.applyPowers();
                    }
                    break;
                }
            }
        //}
    }

    public AbstractCard makeCopy() {
        return new Finding();
    }
}
