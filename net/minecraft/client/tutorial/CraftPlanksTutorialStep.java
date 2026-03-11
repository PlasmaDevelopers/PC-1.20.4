/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import net.minecraft.client.gui.components.toasts.Toast;
/*    */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class CraftPlanksTutorialStep
/*    */   implements TutorialStepInstance {
/*    */   private static final int HINT_DELAY = 1200;
/* 18 */   private static final Component CRAFT_TITLE = (Component)Component.translatable("tutorial.craft_planks.title");
/* 19 */   private static final Component CRAFT_DESCRIPTION = (Component)Component.translatable("tutorial.craft_planks.description");
/*    */   
/*    */   private final Tutorial tutorial;
/*    */   private TutorialToast toast;
/*    */   private int timeWaiting;
/*    */   
/*    */   public CraftPlanksTutorialStep(Tutorial $$0) {
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
/* 41 */         if ($$0.getInventory().contains(ItemTags.PLANKS)) {
/* 42 */           this.tutorial.setStep(TutorialSteps.NONE);
/*    */           return;
/*    */         } 
/* 45 */         if (hasCraftedPlanksPreviously($$0, ItemTags.PLANKS)) {
/* 46 */           this.tutorial.setStep(TutorialSteps.NONE);
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/* 52 */     if (this.timeWaiting >= 1200 && 
/* 53 */       this.toast == null) {
/* 54 */       this.toast = new TutorialToast(TutorialToast.Icons.WOODEN_PLANKS, CRAFT_TITLE, CRAFT_DESCRIPTION, false);
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
/*    */   public void onGetItem(ItemStack $$0) {
/* 70 */     if ($$0.is(ItemTags.PLANKS)) {
/* 71 */       this.tutorial.setStep(TutorialSteps.NONE);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean hasCraftedPlanksPreviously(LocalPlayer $$0, TagKey<Item> $$1) {
/* 76 */     for (Holder<Item> $$2 : (Iterable<Holder<Item>>)BuiltInRegistries.ITEM.getTagOrEmpty($$1)) {
/* 77 */       if ($$0.getStats().getValue(Stats.ITEM_CRAFTED.get($$2.value())) > 0) {
/* 78 */         return true;
/*    */       }
/*    */     } 
/* 81 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\CraftPlanksTutorialStep.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */