/*    */ package net.minecraft.world.level.dimension.end;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
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
/* 21 */     BlockPos $$5 = new BlockPos(0, 128, 0);
/* 22 */     for (EndCrystal $$6 : $$2) {
/* 23 */       $$6.setBeamTarget($$5);
/*    */     }
/* 25 */     $$1.setRespawnStage(PREPARING_TO_SUMMON_PILLARS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\DragonRespawnAnimation$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */