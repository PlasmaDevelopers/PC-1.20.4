/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import net.minecraft.client.gui.components.toasts.Toast;
/*    */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class PunchTreeTutorialStepInstance
/*    */   implements TutorialStepInstance {
/*    */   private static final int HINT_DELAY = 600;
/* 17 */   private static final Component TITLE = (Component)Component.translatable("tutorial.punch_tree.title");
/* 18 */   private static final Component DESCRIPTION = (Component)Component.translatable("tutorial.punch_tree.description", new Object[] { Tutorial.key("attack") });
/*    */   
/*    */   private final Tutorial tutorial;
/*    */   private TutorialToast toast;
/*    */   private int timeWaiting;
/*    */   private int resetCount;
/*    */   
/*    */   public PunchTreeTutorialStepInstance(Tutorial $$0) {
/* 26 */     this.tutorial = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 31 */     this.timeWaiting++;
/*    */     
/* 33 */     if (!this.tutorial.isSurvival()) {
/* 34 */       this.tutorial.setStep(TutorialSteps.NONE);
/*    */       
/*    */       return;
/*    */     } 
/* 38 */     if (this.timeWaiting == 1) {
/* 39 */       LocalPlayer $$0 = (this.tutorial.getMinecraft()).player;
/* 40 */       if ($$0 != null) {
/* 41 */         if ($$0.getInventory().contains(ItemTags.LOGS)) {
/* 42 */           this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
/*    */           return;
/*    */         } 
/* 45 */         if (FindTreeTutorialStepInstance.hasPunchedTreesPreviously($$0)) {
/* 46 */           this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/* 52 */     if ((this.timeWaiting >= 600 || this.resetCount > 3) && 
/* 53 */       this.toast == null) {
/* 54 */       this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, true);
/* 55 */       this.tutorial.getMinecraft().getToasts().addToast((Toast)this.toast);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 62 */     if (this.toast != null) {
/* 63 */       this.toast.hide();
/* 64 */       this.toast = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDestroyBlock(ClientLevel $$0, BlockPos $$1, BlockState $$2, float $$3) {
/* 70 */     boolean $$4 = $$2.is(BlockTags.LOGS);
/* 71 */     if ($$4 && $$3 > 0.0F) {
/* 72 */       if (this.toast != null) {
/* 73 */         this.toast.updateProgress($$3);
/*    */       }
/* 75 */       if ($$3 >= 1.0F) {
/* 76 */         this.tutorial.setStep(TutorialSteps.OPEN_INVENTORY);
/*    */       }
/* 78 */     } else if (this.toast != null) {
/* 79 */       this.toast.updateProgress(0.0F);
/* 80 */     } else if ($$4) {
/* 81 */       this.resetCount++;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onGetItem(ItemStack $$0) {
/* 87 */     if ($$0.is(ItemTags.LOGS)) {
/* 88 */       this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
/*    */       return;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\PunchTreeTutorialStepInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */