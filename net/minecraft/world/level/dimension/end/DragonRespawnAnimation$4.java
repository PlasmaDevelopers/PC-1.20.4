/*    */ package net.minecraft.world.level.dimension.end;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*    */ import net.minecraft.world.level.Level;
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
/* 78 */     if ($$3 >= 100) {
/* 79 */       $$1.setRespawnStage(END);
/* 80 */       $$1.resetSpikeCrystals();
/* 81 */       for (EndCrystal $$5 : $$2) {
/* 82 */         $$5.setBeamTarget(null);
/* 83 */         $$0.explode((Entity)$$5, $$5.getX(), $$5.getY(), $$5.getZ(), 6.0F, Level.ExplosionInteraction.NONE);
/* 84 */         $$5.discard();
/*    */       } 
/* 86 */     } else if ($$3 >= 80) {
/* 87 */       $$0.levelEvent(3001, new BlockPos(0, 128, 0), 0);
/* 88 */     } else if ($$3 == 0) {
/* 89 */       for (EndCrystal $$6 : $$2) {
/* 90 */         $$6.setBeamTarget(new BlockPos(0, 128, 0));
/*    */       }
/* 92 */     } else if ($$3 < 5) {
/* 93 */       $$0.levelEvent(3001, new BlockPos(0, 128, 0), 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\DragonRespawnAnimation$4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */