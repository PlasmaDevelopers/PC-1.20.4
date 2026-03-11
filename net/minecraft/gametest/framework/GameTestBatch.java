/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameTestBatch
/*    */ {
/*    */   public static final String DEFAULT_BATCH_NAME = "defaultBatch";
/*    */   private final String name;
/*    */   private final Collection<TestFunction> testFunctions;
/*    */   @Nullable
/*    */   private final Consumer<ServerLevel> beforeBatchFunction;
/*    */   @Nullable
/*    */   private final Consumer<ServerLevel> afterBatchFunction;
/*    */   
/*    */   public GameTestBatch(String $$0, Collection<TestFunction> $$1, @Nullable Consumer<ServerLevel> $$2, @Nullable Consumer<ServerLevel> $$3) {
/* 22 */     if ($$1.isEmpty()) {
/* 23 */       throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
/*    */     }
/*    */     
/* 26 */     this.name = $$0;
/* 27 */     this.testFunctions = $$1;
/* 28 */     this.beforeBatchFunction = $$2;
/* 29 */     this.afterBatchFunction = $$3;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 33 */     return this.name;
/*    */   }
/*    */   
/*    */   public Collection<TestFunction> getTestFunctions() {
/* 37 */     return this.testFunctions;
/*    */   }
/*    */   
/*    */   public void runBeforeBatchFunction(ServerLevel $$0) {
/* 41 */     if (this.beforeBatchFunction != null) {
/* 42 */       this.beforeBatchFunction.accept($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void runAfterBatchFunction(ServerLevel $$0) {
/* 47 */     if (this.afterBatchFunction != null)
/* 48 */       this.afterBatchFunction.accept($$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestBatch.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */