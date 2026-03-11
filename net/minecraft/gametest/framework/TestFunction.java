/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TestFunction
/*    */ {
/*    */   private final String batchName;
/*    */   private final String testName;
/*    */   private final String structureName;
/*    */   private final boolean required;
/*    */   private final int maxAttempts;
/*    */   private final int requiredSuccesses;
/*    */   private final Consumer<GameTestHelper> function;
/*    */   private final int maxTicks;
/*    */   private final long setupTicks;
/*    */   private final Rotation rotation;
/*    */   
/*    */   public TestFunction(String $$0, String $$1, String $$2, int $$3, long $$4, boolean $$5, Consumer<GameTestHelper> $$6) {
/* 24 */     this($$0, $$1, $$2, Rotation.NONE, $$3, $$4, $$5, 1, 1, $$6);
/*    */   }
/*    */   
/*    */   public TestFunction(String $$0, String $$1, String $$2, Rotation $$3, int $$4, long $$5, boolean $$6, Consumer<GameTestHelper> $$7) {
/* 28 */     this($$0, $$1, $$2, $$3, $$4, $$5, $$6, 1, 1, $$7);
/*    */   }
/*    */   
/*    */   public TestFunction(String $$0, String $$1, String $$2, Rotation $$3, int $$4, long $$5, boolean $$6, int $$7, int $$8, Consumer<GameTestHelper> $$9) {
/* 32 */     this.batchName = $$0;
/* 33 */     this.testName = $$1;
/* 34 */     this.structureName = $$2;
/* 35 */     this.rotation = $$3;
/* 36 */     this.maxTicks = $$4;
/* 37 */     this.required = $$6;
/* 38 */     this.requiredSuccesses = $$7;
/* 39 */     this.maxAttempts = $$8;
/* 40 */     this.function = $$9;
/* 41 */     this.setupTicks = $$5;
/*    */   }
/*    */   
/*    */   public void run(GameTestHelper $$0) {
/* 45 */     this.function.accept($$0);
/*    */   }
/*    */   
/*    */   public String getTestName() {
/* 49 */     return this.testName;
/*    */   }
/*    */   
/*    */   public String getStructureName() {
/* 53 */     return this.structureName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     return this.testName;
/*    */   }
/*    */   
/*    */   public int getMaxTicks() {
/* 62 */     return this.maxTicks;
/*    */   }
/*    */   
/*    */   public boolean isRequired() {
/* 66 */     return this.required;
/*    */   }
/*    */   
/*    */   public String getBatchName() {
/* 70 */     return this.batchName;
/*    */   }
/*    */   
/*    */   public long getSetupTicks() {
/* 74 */     return this.setupTicks;
/*    */   }
/*    */   
/*    */   public Rotation getRotation() {
/* 78 */     return this.rotation;
/*    */   }
/*    */   
/*    */   public boolean isFlaky() {
/* 82 */     return (this.maxAttempts > 1);
/*    */   }
/*    */   
/*    */   public int getMaxAttempts() {
/* 86 */     return this.maxAttempts;
/*    */   }
/*    */   
/*    */   public int getRequiredSuccesses() {
/* 90 */     return this.requiredSuccesses;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\TestFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */