package Amiyamod.patches;

import Amiyamod.Amiyamod;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
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
    private static int temhp = 0;
    private static int block = 0;
    private static int hp = 0;
    @SpirePrefixPatch

    public static void Prefix(AbstractCreature __instance, DamageInfo info){

        temhp = Math.max(0,TempHPField.tempHp.get(AbstractDungeon.player));
        block = Math.max(0,AbstractDungeon.player.currentBlock) ;
        hp = AbstractDungeon.player.currentHealth+temhp;

        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：触发prepatch的OnLoseTempHpPatch：{},伤害值：{}", info,info.output
        );
        LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                "模组核心：玩家持有：{} 点格挡。 还有 {} 点丝线。", block,temhp
        );

    }

    @SpirePostfixPatch
    public static void Postfix(AbstractCreature __instance, DamageInfo info){
        temhp -= Math.max(0,TempHPField.tempHp.get(AbstractDungeon.player));
        if (temhp > 0){
            LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                    "模组核心：patch中触发LineLose"+temhp
            );
            Amiyamod.LineLose(temhp);
        }
        Amiyamod.LoseHPthisturn = !(AbstractDungeon.player.currentHealth+temhp == hp);
        temhp = block = hp = 0;
    }
}
