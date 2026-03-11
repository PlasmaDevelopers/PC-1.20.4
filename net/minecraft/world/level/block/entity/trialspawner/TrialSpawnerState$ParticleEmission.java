/*     */ package net.minecraft.world.level.block.entity.trialspawner;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ interface ParticleEmission
/*     */ {
/*     */   public static final ParticleEmission NONE = ($$0, $$1, $$2) -> {
/*     */     
/*     */     };
/*     */   public static final ParticleEmission SMALL_FLAMES;
/*     */   public static final ParticleEmission FLAMES_AND_SMOKE;
/*     */   public static final ParticleEmission SMOKE_INSIDE_AND_TOP_FACE;
/*     */   
/*     */   static {
/* 165 */     SMALL_FLAMES = (($$0, $$1, $$2) -> {
/*     */         if ($$1.nextInt(2) == 0) {
/*     */           Vec3 $$3 = $$2.getCenter().offsetRandom($$1, 0.9F);
/*     */           addParticle(ParticleTypes.SMALL_FLAME, $$3, $$0);
/*     */         } 
/*     */       });
/* 171 */     FLAMES_AND_SMOKE = (($$0, $$1, $$2) -> {
/*     */         Vec3 $$3 = $$2.getCenter().offsetRandom($$1, 1.0F);
/*     */         addParticle(ParticleTypes.SMOKE, $$3, $$0);
/*     */         addParticle(ParticleTypes.FLAME, $$3, $$0);
/*     */       });
/* 176 */     SMOKE_INSIDE_AND_TOP_FACE = (($$0, $$1, $$2) -> {
/*     */         Vec3 $$3 = $$2.getCenter().offsetRandom($$1, 0.9F);
/*     */         if ($$1.nextInt(3) == 0) {
/*     */           addParticle(ParticleTypes.SMOKE, $$3, $$0);
/*     */         }
/*     */         if ($$0.getGameTime() % 20L == 0L) {
/*     */           Vec3 $$4 = $$2.getCenter().add(0.0D, 0.5D, 0.0D);
/*     */           int $$5 = $$0.getRandom().nextInt(4) + 20;
/*     */           for (int $$6 = 0; $$6 < $$5; $$6++) {
/*     */             addParticle(ParticleTypes.SMOKE, $$4, $$0);
/*     */           }
/*     */         } 
/*     */       });
/*     */   }
/*     */   
/*     */   private static void addParticle(SimpleParticleType $$0, Vec3 $$1, Level $$2) {
/* 192 */     $$2.addParticle((ParticleOptions)$$0, $$1.x(), $$1.y(), $$1.z(), 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   void emit(Level paramLevel, RandomSource paramRandomSource, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\TrialSpawnerState$ParticleEmission.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */