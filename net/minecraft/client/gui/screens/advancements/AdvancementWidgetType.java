/*    */ package net.minecraft.client.gui.screens.advancements;
/*    */ 
/*    */ import net.minecraft.advancements.AdvancementType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public enum AdvancementWidgetType {
/*  7 */   OBTAINED(new ResourceLocation("advancements/box_obtained"), new ResourceLocation("advancements/task_frame_obtained"), new ResourceLocation("advancements/challenge_frame_obtained"), new ResourceLocation("advancements/goal_frame_obtained")),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 13 */   UNOBTAINED(new ResourceLocation("advancements/box_unobtained"), new ResourceLocation("advancements/task_frame_unobtained"), new ResourceLocation("advancements/challenge_frame_unobtained"), new ResourceLocation("advancements/goal_frame_unobtained"));
/*    */ 
/*    */   
/*    */   private final ResourceLocation boxSprite;
/*    */ 
/*    */   
/*    */   private final ResourceLocation taskFrameSprite;
/*    */   
/*    */   private final ResourceLocation challengeFrameSprite;
/*    */   
/*    */   private final ResourceLocation goalFrameSprite;
/*    */ 
/*    */   
/*    */   AdvancementWidgetType(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/* 27 */     this.boxSprite = $$0;
/* 28 */     this.taskFrameSprite = $$1;
/* 29 */     this.challengeFrameSprite = $$2;
/* 30 */     this.goalFrameSprite = $$3;
/*    */   }
/*    */   
/*    */   public ResourceLocation boxSprite() {
/* 34 */     return this.boxSprite;
/*    */   }
/*    */   
/*    */   public ResourceLocation frameSprite(AdvancementType $$0) {
/* 38 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case TASK: case CHALLENGE: case GOAL: break; }  return 
/*    */ 
/*    */       
/* 41 */       this.goalFrameSprite;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\advancements\AdvancementWidgetType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */