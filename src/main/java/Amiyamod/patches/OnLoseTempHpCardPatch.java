package Amiyamod.patches;

import Amiyamod.Amiyamod;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage"
)
public class OnLoseTempHpCardPatch {
    @SpirePostfixPatch

    public static void Postfix(AbstractCreature __instance, DamageInfo info){
        int damageAmount = info.output;
        int tem=TempHPField.tempHp.get(AbstractDungeon.player);
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：触发卡片的OnLoseTempHpPatch：{}", info
        );
        if ( damageAmount > 0 && tem >= damageAmount){
            for (AbstractCard card : AbstractDungeon.player.hand.group){
                if (card instanceof OnLoseTempHpPower){
                    ((OnLoseTempHpPower)card).onLoseTempHp(new DamageInfo(AbstractDungeon.player,damageAmount), damageAmount);
                }
            }
            for (AbstractCard card : AbstractDungeon.player.drawPile.group){
                if (card instanceof OnLoseTempHpPower){
                    ((OnLoseTempHpPower)card).onLoseTempHp(new DamageInfo(AbstractDungeon.player,damageAmount), damageAmount);
                }
            }
            for (AbstractCard card : AbstractDungeon.player.discardPile.group){
                if (card instanceof OnLoseTempHpPower){
                    ((OnLoseTempHpPower)card).onLoseTempHp(new DamageInfo(AbstractDungeon.player,damageAmount), damageAmount);
                }
            }
        }
    }
}
