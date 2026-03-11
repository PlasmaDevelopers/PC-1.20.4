/*    */ package net.minecraft.data.models.model;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public final class TextureSlot {
/*  6 */   public static final TextureSlot ALL = create("all");
/*  7 */   public static final TextureSlot TEXTURE = create("texture", ALL);
/*  8 */   public static final TextureSlot PARTICLE = create("particle", TEXTURE);
/*  9 */   public static final TextureSlot END = create("end", ALL);
/* 10 */   public static final TextureSlot BOTTOM = create("bottom", END);
/* 11 */   public static final TextureSlot TOP = create("top", END);
/* 12 */   public static final TextureSlot FRONT = create("front", ALL);
/* 13 */   public static final TextureSlot BACK = create("back", ALL);
/* 14 */   public static final TextureSlot SIDE = create("side", ALL);
/* 15 */   public static final TextureSlot NORTH = create("north", SIDE);
/* 16 */   public static final TextureSlot SOUTH = create("south", SIDE);
/* 17 */   public static final TextureSlot EAST = create("east", SIDE);
/* 18 */   public static final TextureSlot WEST = create("west", SIDE);
/* 19 */   public static final TextureSlot UP = create("up");
/* 20 */   public static final TextureSlot DOWN = create("down");
/* 21 */   public static final TextureSlot CROSS = create("cross");
/* 22 */   public static final TextureSlot PLANT = create("plant");
/* 23 */   public static final TextureSlot WALL = create("wall", ALL);
/* 24 */   public static final TextureSlot RAIL = create("rail");
/* 25 */   public static final TextureSlot WOOL = create("wool");
/* 26 */   public static final TextureSlot PATTERN = create("pattern");
/* 27 */   public static final TextureSlot PANE = create("pane");
/* 28 */   public static final TextureSlot EDGE = create("edge");
/* 29 */   public static final TextureSlot FAN = create("fan");
/* 30 */   public static final TextureSlot STEM = create("stem");
/* 31 */   public static final TextureSlot UPPER_STEM = create("upperstem");
/* 32 */   public static final TextureSlot CROP = create("crop");
/* 33 */   public static final TextureSlot DIRT = create("dirt");
/* 34 */   public static final TextureSlot FIRE = create("fire");
/* 35 */   public static final TextureSlot LANTERN = create("lantern");
/* 36 */   public static final TextureSlot PLATFORM = create("platform");
/* 37 */   public static final TextureSlot UNSTICKY = create("unsticky");
/* 38 */   public static final TextureSlot TORCH = create("torch");
/* 39 */   public static final TextureSlot LAYER0 = create("layer0");
/* 40 */   public static final TextureSlot LAYER1 = create("layer1");
/* 41 */   public static final TextureSlot LAYER2 = create("layer2");
/* 42 */   public static final TextureSlot LIT_LOG = create("lit_log");
/* 43 */   public static final TextureSlot CANDLE = create("candle");
/* 44 */   public static final TextureSlot INSIDE = create("inside");
/* 45 */   public static final TextureSlot CONTENT = create("content");
/* 46 */   public static final TextureSlot INNER_TOP = create("inner_top");
/* 47 */   public static final TextureSlot FLOWERBED = create("flowerbed");
/*    */   
/*    */   private final String id;
/*    */   
/*    */   @Nullable
/*    */   private final TextureSlot parent;
/*    */   
/*    */   private static TextureSlot create(String $$0) {
/* 55 */     return new TextureSlot($$0, null);
/*    */   }
/*    */   
/*    */   private static TextureSlot create(String $$0, TextureSlot $$1) {
/* 59 */     return new TextureSlot($$0, $$1);
/*    */   }
/*    */   
/*    */   private TextureSlot(String $$0, @Nullable TextureSlot $$1) {
/* 63 */     this.id = $$0;
/* 64 */     this.parent = $$1;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 68 */     return this.id;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public TextureSlot getParent() {
/* 73 */     return this.parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 78 */     return "#" + this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\TextureSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */