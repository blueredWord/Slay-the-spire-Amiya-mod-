package Amiyamod.power;

import Amiyamod.Amiyamod;

import Amiyamod.action.cards.ChoseTempToHandAction;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class MemoryPower extends AbstractPower {
    public static final String NAME = "MemoryPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private final AbstractCard.CardColor C ;
    boolean N = false;
    boolean U = false;
    boolean R = false;
    boolean UP ;
    private final String T;
    public MemoryPower(AbstractCard.CardColor color,int i,boolean up,String tit) {
        this.name = up? (tit+powerStrings.NAME): (tit+DESCRIPTIONS[0]);
        this.T = tit;
        this.ID = POWER_ID+color.name()+up;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = i;
        this.type = PowerType.BUFF;
        // 添加图标
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);

        this.UP = up;
        this.C = color;
        for (AbstractCard card : CardLibrary.getAllCards()){
            if (up){
                if (card.rarity != AbstractCard.CardRarity.COMMON && (card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.ATTACK) && card.color == this.C && !card.hasTag(AbstractCard.CardTags.HEALING)){
                    this.G.addToBottom(card);
                    if (card.rarity == AbstractCard.CardRarity.UNCOMMON){this.U = true;}
                    if (card.rarity == AbstractCard.CardRarity.RARE){this.R = true;}
                }
            } else {
                if ((card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.ATTACK) && card.color == this.C && !card.hasTag(AbstractCard.CardTags.HEALING)){
                    this.G.addToBottom(card);
                    if (card.rarity == AbstractCard.CardRarity.COMMON){this.N = true;}
                    if (card.rarity == AbstractCard.CardRarity.UNCOMMON){this.U = true;}
                    if (card.rarity == AbstractCard.CardRarity.RARE){this.R = true;}
                }
            }
        }
        // 首次添加能力更新描述
        this.updateDescription();
    }

    //回合开始时获得随即卡牌
    public void atStartOfTurn() {
        if (this.N || this.U || this.R){
            this.flash();
            CardGroup GG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard.CardRarity cardRarity;
            for (int n = 0 ;n<this.amount;n++){
                //重复执行层数次
                //for (int i = 0;i<3;i++){
                    //生成3次卡片
                    int roll = AbstractDungeon.cardRandomRng.random(99);
                    //稀有度匹配
                    if (this.UP){
                        if (this.U){
                            cardRarity = AbstractCard.CardRarity.UNCOMMON;
                        } else {
                            cardRarity = AbstractCard.CardRarity.RARE;
                        }
                        if (roll < 33 && this.R) {
                            cardRarity = AbstractCard.CardRarity.RARE;
                        }
                    } else {
                        if (this.N){
                            cardRarity = AbstractCard.CardRarity.COMMON;
                        } else if (this.U){
                            cardRarity = AbstractCard.CardRarity.UNCOMMON;
                        } else {
                            cardRarity = AbstractCard.CardRarity.RARE;
                        }
                        if (roll < 65 && this.N) {
                            cardRarity = AbstractCard.CardRarity.COMMON;
                        } else if (roll < 95 && this.U) {
                            cardRarity = AbstractCard.CardRarity.UNCOMMON;
                        } else if (this.R) {
                            cardRarity = AbstractCard.CardRarity.RARE;
                        }
                    }

                    AbstractCard A = this.G.getRandomCard(AbstractDungeon.cardRng,cardRarity).makeCopy();

                    if (!A.isEthereal && !A.exhaust){
                        A.rawDescription = A.rawDescription+ DESCRIPTIONS[2];
                    } else if (!A.exhaust) {
                        //原本只虚无
                        A.rawDescription = A.rawDescription+ DESCRIPTIONS[4];
                    } else {
                        A.rawDescription = A.rawDescription+ DESCRIPTIONS[3];
                    }
                //添加虚无和消耗
                    A.exhaust = true;
                    A.isEthereal = true;
                    //A.purgeOnUse = true;
                    A.name = DESCRIPTIONS[1]+A.name;
                    A.initializeDescription();
                    this.addToBot(new ChoseTempToHandAction(A));
                    //GG.addToBottom(A);
                }
                //选卡
                //this.addToBot(new ChoseTempToHandActiob(GG,1,true));
                //GG.clear();
            //}
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[5]+this.amount+DESCRIPTIONS[6]+this.T+DESCRIPTIONS[7];
    }
}
