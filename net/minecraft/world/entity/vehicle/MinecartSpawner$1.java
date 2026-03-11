/*    */ package net.minecraft.world.entity.vehicle;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BaseSpawner;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends BaseSpawner
/*    */ {
/*    */   public void broadcastEvent(Level $$0, BlockPos $$1, int $$2) {
/* 18 */     $$0.broadcastEntityEvent(MinecartSpawner.this, (byte)$$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartSpawner$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */