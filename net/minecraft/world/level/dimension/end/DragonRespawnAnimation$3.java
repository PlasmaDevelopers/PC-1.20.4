/*    */ package net.minecraft.world.level.dimension.end;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.SpikeFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum null
/*    */ {
/*    */   public void tick(ServerLevel $$0, EndDragonFight $$1, List<EndCrystal> $$2, int $$3, BlockPos $$4) {
/* 43 */     int $$5 = 40;
/* 44 */     boolean $$6 = ($$3 % 40 == 0);
/* 45 */     boolean $$7 = ($$3 % 40 == 39);
/* 46 */     if ($$6 || $$7) {
/* 47 */       List<SpikeFeature.EndSpike> $$8 = SpikeFeature.getSpikesForLevel((WorldGenLevel)$$0);
/* 48 */       int $$9 = $$3 / 40;
/* 49 */       if ($$9 < $$8.size()) {
/* 50 */         SpikeFeature.EndSpike $$10 = $$8.get($$9);
/*    */         
/* 52 */         if ($$6) {
/* 53 */           for (EndCrystal $$11 : $$2) {
/* 54 */             $$11.setBeamTarget(new BlockPos($$10.getCenterX(), $$10.getHeight() + 1, $$10.getCenterZ()));
/*    */           }
/*    */         } else {
/* 57 */           int $$12 = 10;
/* 58 */           for (BlockPos $$13 : BlockPos.betweenClosed(new BlockPos($$10
/* 59 */                 .getCenterX() - 10, $$10.getHeight() - 10, $$10.getCenterZ() - 10), new BlockPos($$10
/* 60 */                 .getCenterX() + 10, $$10.getHeight() + 10, $$10.getCenterZ() + 10)))
/*    */           {
/* 62 */             $$0.removeBlock($$13, false);
/*    */           }
/* 64 */           $$0.explode(null, ($$10.getCenterX() + 0.5F), $$10.getHeight(), ($$10.getCenterZ() + 0.5F), 5.0F, Level.ExplosionInteraction.BLOCK);
/*    */           
/* 66 */           SpikeConfiguration $$14 = new SpikeConfiguration(true, (List)ImmutableList.of($$10), new BlockPos(0, 128, 0));
/* 67 */           Feature.END_SPIKE.place((FeatureConfiguration)$$14, (WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), RandomSource.create(), new BlockPos($$10.getCenterX(), 45, $$10.getCenterZ()));
/*    */         } 
/* 69 */       } else if ($$6) {
/* 70 */         $$1.setRespawnStage(SUMMONING_DRAGON);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\DragonRespawnAnimation$3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */