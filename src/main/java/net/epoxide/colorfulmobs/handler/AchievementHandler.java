package net.epoxide.colorfulmobs.handler;

import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementHandler {
    
    public static AchievementPage achPage;
    public static Achievement achKillDyed;
    public static Achievement achDyeMob;
    public static Achievement achCloneDye;
    public static Achievement achGhost;
    public static Achievement achRainbow;
    public static Achievement achWand;
    public static Achievement achColorDeath;
    public static Achievement achPureDye;
    public static Achievement achDataChecker;
    
    public AchievementHandler() {
    
        achKillDyed = new Achievement("cfm.harvester", "cfm.harvester", 0, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(175, 0, 0)), null).func_180788_c();
        achDyeMob = new Achievement("cfm.artist", "cfm.artist", 0, 2, ItemColoredPowder.getStackFromColorObject(new ColorObject(255, 105, 180)), null).func_180788_c();
        achCloneDye = new Achievement("cfm.artisan", "cfm.artisan", 2, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(15, 255, 15)), null).func_180788_c();
        achGhost = new Achievement("cfm.ghost", "cfm.ghost", 2, 2, ColorfulMobs.itemGhostDust, null).func_180788_c();
        achRainbow = new Achievement("cfm.rainbow", "cfm.rainbow", 1, 1, ColorfulMobs.itemRainbowDust, null).setSpecial().func_180788_c();
        achWand = new Achievement("cfm.overload", "cfm.overload", 1, 2, ColorfulMobs.itemColorWand, null).func_180788_c();
        achColorDeath = new Achievement("cfm.death", "cfm.death", 2, 1, ItemColoredPowder.getStackFromColorObject(new ColorObject(20, 20, 20)), null).func_180788_c();
        achPureDye = new Achievement("cfm.purify", "cfm.purify", 1, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(255, 255, 255)), null).func_180788_c();
        achDataChecker = new Achievement("cfm.datachecker", "cfm.datachecker", 0, 1, ColorfulMobs.itemDataChecker, null).func_180788_c();
        
        Achievement[] achList = { achKillDyed, achDyeMob, achCloneDye, achGhost, achRainbow, achWand, achColorDeath, achPureDye, achDataChecker };
        achPage = new AchievementPage("Colorful Mobs", achList);
        AchievementPage.registerAchievementPage(achPage);
    }
}