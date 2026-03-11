/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class GameTestAssertPosException
/*    */   extends GameTestAssertException {
/*    */   private final BlockPos absolutePos;
/*    */   private final BlockPos relativePos;
/*    */   private final long tick;
/*    */   
/*    */   public GameTestAssertPosException(String $$0, BlockPos $$1, BlockPos $$2, long $$3) {
/* 13 */     super($$0);
/* 14 */     this.absolutePos = $$1;
/* 15 */     this.relativePos = $$2;
/* 16 */     this.tick = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 21 */     String $$0 = "" + this.absolutePos.getX() + "," + this.absolutePos.getX() + "," + this.absolutePos.getY() + " (relative: " + this.absolutePos.getZ() + "," + this.relativePos.getX() + "," + this.relativePos.getY() + ")";
/* 22 */     return super.getMessage() + " at " + super.getMessage() + " (t=" + $$0 + ")";
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getMessageToShowAtBlock() {
/* 27 */     return super.getMessage();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockPos getRelativePos() {
/* 32 */     return this.relativePos;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockPos getAbsolutePos() {
/* 37 */     return this.absolutePos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestAssertPosException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */