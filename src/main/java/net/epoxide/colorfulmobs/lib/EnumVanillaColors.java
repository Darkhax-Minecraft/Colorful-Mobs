package net.epoxide.colorfulmobs.lib;

public enum EnumVanillaColors {

    BLACK("black", new ColorObject(25, 25, 25)),
    RED("red", new ColorObject(153, 51, 51)),
    GREEN("green", new ColorObject(102, 127, 51)),
    BROWN("brown", new ColorObject(102, 76, 51)),
    BLUE("blue", new ColorObject(51, 76, 178)),  
    PURPLE("purple", new ColorObject(127, 63, 178)),
    CYAN("cyan", new ColorObject(76, 127, 153)),
    LIGHT_GRAY("lightGray", new ColorObject(153, 153, 153)),
    GRAY("gray", new ColorObject(76, 76, 76)),
    PINK("pink", new ColorObject(242, 127, 165)),
    LIME("lime", new ColorObject(127, 204, 25)),
    YELLOW("yellow", new ColorObject(229, 229, 51)), 
    LIGHT_BLUE("lightBlue", new ColorObject(102, 153, 216)),
    MAGENTAG("magenta", new ColorObject(178, 76, 216)),
    ORANGE("orange", new ColorObject(216, 127, 5)), 
    WHITE("white", new ColorObject(255, 255, 255));
    
    public String colorName;
    public ColorObject colorObj;
    
    EnumVanillaColors(String name, ColorObject color) {
        
        colorName = name;
        colorObj = color;
    }
}
