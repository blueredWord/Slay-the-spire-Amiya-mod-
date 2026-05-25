package Amiyamod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.HashMap;

@SpirePatch(cls = "com.megacrit.cardcrawl.audio.SoundMaster", method = "playA", paramtypes = {
        "java.lang.String", "float"})
public class SoundPatch {

    public static HashMap<String, Sfx> map = new HashMap<>();

    public static long Postfix(long res, SoundMaster _inst, String key, float pitchAdjust) {
        if (map.containsKey(key)) {

            return ((Sfx) map.get(key)).play(
                    Settings.SOUND_VOLUME * Settings.MASTER_VOLUME,
                    1.0F + pitchAdjust,
                    0.0F
            );

        }
        return 0L;
    }

    private static Sfx load(String filename) {
        return new Sfx("SE/" + filename, false);
    }

    static {
        ArrayList<String> A = new ArrayList<>();
        // this.addToBot(new SEAction(NAME));
        A.add("SadMind");
        A.add("ShadowOut");
        A.add("ShadowRunNight");
        A.add("MakeLine");
        A.add("SeeMe");
        A.add("Wish");
        A.add("ShadowBlood");
        A.add("Mercy");
        A.add("AmiyaStrike");
        A.add("DA");
        A.add("Solo");
        A.add("ShadowBreak");
        A.add("Memory");
        A.add("Punch");
        A.add("BlackKing");
        A.add("Hate");
        A.add("ShadowBackWindy");
        A.add("HerSee");
        A.add("MindBreak");
        A.add("ShadowNoShadow");
        A.add("LoseWish");
        A.add("ShadowCry");
        A.add("ShadowTwo");
        A.add("FinalSong");
        A.add("KingType2");
        A.add("DefendMagic");
        A.add("Hope");
        A.add("ChiMeRa");
        A.add("Bonk");
        A.add("MindLink");
        A.add("MindEat");
        A.add("LifeMade");
        A.add("ShadowWaterMusic");
        A.add("SoulMilk");
        A.add("ShadowSkyOpen");
        A.add("Horn");
        A.add("AngryForever");
        A.add("HerDo");
        A.add("PainMagic");
        A.add("ShadowYangMei");
        A.add("SwordHeard");
        A.add("MindRoll");
        A.add("RedSky");
        A.add("LittleTe2");
        A.add("YBBS");
        A.add("BeautifulLife");
        A.add("ShadowCloudBreak2");
        A.add("FastSing ");
        A.add("ShadowBack");
        A.add("LineHand");
        A.add("BurnMark");
        A.add("BadMagic");
// this.addToBot(new SEAction(NAME));

        map.put("Amiya_SELECT", load("title.wav"));
        map.put("break", load("break.wav"));
        for (String a : A){
            map.put(a, load("card/"+a+".MP3"));
        }
    }
}