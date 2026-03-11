/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import net.minecraft.Util;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class LogTestReporter implements TestReporter {
/*  8 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */   
/*    */   public void onTestFailed(GameTestInfo $$0) {
/* 12 */     String $$1 = $$0.getStructureBlockPos().toShortString();
/* 13 */     if ($$0.isRequired()) {
/* 14 */       LOGGER.error("{} failed at {}! {}", new Object[] { $$0.getTestName(), $$1, Util.describeError($$0.getError()) });
/*    */     } else {
/* 16 */       LOGGER.warn("(optional) {} failed at {}. {}", new Object[] { $$0.getTestName(), $$1, Util.describeError($$0.getError()) });
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onTestSuccess(GameTestInfo $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\LogTestReporter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */