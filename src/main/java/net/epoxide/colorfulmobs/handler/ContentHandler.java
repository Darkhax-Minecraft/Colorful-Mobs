package net.epoxide.colorfulmobs.handler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.epoxide.colorfulmobs.items.ItemColorWand;
import net.epoxide.colorfulmobs.items.ItemColoredPowder;
import net.epoxide.colorfulmobs.items.ItemDataChecker;
import net.epoxide.colorfulmobs.items.ItemGhostDust;
import net.epoxide.colorfulmobs.items.ItemRainbowDust;
import net.epoxide.colorfulmobs.lib.ColorObject;
import net.epoxide.colorfulmobs.lib.EnumVanillaColors;
import net.epoxide.colorfulmobs.recipe.RecipeDyePowder;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ContentHandler {

    public static Item itemColorWand;
    public static Item itemDataChecker;
    public static Item itemRainbowDust;
    public static Item itemGhostDust;
    public static Item itemPowder;

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

    public static void initItems () {

        itemColorWand = new ItemColorWand();
        GameRegistry.registerItem(itemColorWand, "colorWand");

        itemDataChecker = new ItemDataChecker();
        GameRegistry.registerItem(itemDataChecker, "dataChecker");

        itemRainbowDust = new ItemRainbowDust();
        GameRegistry.registerItem(itemRainbowDust, "rainbowDust");

        itemGhostDust = new ItemGhostDust();
        GameRegistry.registerItem(itemGhostDust, "ghostDust");

        itemPowder = new ItemColoredPowder();
        GameRegistry.registerItem(itemPowder, "colorPowder");
    }

    public static void initRecipe () {

        if (ConfigurationHandler.cloneDye)
            GameRegistry.addRecipe(new RecipeDyePowder());

        if (ConfigurationHandler.craftDye) {
            for (EnumVanillaColors color : EnumVanillaColors.values()) {

                ItemStack powderStack = new ItemStack(itemPowder, 3);
                color.colorObj.writeToItemStack(powderStack);
                GameRegistry.addRecipe(new ShapedOreRecipe(powderStack, new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), color.colorName }));
            }
        }

        if (ConfigurationHandler.craftBook)
            GameRegistry.addShapelessRecipe(new ItemStack(itemDataChecker), itemPowder, Items.book);

        if (ConfigurationHandler.craftGhostDust)
            GameRegistry.addShapedRecipe(new ItemStack(itemGhostDust, 3), new Object[] { " s ", "pdp", " p ", Character.valueOf('s'), Items.string, Character.valueOf('p'), Items.paper, Character.valueOf('d'), Items.quartz });

        if (ConfigurationHandler.craftRainbowWand)
            GameRegistry.addShapedRecipe(new ItemStack(itemColorWand), new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), itemRainbowDust, Character.valueOf('y'), Items.stick });
    }

    public static void initLoot () {

        for (int pos = 0; pos < ConfigurationHandler.validLootLocations.size(); pos++)
            ChestGenHooks.addItem(ConfigurationHandler.validLootLocations.get(pos), new WeightedRandomChestContent(new ItemStack(itemRainbowDust), 1, 1, ConfigurationHandler.rainbowDustRate));
    }

    public static void initAchievements () {

        achKillDyed = new Achievement("cfm.harvester", "cfm.harvester", 0, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(175, 0, 0)), null).registerStat();
        achDyeMob = new Achievement("cfm.artist", "cfm.artist", 0, 2, ItemColoredPowder.getStackFromColorObject(new ColorObject(255, 105, 180)), null).registerStat();
        achCloneDye = new Achievement("cfm.artisan", "cfm.artisan", 2, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(15, 255, 15)), null).registerStat();
        achGhost = new Achievement("cfm.ghost", "cfm.ghost", 2, 2, itemGhostDust, null).registerStat();
        achRainbow = new Achievement("cfm.rainbow", "cfm.rainbow", 1, 1, itemRainbowDust, null).setSpecial().registerStat();
        achWand = new Achievement("cfm.overload", "cfm.overload", 1, 2, itemColorWand, null).registerStat();
        achColorDeath = new Achievement("cfm.death", "cfm.death", 2, 1, ItemColoredPowder.getStackFromColorObject(new ColorObject(20, 20, 20)), null).registerStat();
        achPureDye = new Achievement("cfm.purify", "cfm.purify", 1, 0, ItemColoredPowder.getStackFromColorObject(new ColorObject(255, 255, 255)), null).registerStat();
        achDataChecker = new Achievement("cfm.datachecker", "cfm.datachecker", 0, 1, itemDataChecker, null).registerStat();

        Achievement[] achList = { achKillDyed, achDyeMob, achCloneDye, achGhost, achRainbow, achWand, achColorDeath, achPureDye, achDataChecker };
        achPage = new AchievementPage("Colorful Mobs", achList);
        AchievementPage.registerAchievementPage(achPage);
    }
}
