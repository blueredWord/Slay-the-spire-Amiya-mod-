package Amiyamod.relics;

import Amiyamod.Amiyamod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
//十戒
//每场战斗开始时，可以从5张所有角色的随机卡牌中选择1张加入手牌。
public class TheTen extends AbstractRelic {
    public static final String NAME = "TheTen";
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID = Amiyamod.makeID(NAME);
    // 图片路径
    private static final String IMG_PATH = "img/relics/"+NAME+".png";
    // 构造函数，初始化遗物
    public TheTen() {
        super(ID, IMG_PATH, RelicTier.STARTER, LandingSound.HEAVY);
    }

    // 返回遗物的描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    // 在战斗开始前执行
    public void atBattleStartPreDraw() {
        // 显示遗物的效果
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        // 生成并提供五张卡牌供玩家选择
        this.addToBot(new ChooseOneAction(this.generateCardChoices()));
    }

    // 生成五张随机卡牌
    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> cardChoices = new ArrayList<>();

        // 直到生成五张不同的卡牌
        while (cardChoices.size() != 5) {
            boolean duplicate = false;
            // 随机决定卡牌稀有度
            int roll = AbstractDungeon.cardRandomRng.random(99);
            AbstractCard.CardRarity cardRarity;

            if (roll < 55) {
                cardRarity = AbstractCard.CardRarity.COMMON;
            } else if (roll < 85) {
                cardRarity = AbstractCard.CardRarity.UNCOMMON;
            } else {
                cardRarity = AbstractCard.CardRarity.RARE;
            }

            // 随机选择卡牌类型
            AbstractCard.CardType[] cardTypes = AbstractCard.CardType.values();
            AbstractCard.CardType cardType = cardTypes[AbstractDungeon.cardRandomRng.random(cardTypes.length - 1)];

            // 获取随机卡牌
            AbstractCard card = CardLibrary.getAnyColorCard(cardType, cardRarity);
            // 检查卡牌是否重复

            for (AbstractCard c : cardChoices) {
                if (c.cardID.equals(card.cardID)) {
                    duplicate = true;
                    break;
                }
            }

            // 如果卡牌不重复，则加入到选择列表中
            if (!duplicate) {
                cardChoices.add(card.makeCopy());
            }
        }

        return cardChoices;
    }

    // 返回当前遗物的副本
    public AbstractRelic makeCopy() {
        return new TheTen();
    }
}