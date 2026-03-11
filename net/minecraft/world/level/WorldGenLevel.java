/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public interface WorldGenLevel
/*    */   extends ServerLevelAccessor {
/*    */   long getSeed();
/*    */   
/*    */   default boolean ensureCanWrite(BlockPos $$0) {
/* 12 */     return true;
/*    */   }
/*    */   
/*    */   default void setCurrentlyGenerating(@Nullable Supplier<String> $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\WorldGenLevel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */