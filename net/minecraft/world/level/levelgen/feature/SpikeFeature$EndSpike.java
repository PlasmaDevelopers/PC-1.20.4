/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndSpike
/*     */ {
/*     */   public static final Codec<EndSpike> CODEC;
/*     */   private final int centerX;
/*     */   private final int centerZ;
/*     */   private final int radius;
/*     */   private final int height;
/*     */   private final boolean guarded;
/*     */   private final AABB topBoundingBox;
/*     */   
/*     */   static {
/* 120 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("centerX").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("centerZ").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("radius").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("height").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.BOOL.fieldOf("guarded").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, EndSpike::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndSpike(int $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 136 */     this.centerX = $$0;
/* 137 */     this.centerZ = $$1;
/* 138 */     this.radius = $$2;
/* 139 */     this.height = $$3;
/* 140 */     this.guarded = $$4;
/*     */     
/* 142 */     this.topBoundingBox = new AABB(($$0 - $$2), DimensionType.MIN_Y, ($$1 - $$2), ($$0 + $$2), DimensionType.MAX_Y, ($$1 + $$2));
/*     */   }
/*     */   
/*     */   public boolean isCenterWithinChunk(BlockPos $$0) {
/* 146 */     return (SectionPos.blockToSectionCoord($$0.getX()) == SectionPos.blockToSectionCoord(this.centerX) && 
/* 147 */       SectionPos.blockToSectionCoord($$0.getZ()) == SectionPos.blockToSectionCoord(this.centerZ));
/*     */   }
/*     */   
/*     */   public int getCenterX() {
/* 151 */     return this.centerX;
/*     */   }
/*     */   
/*     */   public int getCenterZ() {
/* 155 */     return this.centerZ;
/*     */   }
/*     */   
/*     */   public int getRadius() {
/* 159 */     return this.radius;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 163 */     return this.height;
/*     */   }
/*     */   
/*     */   public boolean isGuarded() {
/* 167 */     return this.guarded;
/*     */   }
/*     */   
/*     */   public AABB getTopBoundingBox() {
/* 171 */     return this.topBoundingBox;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\SpikeFeature$EndSpike.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */