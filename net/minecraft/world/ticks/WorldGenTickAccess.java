/*    */ package net.minecraft.world.ticks;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class WorldGenTickAccess<T>
/*    */   implements LevelTickAccess<T> {
/*    */   private final Function<BlockPos, TickContainerAccess<T>> containerGetter;
/*    */   
/*    */   public WorldGenTickAccess(Function<BlockPos, TickContainerAccess<T>> $$0) {
/* 11 */     this.containerGetter = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasScheduledTick(BlockPos $$0, T $$1) {
/* 16 */     return ((TickContainerAccess<T>)this.containerGetter.apply($$0)).hasScheduledTick($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void schedule(ScheduledTick<T> $$0) {
/* 21 */     ((TickContainerAccess<T>)this.containerGetter.apply($$0.pos())).schedule($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean willTickThisTick(BlockPos $$0, T $$1) {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int count() {
/* 32 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\WorldGenTickAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */