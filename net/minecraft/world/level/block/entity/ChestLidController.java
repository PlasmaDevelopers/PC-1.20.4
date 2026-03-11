/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class ChestLidController {
/*    */   private boolean shouldBeOpen;
/*    */   private float openness;
/*    */   private float oOpenness;
/*    */   
/*    */   public void tickLid() {
/* 11 */     this.oOpenness = this.openness;
/*    */     
/* 13 */     float $$0 = 0.1F;
/*    */     
/* 15 */     if (!this.shouldBeOpen && this.openness > 0.0F) {
/* 16 */       this.openness = Math.max(this.openness - 0.1F, 0.0F);
/* 17 */     } else if (this.shouldBeOpen && this.openness < 1.0F) {
/* 18 */       this.openness = Math.min(this.openness + 0.1F, 1.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getOpenness(float $$0) {
/* 23 */     return Mth.lerp($$0, this.oOpenness, this.openness);
/*    */   }
/*    */   
/*    */   public void shouldBeOpen(boolean $$0) {
/* 27 */     this.shouldBeOpen = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ChestLidController.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */