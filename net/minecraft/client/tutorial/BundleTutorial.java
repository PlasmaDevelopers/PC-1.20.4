/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.world.inventory.ClickAction;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ 
/*    */ public class BundleTutorial
/*    */ {
/*    */   private final Tutorial tutorial;
/*    */   private final Options options;
/*    */   @Nullable
/*    */   private TutorialToast toast;
/*    */   
/*    */   public BundleTutorial(Tutorial $$0, Options $$1) {
/* 21 */     this.tutorial = $$0;
/* 22 */     this.options = $$1;
/*    */   }
/*    */   
/*    */   private void showToast() {
/* 26 */     if (this.toast != null) {
/* 27 */       this.tutorial.removeTimedToast(this.toast);
/*    */     }
/* 29 */     MutableComponent mutableComponent1 = Component.translatable("tutorial.bundleInsert.title");
/* 30 */     MutableComponent mutableComponent2 = Component.translatable("tutorial.bundleInsert.description");
/* 31 */     this.toast = new TutorialToast(TutorialToast.Icons.RIGHT_CLICK, (Component)mutableComponent1, (Component)mutableComponent2, true);
/* 32 */     this.tutorial.addTimedToast(this.toast, 160);
/*    */   }
/*    */   
/*    */   private void clearToast() {
/* 36 */     if (this.toast != null) {
/* 37 */       this.tutorial.removeTimedToast(this.toast);
/* 38 */       this.toast = null;
/*    */     } 
/* 40 */     if (!this.options.hideBundleTutorial) {
/* 41 */       this.options.hideBundleTutorial = true;
/* 42 */       this.options.save();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onInventoryAction(ItemStack $$0, ItemStack $$1, ClickAction $$2) {
/* 47 */     if (this.options.hideBundleTutorial) {
/*    */       return;
/*    */     }
/* 50 */     if (!$$0.isEmpty() && $$1.is(Items.BUNDLE)) {
/* 51 */       if ($$2 == ClickAction.PRIMARY) {
/* 52 */         showToast();
/* 53 */       } else if ($$2 == ClickAction.SECONDARY) {
/* 54 */         clearToast();
/*    */       } 
/* 56 */     } else if ($$0.is(Items.BUNDLE) && !$$1.isEmpty() && $$2 == ClickAction.SECONDARY) {
/* 57 */       clearToast();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\BundleTutorial.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */