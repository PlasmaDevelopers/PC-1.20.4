/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
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
/*    */ class null
/*    */   implements ContainerLevelAccess
/*    */ {
/*    */   public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> $$0) {
/* 22 */     return Optional.of($$0.apply(level, pos));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ContainerLevelAccess$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */