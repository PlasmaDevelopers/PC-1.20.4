/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.function.IntFunction;
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
/*     */ public enum Gene
/*     */   implements StringRepresentable
/*     */ {
/* 301 */   NORMAL(0, "normal", false),
/* 302 */   LAZY(1, "lazy", false),
/* 303 */   WORRIED(2, "worried", false),
/* 304 */   PLAYFUL(3, "playful", false),
/* 305 */   BROWN(4, "brown", true),
/* 306 */   WEAK(5, "weak", true),
/* 307 */   AGGRESSIVE(6, "aggressive", false); public static final StringRepresentable.EnumCodec<Gene> CODEC;
/*     */   static {
/* 309 */     CODEC = StringRepresentable.fromEnum(Gene::values);
/*     */     
/* 311 */     BY_ID = ByIdMap.continuous(Gene::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */   }
/*     */   private static final IntFunction<Gene> BY_ID; private static final int MAX_GENE = 6;
/*     */   private final int id;
/*     */   private final String name;
/*     */   private final boolean isRecessive;
/*     */   
/*     */   Gene(int $$0, String $$1, boolean $$2) {
/* 319 */     this.id = $$0;
/* 320 */     this.name = $$1;
/* 321 */     this.isRecessive = $$2;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 325 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 330 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isRecessive() {
/* 334 */     return this.isRecessive;
/*     */   }
/*     */   
/*     */   static Gene getVariantFromGenes(Gene $$0, Gene $$1) {
/* 338 */     if ($$0.isRecessive()) {
/* 339 */       if ($$0 == $$1) {
/* 340 */         return $$0;
/*     */       }
/* 342 */       return NORMAL;
/*     */     } 
/*     */ 
/*     */     
/* 346 */     return $$0;
/*     */   }
/*     */   
/*     */   public static Gene byId(int $$0) {
/* 350 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   public static Gene byName(String $$0) {
/* 354 */     return (Gene)CODEC.byName($$0, NORMAL);
/*     */   }
/*     */   
/*     */   public static Gene getRandom(RandomSource $$0) {
/* 358 */     int $$1 = $$0.nextInt(16);
/* 359 */     if ($$1 == 0) {
/* 360 */       return LAZY;
/*     */     }
/* 362 */     if ($$1 == 1) {
/* 363 */       return WORRIED;
/*     */     }
/* 365 */     if ($$1 == 2) {
/* 366 */       return PLAYFUL;
/*     */     }
/* 368 */     if ($$1 == 4) {
/* 369 */       return AGGRESSIVE;
/*     */     }
/* 371 */     if ($$1 < 9) {
/* 372 */       return WEAK;
/*     */     }
/* 374 */     if ($$1 < 11) {
/* 375 */       return BROWN;
/*     */     }
/*     */     
/* 378 */     return NORMAL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Panda$Gene.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */