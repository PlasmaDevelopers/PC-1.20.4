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
/* 31 */     if ($$3 < 100) {
/* 32 */       if ($$3 == 0 || $$3 == 50 || $$3 == 51 || $$3 == 52 || $$3 >= 95) {
/* 33 */         $$0.levelEvent(3001, new BlockPos(0, 128, 0), 0);
/*    */       }
/*    */     } else {
/* 36 */       $$1.setRespawnStage(SUMMONING_PILLARS);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\end\DragonRespawnAnimation$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */