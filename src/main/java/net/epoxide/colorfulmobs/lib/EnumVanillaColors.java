package net.epoxide.colorfulmobs.lib;

public enum EnumVanillaColors {
    
    BLACK("dyeBlack", new ColorObject(25, 25, 25)),
    RED("dyeRed", new ColorObject(153, 51, 51)),
    GREEN("dyeGreen", new ColorObject(102, 127, 51)),
    BROWN("dyeBrown", new ColorObject(102, 76, 51)),
    BLUE("dyeBlue", new ColorObject(51, 76, 178)),
    PURPLE("dyePurple", new ColorObject(127, 63, 178)),
    CYAN("dyeCyan", new ColorObject(76, 127, 153)),
    LIGHT_GRAY("dyeLightGray", new ColorObject(153, 153, 153)),
    GRAY("dyeGray", new ColorObject(76, 76, 76)),
    PINK("dyePink", new ColorObject(242, 127, 165)),
    LIME("dyeLime", new ColorObject(127, 204, 25)),
    YELLOW("dyeYellow", new ColorObject(229, 229, 51)),
    LIGHT_BLUE("dyeLightBlue", new ColorObject(102, 153, 216)),
    MAGENTAG("dyeMagenta", new ColorObject(178, 76, 216)),
    ORANGE("dyeOrange", new ColorObject(216, 127, 5)),
    WHITE("dyeWhite", new ColorObject(255, 255, 255));
    
    public String colorName;
    public ColorObject colorObj;
    
    EnumVanillaColors(String name, ColorObject color) {
        
        colorName = name;
        colorObj = color;
    }
}
