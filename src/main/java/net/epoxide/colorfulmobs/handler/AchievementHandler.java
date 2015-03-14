package net.epoxide.colorfulmobs.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.darkhax.bookshelf.helper.ItemHelper;
import net.darkhax.bookshelf.objects.ColorObject;
import net.epoxide.colorfulmobs.ColorfulMobs;
import net.epoxide.colorfulmobs.common.ColorProperties;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class AchievementHandler {

    public static AchievementPage achPage;
    public static Achievement achKillDyed;
    public static Achievement achDyeMob;
    public static Achievement achCloneDye;
    public static Achievement achGhost;
    public static Achievement achRainbow;
    public static Achievement achWand;
    public static Achievement achBreedFail;
    public static Achievement achBreedSuccess;
    public static Achievement achDataChecker;
    
    public AchievementHandler() {
               
        achKillDyed = new Achievement("cfm.harvester", "cfm.harvester", 0, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(175, 0, 0)), null).registerStat();
        achDyeMob = new Achievement("cfm.artist", "cfm.artist", 0, 2, ItemColoredPowder.getStackFromColorObject(new ColorObject(255, 105, 180)), null).registerStat();
        achCloneDye = new Achievement("cfm.artisan", "cfm.artisan", 2, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(15, 255, 15)), null).registerStat();
        achGhost = new Achievement("cfm.ghost", "cfm.ghost", 2, 2, ColorfulMobs.itemGhostDust, null).registerStat();
        achRainbow = new Achievement("cfm.rainbow", "cfm.rainbow", 1, 1, ColorfulMobs.itemRainbowDust, null).setSpecial().registerStat();
        achWand = new Achievement("cfm.overload", "cfm.overload", 1, 2, ColorfulMobs.itemColorWand, null).registerStat();
        achBreedFail = new Achievement("cfm.disapointment", "cfm.disapointment", 2, 1, Items.wheat, null).registerStat();
        achBreedSuccess = new Achievement("cfm.success", "cfm.success", 1, 0, Items.egg, null).registerStat();
        achDataChecker = new Achievement("cfm.datachecker", "cfm.datachecker", 0, 1, ColorfulMobs.itemDataChecker, null).registerStat();
        
        Achievement[] achList = {achKillDyed, achDyeMob, achCloneDye, achGhost, achRainbow, achWand, achBreedFail, achBreedSuccess, achDataChecker};
        achPage = new AchievementPage("Colorful Mobs", achList);
        AchievementPage.registerAchievementPage(achPage);
    }
}