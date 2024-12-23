package Amiyamod.cards.YCard;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.ChoseTemCardAction;
import Amiyamod.action.cards.ChoseTempToHandAction;
import Amiyamod.action.cards.KingSayAction;
import Amiyamod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import basemod.devcommands.history.History;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.stats.RunData;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.Objects;

import static basemod.devcommands.history.History.characterIndex;
import static basemod.devcommands.history.History.getVictories;

public class KingSay2 extends CustomCard {
    //=================================================================================================================
    //@ 【魔王律令】 不能被打出 ，抽到此卡时将 !M! 张随机 源石技艺 拿到手中，回合结束时若它仍在手中则 消耗 所有手牌。
    //=================================================================================================================
    private static final String NAME = "KingSay";// 【卡片名字】

    public static final String ID = Amiyamod.makeID(NAME);//卡片ID
    private static final CardColor COLOR = CardColorEnum.AMIYA;//卡牌颜色
    public static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = "img/cards/"+NAME+".png";//卡图

    private static final int COST = 0;//【卡片费用】
    private static final CardType TYPE = CardType.SKILL;//【卡片类型】
    private static final CardRarity RARITY = CardRarity.COMMON;//【卡片稀有度】，基础BASIC 普通COMMON 罕见UNCOMMON 稀有RARE 特殊SPECIAL 诅咒CURSE
    private static final CardTarget TARGET = CardTarget.SELF;//【是否指向敌人】
    public ArrayList<AbstractCard> list = new ArrayList<>();

    public KingSay2() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, COST,CARD_STRINGS.EXTENDED_DESCRIPTION[4], TYPE, COLOR, RARITY, TARGET);
        //this.damage = this.baseDamage = 8;
        //this.baseBlock = this.block = 12;
        this.magicNumber = this.baseMagicNumber = 3;
        this.baseDraw = this.draw =1;
        //this.heal = 15;
        this.misc = 1;
        //DamageModifierManager.addModifier(this, new KingDamage());
        this.exhaust = true;
        //this.isEthereal = true;
        //this.selfRetain = true;

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
            this.exhaust = false;
            //this.isEthereal = false;
            //this.upgradeDamage(4);
            //this.upgradeMagicNumber(2);
            //this.baseDraw += 1;
            //this.draw = this.baseDraw;
            //this.selfRetain = true;
            //this.selfRetain = true;
            //this.upgradeBaseCost(0);
            //this.target = CardTarget.ENEMY;
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[5];
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

            ArrayList<RunData> rdlist = getVictories(characterIndex(AbstractDungeon.player));

            if (!rdlist.isEmpty()) {
                CardGroup G = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                RunData rd = ((RunData)rdlist.get(rdlist.size() - 1));
                boolean ok = false;
                for(String card : rd.master_deck) {
                    AbstractCard ac = null;
                    LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                            "传承："+card
                    );
                    if (card.matches(".*\\+\\d+")) {
                        int index = card.lastIndexOf("+");

                        for(AbstractCard c : CardLibrary.getAllCards()){
                            if(Objects.equals(c.cardID, card.substring(0, index))){
                                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                        "=确实="
                                );
                                ok = true;
                                break;
                            }
                        }
                        if(ok){
                            ac = CardLibrary.getCopy(card.substring(0, index));
                            for (int x = 0;x<index;x++){
                                ac.upgrade();
                                ac.applyPowers();
                                ac.initializeDescription();
                            }
                        }
                    } else {
                        for(AbstractCard c : CardLibrary.getAllCards()){
                            if(Objects.equals(c.cardID, card)){
                                LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                                        "=确实="
                                );
                                ok = true;
                                break;
                            }
                        }
                        if (ok){
                            ac = CardLibrary.getCopy(card);
                        }
                    }
                    if (ac!=null &&  !(ac instanceof KingSay2) && ac.type != CardType.CURSE && ac.type != CardType.STATUS && ac.rarity != CardRarity.BASIC){
                        G.addToBottom(ac);
                    }
                }
                for (int i= 0;i<this.draw;i++){
                    CardGroup GG = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    GG.group.addAll(G.group);

                    ArrayList<AbstractCard> list = new ArrayList<>();
                    int n = Math.min(G.size(),this.magicNumber);

                    for (int z = 0;z<n;z++){
                        AbstractCard ca = GG.getRandomCard(true);
                        GG.removeCard(ca);
                        list.add(ca);
                    }
                    if (!list.isEmpty()){
                        this.addToBot(new ChoseTemCardAction(list));
                    }
                    G.clear();
                }
            }

    }

    public AbstractCard makeCopy() {return new KingSay2();}
}
