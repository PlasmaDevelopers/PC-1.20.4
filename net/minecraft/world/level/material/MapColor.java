/*     */ package net.minecraft.world.level.material;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ 
/*     */ public class MapColor {
/*   6 */   private static final MapColor[] MATERIAL_COLORS = new MapColor[64];
/*     */   
/*   8 */   public static final MapColor NONE = new MapColor(0, 0);
/*   9 */   public static final MapColor GRASS = new MapColor(1, 8368696);
/*  10 */   public static final MapColor SAND = new MapColor(2, 16247203);
/*  11 */   public static final MapColor WOOL = new MapColor(3, 13092807);
/*  12 */   public static final MapColor FIRE = new MapColor(4, 16711680);
/*  13 */   public static final MapColor ICE = new MapColor(5, 10526975);
/*  14 */   public static final MapColor METAL = new MapColor(6, 10987431);
/*  15 */   public static final MapColor PLANT = new MapColor(7, 31744);
/*  16 */   public static final MapColor SNOW = new MapColor(8, 16777215);
/*  17 */   public static final MapColor CLAY = new MapColor(9, 10791096);
/*  18 */   public static final MapColor DIRT = new MapColor(10, 9923917);
/*  19 */   public static final MapColor STONE = new MapColor(11, 7368816);
/*  20 */   public static final MapColor WATER = new MapColor(12, 4210943);
/*  21 */   public static final MapColor WOOD = new MapColor(13, 9402184);
/*  22 */   public static final MapColor QUARTZ = new MapColor(14, 16776437);
/*  23 */   public static final MapColor COLOR_ORANGE = new MapColor(15, 14188339);
/*  24 */   public static final MapColor COLOR_MAGENTA = new MapColor(16, 11685080);
/*  25 */   public static final MapColor COLOR_LIGHT_BLUE = new MapColor(17, 6724056);
/*  26 */   public static final MapColor COLOR_YELLOW = new MapColor(18, 15066419);
/*  27 */   public static final MapColor COLOR_LIGHT_GREEN = new MapColor(19, 8375321);
/*  28 */   public static final MapColor COLOR_PINK = new MapColor(20, 15892389);
/*  29 */   public static final MapColor COLOR_GRAY = new MapColor(21, 5000268);
/*  30 */   public static final MapColor COLOR_LIGHT_GRAY = new MapColor(22, 10066329);
/*  31 */   public static final MapColor COLOR_CYAN = new MapColor(23, 5013401);
/*  32 */   public static final MapColor COLOR_PURPLE = new MapColor(24, 8339378);
/*  33 */   public static final MapColor COLOR_BLUE = new MapColor(25, 3361970);
/*  34 */   public static final MapColor COLOR_BROWN = new MapColor(26, 6704179);
/*  35 */   public static final MapColor COLOR_GREEN = new MapColor(27, 6717235);
/*  36 */   public static final MapColor COLOR_RED = new MapColor(28, 10040115);
/*  37 */   public static final MapColor COLOR_BLACK = new MapColor(29, 1644825);
/*  38 */   public static final MapColor GOLD = new MapColor(30, 16445005);
/*  39 */   public static final MapColor DIAMOND = new MapColor(31, 6085589);
/*  40 */   public static final MapColor LAPIS = new MapColor(32, 4882687);
/*  41 */   public static final MapColor EMERALD = new MapColor(33, 55610);
/*  42 */   public static final MapColor PODZOL = new MapColor(34, 8476209);
/*  43 */   public static final MapColor NETHER = new MapColor(35, 7340544);
/*     */   
/*  45 */   public static final MapColor TERRACOTTA_WHITE = new MapColor(36, 13742497);
/*  46 */   public static final MapColor TERRACOTTA_ORANGE = new MapColor(37, 10441252);
/*  47 */   public static final MapColor TERRACOTTA_MAGENTA = new MapColor(38, 9787244);
/*  48 */   public static final MapColor TERRACOTTA_LIGHT_BLUE = new MapColor(39, 7367818);
/*  49 */   public static final MapColor TERRACOTTA_YELLOW = new MapColor(40, 12223780);
/*  50 */   public static final MapColor TERRACOTTA_LIGHT_GREEN = new MapColor(41, 6780213);
/*  51 */   public static final MapColor TERRACOTTA_PINK = new MapColor(42, 10505550);
/*  52 */   public static final MapColor TERRACOTTA_GRAY = new MapColor(43, 3746083);
/*  53 */   public static final MapColor TERRACOTTA_LIGHT_GRAY = new MapColor(44, 8874850);
/*  54 */   public static final MapColor TERRACOTTA_CYAN = new MapColor(45, 5725276);
/*  55 */   public static final MapColor TERRACOTTA_PURPLE = new MapColor(46, 8014168);
/*  56 */   public static final MapColor TERRACOTTA_BLUE = new MapColor(47, 4996700);
/*  57 */   public static final MapColor TERRACOTTA_BROWN = new MapColor(48, 4993571);
/*  58 */   public static final MapColor TERRACOTTA_GREEN = new MapColor(49, 5001770);
/*  59 */   public static final MapColor TERRACOTTA_RED = new MapColor(50, 9321518);
/*  60 */   public static final MapColor TERRACOTTA_BLACK = new MapColor(51, 2430480);
/*     */   
/*  62 */   public static final MapColor CRIMSON_NYLIUM = new MapColor(52, 12398641);
/*  63 */   public static final MapColor CRIMSON_STEM = new MapColor(53, 9715553);
/*  64 */   public static final MapColor CRIMSON_HYPHAE = new MapColor(54, 6035741);
/*  65 */   public static final MapColor WARPED_NYLIUM = new MapColor(55, 1474182);
/*  66 */   public static final MapColor WARPED_STEM = new MapColor(56, 3837580);
/*  67 */   public static final MapColor WARPED_HYPHAE = new MapColor(57, 5647422);
/*  68 */   public static final MapColor WARPED_WART_BLOCK = new MapColor(58, 1356933);
/*  69 */   public static final MapColor DEEPSLATE = new MapColor(59, 6579300);
/*  70 */   public static final MapColor RAW_IRON = new MapColor(60, 14200723);
/*  71 */   public static final MapColor GLOW_LICHEN = new MapColor(61, 8365974);
/*     */   
/*     */   public final int col;
/*     */   public final int id;
/*     */   
/*     */   private MapColor(int $$0, int $$1) {
/*  77 */     if ($$0 < 0 || $$0 > 63) {
/*  78 */       throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
/*     */     }
/*  80 */     this.id = $$0;
/*  81 */     this.col = $$1;
/*  82 */     MATERIAL_COLORS[$$0] = this;
/*     */   }
/*     */   
/*     */   public int calculateRGBColor(Brightness $$0) {
/*  86 */     if (this == NONE) {
/*  87 */       return 0;
/*     */     }
/*     */     
/*  90 */     int $$1 = $$0.modifier;
/*  91 */     int $$2 = (this.col >> 16 & 0xFF) * $$1 / 255;
/*  92 */     int $$3 = (this.col >> 8 & 0xFF) * $$1 / 255;
/*  93 */     int $$4 = (this.col & 0xFF) * $$1 / 255;
/*     */     
/*  95 */     return 0xFF000000 | $$4 << 16 | $$3 << 8 | $$2;
/*     */   }
/*     */   
/*     */   public static MapColor byId(int $$0) {
/*  99 */     Preconditions.checkPositionIndex($$0, MATERIAL_COLORS.length, "material id");
/* 100 */     return byIdUnsafe($$0);
/*     */   }
/*     */   
/*     */   private static MapColor byIdUnsafe(int $$0) {
/* 104 */     MapColor $$1 = MATERIAL_COLORS[$$0];
/* 105 */     return ($$1 != null) ? $$1 : NONE;
/*     */   }
/*     */   
/*     */   public static int getColorFromPackedId(int $$0) {
/* 109 */     int $$1 = $$0 & 0xFF;
/* 110 */     return byIdUnsafe($$1 >> 2).calculateRGBColor(Brightness.byIdUnsafe($$1 & 0x3));
/*     */   }
/*     */   
/*     */   public byte getPackedId(Brightness $$0) {
/* 114 */     return (byte)(this.id << 2 | $$0.id & 0x3);
/*     */   }
/*     */   
/*     */   public enum Brightness {
/* 118 */     LOW(0, 180),
/* 119 */     NORMAL(1, 220),
/* 120 */     HIGH(2, 255),
/* 121 */     LOWEST(3, 135);
/*     */ 
/*     */     
/* 124 */     private static final Brightness[] VALUES = new Brightness[] { LOW, NORMAL, HIGH, LOWEST };
/*     */     
/*     */     public final int id;
/*     */ 
/*     */     
/*     */     Brightness(int $$0, int $$1) {
/* 130 */       this.id = $$0;
/* 131 */       this.modifier = $$1;
/*     */     } public final int modifier; static {
/*     */     
/*     */     } public static Brightness byId(int $$0) {
/* 135 */       Preconditions.checkPositionIndex($$0, VALUES.length, "brightness id");
/* 136 */       return byIdUnsafe($$0);
/*     */     }
/*     */     
/*     */     static Brightness byIdUnsafe(int $$0) {
/* 140 */       return VALUES[$$0];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\MapColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */