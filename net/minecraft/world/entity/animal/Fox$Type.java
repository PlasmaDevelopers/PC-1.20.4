/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.function.IntFunction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.biome.Biome;
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
/*     */ public enum Type
/*     */   implements StringRepresentable
/*     */ {
/*     */   public static final StringRepresentable.EnumCodec<Type> CODEC;
/*     */   private static final IntFunction<Type> BY_ID;
/* 143 */   RED(0, "red"),
/* 144 */   SNOW(1, "snow");
/*     */   static {
/* 146 */     CODEC = StringRepresentable.fromEnum(Type::values);
/*     */     
/* 148 */     BY_ID = ByIdMap.continuous(Type::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */   }
/*     */   private final int id;
/*     */   private final String name;
/*     */   
/*     */   Type(int $$0, String $$1) {
/* 154 */     this.id = $$0;
/* 155 */     this.name = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 160 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 164 */     return this.id;
/*     */   }
/*     */   
/*     */   public static Type byName(String $$0) {
/* 168 */     return (Type)CODEC.byName($$0, RED);
/*     */   }
/*     */   
/*     */   public static Type byId(int $$0) {
/* 172 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   public static Type byBiome(Holder<Biome> $$0) {
/* 176 */     return $$0.is(BiomeTags.SPAWNS_SNOW_FOXES) ? SNOW : RED;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Fox$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */