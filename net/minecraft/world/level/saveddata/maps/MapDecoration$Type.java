/*     */ package net.minecraft.world.level.saveddata.maps;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.material.MapColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Type
/*     */   implements StringRepresentable
/*     */ {
/*  13 */   PLAYER("player", false, true),
/*  14 */   FRAME("frame", true, true),
/*  15 */   RED_MARKER("red_marker", false, true),
/*  16 */   BLUE_MARKER("blue_marker", false, true),
/*  17 */   TARGET_X("target_x", true, false),
/*  18 */   TARGET_POINT("target_point", true, false),
/*  19 */   PLAYER_OFF_MAP("player_off_map", false, true),
/*  20 */   PLAYER_OFF_LIMITS("player_off_limits", false, true),
/*  21 */   MANSION("mansion", true, 5393476, false, true),
/*  22 */   MONUMENT("monument", true, 3830373, false, true),
/*  23 */   BANNER_WHITE("banner_white", true, true),
/*  24 */   BANNER_ORANGE("banner_orange", true, true),
/*  25 */   BANNER_MAGENTA("banner_magenta", true, true),
/*  26 */   BANNER_LIGHT_BLUE("banner_light_blue", true, true),
/*  27 */   BANNER_YELLOW("banner_yellow", true, true),
/*  28 */   BANNER_LIME("banner_lime", true, true),
/*  29 */   BANNER_PINK("banner_pink", true, true),
/*  30 */   BANNER_GRAY("banner_gray", true, true),
/*  31 */   BANNER_LIGHT_GRAY("banner_light_gray", true, true),
/*  32 */   BANNER_CYAN("banner_cyan", true, true),
/*  33 */   BANNER_PURPLE("banner_purple", true, true),
/*  34 */   BANNER_BLUE("banner_blue", true, true),
/*  35 */   BANNER_BROWN("banner_brown", true, true),
/*  36 */   BANNER_GREEN("banner_green", true, true),
/*  37 */   BANNER_RED("banner_red", true, true),
/*  38 */   BANNER_BLACK("banner_black", true, true),
/*  39 */   RED_X("red_x", true, false),
/*  40 */   DESERT_VILLAGE("village_desert", true, MapColor.COLOR_LIGHT_GRAY.col, false, true),
/*  41 */   PLAINS_VILLAGE("village_plains", true, MapColor.COLOR_LIGHT_GRAY.col, false, true),
/*  42 */   SAVANNA_VILLAGE("village_savanna", true, MapColor.COLOR_LIGHT_GRAY.col, false, true),
/*  43 */   SNOWY_VILLAGE("village_snowy", true, MapColor.COLOR_LIGHT_GRAY.col, false, true),
/*  44 */   TAIGA_VILLAGE("village_taiga", true, MapColor.COLOR_LIGHT_GRAY.col, false, true),
/*  45 */   JUNGLE_TEMPLE("jungle_temple", true, MapColor.COLOR_LIGHT_GRAY.col, false, true),
/*  46 */   SWAMP_HUT("swamp_hut", true, MapColor.COLOR_LIGHT_GRAY.col, false, true);
/*     */   
/*     */   static {
/*  49 */     CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<Type> CODEC;
/*     */   
/*     */   private final String name;
/*     */   private final byte icon;
/*     */   private final boolean renderedOnFrame;
/*     */   private final int mapColor;
/*     */   private final boolean isExplorationMapElement;
/*     */   private final boolean trackCount;
/*     */   
/*     */   Type(String $$0, boolean $$1, int $$2, boolean $$3, boolean $$4) {
/*  63 */     this.name = $$0;
/*  64 */     this.trackCount = $$3;
/*  65 */     this.icon = (byte)ordinal();
/*  66 */     this.renderedOnFrame = $$1;
/*  67 */     this.mapColor = $$2;
/*  68 */     this.isExplorationMapElement = $$4;
/*     */   }
/*     */   
/*     */   public byte getIcon() {
/*  72 */     return this.icon;
/*     */   }
/*     */   
/*     */   public boolean isExplorationMapElement() {
/*  76 */     return this.isExplorationMapElement;
/*     */   }
/*     */   
/*     */   public boolean isRenderedOnFrame() {
/*  80 */     return this.renderedOnFrame;
/*     */   }
/*     */   
/*     */   public boolean hasMapColor() {
/*  84 */     return (this.mapColor >= 0);
/*     */   }
/*     */   
/*     */   public int getMapColor() {
/*  88 */     return this.mapColor;
/*     */   }
/*     */   
/*     */   public static Type byIcon(byte $$0) {
/*  92 */     return values()[Mth.clamp($$0, 0, (values()).length - 1)];
/*     */   }
/*     */   
/*     */   public boolean shouldTrackCount() {
/*  96 */     return this.trackCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 101 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapDecoration$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */