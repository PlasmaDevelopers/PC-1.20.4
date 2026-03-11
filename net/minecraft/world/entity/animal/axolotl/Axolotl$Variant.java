/*     */ package net.minecraft.world.entity.animal.axolotl;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.IntFunction;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Variant
/*     */   implements StringRepresentable
/*     */ {
/*     */   private static final IntFunction<Variant> BY_ID;
/*     */   public static final Codec<Variant> CODEC;
/* 122 */   LUCY(0, "lucy", true),
/* 123 */   WILD(1, "wild", true),
/* 124 */   GOLD(2, "gold", true),
/* 125 */   CYAN(3, "cyan", true),
/* 126 */   BLUE(4, "blue", false);
/*     */   static {
/* 128 */     BY_ID = ByIdMap.continuous(Variant::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */     
/* 130 */     CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */   }
/*     */   private final int id;
/*     */   private final String name;
/*     */   private final boolean common;
/*     */   
/*     */   Variant(int $$0, String $$1, boolean $$2) {
/* 137 */     this.id = $$0;
/* 138 */     this.name = $$1;
/* 139 */     this.common = $$2;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 143 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 147 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 152 */     return this.name;
/*     */   }
/*     */   
/*     */   public static Variant byId(int $$0) {
/* 156 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   public static Variant getCommonSpawnVariant(RandomSource $$0) {
/* 160 */     return getSpawnVariant($$0, true);
/*     */   }
/*     */   
/*     */   public static Variant getRareSpawnVariant(RandomSource $$0) {
/* 164 */     return getSpawnVariant($$0, false);
/*     */   }
/*     */   
/*     */   private static Variant getSpawnVariant(RandomSource $$0, boolean $$1) {
/* 168 */     Variant[] $$2 = (Variant[])Arrays.<Variant>stream(values()).filter($$1 -> ($$1.common == $$0)).toArray($$0 -> new Variant[$$0]);
/* 169 */     return (Variant)Util.getRandom((Object[])$$2, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\axolotl\Axolotl$Variant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */