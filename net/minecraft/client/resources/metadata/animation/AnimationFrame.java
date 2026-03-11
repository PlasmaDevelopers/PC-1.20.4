/*    */ package net.minecraft.client.resources.metadata.animation;
/*    */ 
/*    */ public class AnimationFrame
/*    */ {
/*    */   public static final int UNKNOWN_FRAME_TIME = -1;
/*    */   private final int index;
/*    */   private final int time;
/*    */   
/*    */   public AnimationFrame(int $$0) {
/* 10 */     this($$0, -1);
/*    */   }
/*    */   
/*    */   public AnimationFrame(int $$0, int $$1) {
/* 14 */     this.index = $$0;
/* 15 */     this.time = $$1;
/*    */   }
/*    */   
/*    */   public int getTime(int $$0) {
/* 19 */     return (this.time == -1) ? $$0 : this.time;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 23 */     return this.index;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\animation\AnimationFrame.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */