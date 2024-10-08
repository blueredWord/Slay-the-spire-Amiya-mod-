package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

        public class LittleTePower extends AbstractPower {
            public static final String NAME = "LittleTePower";
            public static final String ID1 = "AmiyaMod:"+NAME;
            private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(ID1);
            public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
            public LittleTePower(AbstractCreature owner) {
                this.name = powerStrings.NAME;
                this.ID = ID1;
                this.owner = owner;
                // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
                this.amount = -1;
                this.type = PowerType.BUFF;
                // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
                this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
                this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
                // 首次添加能力更新描述
                this.updateDescription();
            }

            // 能力在更新时如何修改描述
            public void updateDescription() {
                this.description = DESCRIPTIONS[0];
            }

            // 效果 : 不获取格挡，改为获得等量的丝线
            @Override
            public float modifyBlock(float blockAmount, AbstractCard card) {
                if (blockAmount > 0){
                    this.flash();
                    AbstractDungeon.actionManager.addToBottom(
                            new AddTemporaryHPAction(
                                    this.owner, //受益者是能力持有者
                                    this.owner, //来源是能力持有者
                                    (int)blockAmount //获得数量为原本获得的格挡
                            )
                    );
                    return 0;
                }else{
                    return blockAmount;
                }
            }

}
