/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public interface ContainerLevelAccess
/*    */ {
/* 11 */   public static final ContainerLevelAccess NULL = new ContainerLevelAccess()
/*    */     {
/*    */       public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> $$0) {
/* 14 */         return Optional.empty();
/*    */       }
/*    */     };
/*    */   
/*    */   static ContainerLevelAccess create(final Level level, final BlockPos pos) {
/* 19 */     return new ContainerLevelAccess()
/*    */       {
/*    */         public <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> $$0) {
/* 22 */           return Optional.of($$0.apply(level, pos));
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   <T> Optional<T> evaluate(BiFunction<Level, BlockPos, T> paramBiFunction);
/*    */   
/*    */   default <T> T evaluate(BiFunction<Level, BlockPos, T> $$0, T $$1) {
/* 30 */     return evaluate($$0).orElse($$1);
/*    */   }
/*    */   
/*    */   default void execute(BiConsumer<Level, BlockPos> $$0) {
/* 34 */     evaluate(($$1, $$2) -> {
/*    */           $$0.accept($$1, $$2);
/*    */           return Optional.empty();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ContainerLevelAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */