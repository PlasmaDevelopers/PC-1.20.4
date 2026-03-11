/*    */ package net.minecraft.world.level.dimension.end;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.SpikeFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
/*    */ 
/*    */ public enum DragonRespawnAnimation {
/* 18 */   START
/*    */   {
/*    */     public void tick(ServerLevel $$0, EndDragonFight $$1, List<EndCrystal> $$2, int $$3, BlockPos $$4) {
/* 21 */       BlockPos $$5 = new BlockPos(0, 128, 0);
/* 22 */       for (EndCrystal $$6 : $$2) {
/* 23 */         $$6.setBeamTarget($$5);
/*    */       }
/* 25 */       $$1.setRespawnStage(PREPARING_TO_SUMMON_PILLARS);
/*    */     }
/*    */   },
/* 28 */   PREPARING_TO_SUMMON_PILLARS
/*    */   {
/*    */     public void tick(ServerLevel $$0, EndDragonFight $$1, List<EndCrystal> $$2, int $$3, BlockPos $$4) {
/* 31 */       if ($$3 < 100) {
/* 32 */         if ($$3 == 0 || $$3 == 50 || $$3 == 51 || $$3 == 52 || $$3 >= 95) {
/* 33 */           $$0.levelEvent(3001, new BlockPos(0, 128, 0), 0);
/*    */         }
/*    */       } else {
/* 36 */         $$1.setRespawnStage(SUMMONING_PILLARS);
/*    */       } 
/*    */     }
/*    */   },
/* 40 */   SUMMONING_PILLARS
/*    */   {
/*    */     public void tick(ServerLevel $$0, EndDragonFight $$1, List<EndCrystal> $$2, int $$3, BlockPos $$4) {
/* 43 */       int $$5 = 40;
/* 44 */       boolean $$6 = ($$3 % 40 == 0);
/* 45 */       boolean $$7 = ($$3 % 40 == 39);
/* 46 */       if ($$6 || $$7) {
/* 47 */         List<SpikeFeature.EndSpike> $$8 = SpikeFeature.getSpikesForLevel((WorldGenLevel)$$0);
/* 48 */         int $$9 = $$3 / 40;
/* 49 */         if ($$9 < $$8.size()) {
/* 50 */           SpikeFeature.EndSpike $$10 = $$8.get($$9);
/*    */           
/* 52 */           if ($$6) {
/* 53 */             for (EndCrystal $$11 : $$2) {
/* 54 */               $$11.setBeamTarget(new BlockPos($$10.getCenterX(), $$10.getHeight() + 1, $$10.getCenterZ()));
/*    */             }
/*    */           } else {
/* 57 */             int $$12 = 10;
/* 58 */             for (BlockPos $$13 : BlockPos.betweenClosed(new BlockPos($$10
/* 59 */                   .getCenterX() - 10, $$10.getHeight() - 10, $$10.getCenterZ() - 10), new BlockPos($$10
/* 60 */                   .getCenterX() + 10, $$10.getHeight() + 10, $$10.getCenterZ() + 10)))
/*    */             {
/* 62 */               $$0.removeBlock($$13, false);
/*    */             }
/* 64 */             $$0.explode(null, ($$10.getCenterX() + 0.5F), $$10.getHeight(), ($$10.getCenterZ() + 0.5F), 5.0F, Level.ExplosionInteraction.BLOCK);
/*    */             
/* 66 */             SpikeConfiguration $$14 = new SpikeConfiguration(true, (List)ImmutableList.of($$10), new BlockPos(0, 128, 0));
/* 67 */             Feature.END_SPIKE.place((FeatureConfiguration)$$14, (WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), RandomSource.create(), new BlockPos($$10.getCenterX(), 45, $$10.getCenterZ()));
/*    */           } 
/* 69 */         } else if ($$6) {
/* 70 */           $$1.setRespawnStage(SUMMONING_DRAGON);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   },
/* 75 */   SUMMONING_DRAGON
/*    */   {
/*    */     public void tick(ServerLevel $$0, EndDragonFight $$1, List<EndCrystal> $$2, int $$3, BlockPos $$4) {
/* 78 */       if ($$3 >= 100) {
/* 79 */         $$1.setRespawnStage(END);
/* 80 */         $$1.resetSpikeCrystals();
/* 81 */         for (EndCrystal $$5 : $$2) {
/* 82 */           $$5.setBeamTarget(null);
/* 83 */           $$0.explode((Entity)$$5, $$5.getX(), $$5.getY(), $$5.getZ(), 6.0F, Level.ExplosionInteraction.NONE);
/* 84 */           $$5.discard();
/*    */         } 
/* 86 */       } else if ($$3 >= 80) {
/* 87 */         $$0.levelEvent(3001, new BlockPos(0, 128, 0), 0);
/* 88 */       } else if ($$3 == 0) {
/* 89 */         for (EndCrystal $$6 : $$2) {
/* 90 */           $$6.setBeamTarget(new BlockPos(0, 128, 0));
/*    */         }
/* 92 */       } else if ($$3 < 5) {
/* 93 */         $$0.levelEvent(3001, new BlockPos(0, 128, 0), 0);
/*    */       } 
/*    */     }
/*    */   },
/* 97 */   END {
/*    */     public void tick(ServerLevel $$0, EndDragonFight $$1, List<EndCrystal> $$2, int $$3, BlockPos $$4) {}
/*    */   };
/*    */   
/*    */   public abstract void tick(ServerLevel paramServerLevel, EndDragonFight paramEndDragonFight, List<EndCrystal> paramList, int paramInt, BlockPos paramBlockPos);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\DragonRespawnAnimation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */