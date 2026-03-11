/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SteepMaterialCondition
/*     */   extends SurfaceRules.LazyXZCondition
/*     */ {
/*     */   SteepMaterialCondition(SurfaceRules.Context $$0) {
/* 187 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean compute() {
/* 192 */     int $$0 = this.context.blockX & 0xF;
/* 193 */     int $$1 = this.context.blockZ & 0xF;
/*     */     
/* 195 */     int $$2 = Math.max($$1 - 1, 0);
/* 196 */     int $$3 = Math.min($$1 + 1, 15);
/*     */     
/* 198 */     ChunkAccess $$4 = this.context.chunk;
/* 199 */     int $$5 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$2);
/* 200 */     int $$6 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$0, $$3);
/*     */     
/* 202 */     if ($$6 >= $$5 + 4) {
/* 203 */       return true;
/*     */     }
/*     */     
/* 206 */     int $$7 = Math.max($$0 - 1, 0);
/* 207 */     int $$8 = Math.min($$0 + 1, 15);
/* 208 */     int $$9 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$7, $$1);
/* 209 */     int $$10 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$8, $$1);
/*     */     
/* 211 */     return ($$9 >= $$10 + 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SurfaceRules$Context$SteepMaterialCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */