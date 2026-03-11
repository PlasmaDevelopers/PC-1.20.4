/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.material.MapColor;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ 
/*     */ public enum DyeColor implements StringRepresentable {
/*  15 */   WHITE(0, "white", 16383998, MapColor.SNOW, 15790320, 16777215),
/*  16 */   ORANGE(1, "orange", 16351261, MapColor.COLOR_ORANGE, 15435844, 16738335),
/*  17 */   MAGENTA(2, "magenta", 13061821, MapColor.COLOR_MAGENTA, 12801229, 16711935),
/*  18 */   LIGHT_BLUE(3, "light_blue", 3847130, MapColor.COLOR_LIGHT_BLUE, 6719955, 10141901),
/*  19 */   YELLOW(4, "yellow", 16701501, MapColor.COLOR_YELLOW, 14602026, 16776960),
/*  20 */   LIME(5, "lime", 8439583, MapColor.COLOR_LIGHT_GREEN, 4312372, 12582656),
/*  21 */   PINK(6, "pink", 15961002, MapColor.COLOR_PINK, 14188952, 16738740),
/*  22 */   GRAY(7, "gray", 4673362, MapColor.COLOR_GRAY, 4408131, 8421504),
/*  23 */   LIGHT_GRAY(8, "light_gray", 10329495, MapColor.COLOR_LIGHT_GRAY, 11250603, 13882323),
/*  24 */   CYAN(9, "cyan", 1481884, MapColor.COLOR_CYAN, 2651799, 65535),
/*  25 */   PURPLE(10, "purple", 8991416, MapColor.COLOR_PURPLE, 8073150, 10494192),
/*  26 */   BLUE(11, "blue", 3949738, MapColor.COLOR_BLUE, 2437522, 255),
/*  27 */   BROWN(12, "brown", 8606770, MapColor.COLOR_BROWN, 5320730, 9127187),
/*  28 */   GREEN(13, "green", 6192150, MapColor.COLOR_GREEN, 3887386, 65280),
/*  29 */   RED(14, "red", 11546150, MapColor.COLOR_RED, 11743532, 16711680),
/*  30 */   BLACK(15, "black", 1908001, MapColor.COLOR_BLACK, 1973019, 0); private static final IntFunction<DyeColor> BY_ID; private static final Int2ObjectOpenHashMap<DyeColor> BY_FIREWORK_COLOR;
/*     */   
/*     */   static {
/*  33 */     BY_ID = ByIdMap.continuous(DyeColor::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*  34 */     BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap((Map)Arrays.<DyeColor>stream(values()).collect(Collectors.toMap($$0 -> Integer.valueOf($$0.fireworkColor), $$0 -> $$0)));
/*     */     
/*  36 */     CODEC = StringRepresentable.fromEnum(DyeColor::values);
/*     */   }
/*     */   public static final StringRepresentable.EnumCodec<DyeColor> CODEC; private final int id;
/*     */   private final String name;
/*     */   private final MapColor mapColor;
/*     */   private final float[] textureDiffuseColors;
/*     */   private final int fireworkColor;
/*     */   private final int textColor;
/*     */   
/*     */   DyeColor(int $$0, String $$1, int $$2, MapColor $$3, int $$4, int $$5) {
/*  46 */     this.id = $$0;
/*  47 */     this.name = $$1;
/*  48 */     this.mapColor = $$3;
/*  49 */     this.textColor = $$5;
/*     */     
/*  51 */     int $$6 = ($$2 & 0xFF0000) >> 16;
/*  52 */     int $$7 = ($$2 & 0xFF00) >> 8;
/*  53 */     int $$8 = ($$2 & 0xFF) >> 0;
/*  54 */     this.textureDiffuseColors = new float[] { $$6 / 255.0F, $$7 / 255.0F, $$8 / 255.0F };
/*  55 */     this.fireworkColor = $$4;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  59 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  63 */     return this.name;
/*     */   }
/*     */   
/*     */   public float[] getTextureDiffuseColors() {
/*  67 */     return this.textureDiffuseColors;
/*     */   }
/*     */   
/*     */   public MapColor getMapColor() {
/*  71 */     return this.mapColor;
/*     */   }
/*     */   
/*     */   public int getFireworkColor() {
/*  75 */     return this.fireworkColor;
/*     */   }
/*     */   
/*     */   public int getTextColor() {
/*  79 */     return this.textColor;
/*     */   }
/*     */   
/*     */   public static DyeColor byId(int $$0) {
/*  83 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,!null->!null;_,null->_")
/*     */   public static DyeColor byName(String $$0, @Nullable DyeColor $$1) {
/*  89 */     DyeColor $$2 = (DyeColor)CODEC.byName($$0);
/*  90 */     return ($$2 != null) ? $$2 : $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static DyeColor byFireworkColor(int $$0) {
/*  95 */     return (DyeColor)BY_FIREWORK_COLOR.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 105 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DyeColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */