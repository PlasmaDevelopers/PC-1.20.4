/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import net.minecraft.client.gui.components.toasts.Toast;
/*    */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class OpenInventoryTutorialStep
/*    */   implements TutorialStepInstance {
/*    */   private static final int HINT_DELAY = 600;
/* 10 */   private static final Component TITLE = (Component)Component.translatable("tutorial.open_inventory.title");
/* 11 */   private static final Component DESCRIPTION = (Component)Component.translatable("tutorial.open_inventory.description", new Object[] { Tutorial.key("inventory") });
/*    */   
/*    */   private final Tutorial tutorial;
/*    */   private TutorialToast toast;
/*    */   private int timeWaiting;
/*    */   
/*    */   public OpenInventoryTutorialStep(Tutorial $$0) {
/* 18 */     this.tutorial = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 23 */     this.timeWaiting++;
/*    */     
/* 25 */     if (!this.tutorial.isSurvival()) {
/* 26 */       this.tutorial.setStep(TutorialSteps.NONE);
/*    */       
/*    */       return;
/*    */     } 
/* 30 */     if (this.timeWaiting >= 600 && 
/* 31 */       this.toast == null) {
/* 32 */       this.toast = new TutorialToast(TutorialToast.Icons.RECIPE_BOOK, TITLE, DESCRIPTION, false);
/* 33 */       this.tutorial.getMinecraft().getToasts().addToast((Toast)this.toast);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 40 */     if (this.toast != null) {
/* 41 */       this.toast.hide();
/* 42 */       this.toast = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onOpenInventory() {
/* 48 */     this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\OpenInventoryTutorialStep.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */