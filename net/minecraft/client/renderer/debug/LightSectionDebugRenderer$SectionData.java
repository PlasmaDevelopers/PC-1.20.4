/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.lighting.LayerLightSectionStorage;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
/*     */ import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SectionData
/*     */ {
/*     */   final DiscreteVoxelShape lightAndBlocksShape;
/*     */   final DiscreteVoxelShape lightShape;
/*     */   final SectionPos minPos;
/*     */   
/*     */   SectionData(LevelLightEngine $$0, SectionPos $$1, int $$2, LightLayer $$3) {
/* 149 */     int $$4 = $$2 * 2 + 1;
/*     */     
/* 151 */     this.lightAndBlocksShape = (DiscreteVoxelShape)new BitSetDiscreteVoxelShape($$4, $$4, $$4);
/* 152 */     this.lightShape = (DiscreteVoxelShape)new BitSetDiscreteVoxelShape($$4, $$4, $$4);
/*     */     
/* 154 */     for (int $$5 = 0; $$5 < $$4; $$5++) {
/* 155 */       for (int $$6 = 0; $$6 < $$4; $$6++) {
/* 156 */         for (int $$7 = 0; $$7 < $$4; $$7++) {
/* 157 */           SectionPos $$8 = SectionPos.of($$1.x() + $$7 - $$2, $$1.y() + $$6 - $$2, $$1.z() + $$5 - $$2);
/* 158 */           LayerLightSectionStorage.SectionType $$9 = $$0.getDebugSectionType($$3, $$8);
/* 159 */           if ($$9 == LayerLightSectionStorage.SectionType.LIGHT_AND_DATA) {
/* 160 */             this.lightAndBlocksShape.fill($$7, $$6, $$5);
/* 161 */             this.lightShape.fill($$7, $$6, $$5);
/* 162 */           } else if ($$9 == LayerLightSectionStorage.SectionType.LIGHT_ONLY) {
/* 163 */             this.lightShape.fill($$7, $$6, $$5);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     this.minPos = SectionPos.of($$1.x() - $$2, $$1.y() - $$2, $$1.z() - $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\LightSectionDebugRenderer$SectionData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */