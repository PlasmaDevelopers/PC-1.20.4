/*    */ package net.minecraft.client.tutorial;
/*    */ 
/*    */ import net.minecraft.client.gui.components.toasts.Toast;
/*    */ import net.minecraft.client.gui.components.toasts.TutorialToast;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.tags.ItemTags;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class FindTreeTutorialStepInstance
/*    */   implements TutorialStepInstance {
/*    */   private static final int HINT_DELAY = 6000;
/* 22 */   private static final Component TITLE = (Component)Component.translatable("tutorial.find_tree.title");
/* 23 */   private static final Component DESCRIPTION = (Component)Component.translatable("tutorial.find_tree.description");
/*    */   
/*    */   private final Tutorial tutorial;
/*    */   private TutorialToast toast;
/*    */   private int timeWaiting;
/*    */   
/*    */   public FindTreeTutorialStepInstance(Tutorial $$0) {
/* 30 */     this.tutorial = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 35 */     this.timeWaiting++;
/*    */     
/* 37 */     if (!this.tutorial.isSurvival()) {
/* 38 */       this.tutorial.setStep(TutorialSteps.NONE);
/*    */       
/*    */       return;
/*    */     } 
/* 42 */     if (this.timeWaiting == 1) {
/* 43 */       LocalPlayer $$0 = (this.tutorial.getMinecraft()).player;
/* 44 */       if ($$0 != null && (
/* 45 */         hasCollectedTreeItems($$0) || hasPunchedTreesPreviously($$0))) {
/* 46 */         this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     if (this.timeWaiting >= 6000 && 
/* 53 */       this.toast == null) {
/* 54 */       this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, false);
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
/*    */   public void onLookAt(ClientLevel $$0, HitResult $$1) {
/* 70 */     if ($$1.getType() == HitResult.Type.BLOCK) {
/* 71 */       BlockState $$2 = $$0.getBlockState(((BlockHitResult)$$1).getBlockPos());
/* 72 */       if ($$2.is(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)) {
/* 73 */         this.tutorial.setStep(TutorialSteps.PUNCH_TREE);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onGetItem(ItemStack $$0) {
/* 80 */     if ($$0.is(ItemTags.COMPLETES_FIND_TREE_TUTORIAL)) {
/* 81 */       this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
/*    */     }
/*    */   }
/*    */   
/*    */   private static boolean hasCollectedTreeItems(LocalPlayer $$0) {
/* 86 */     return $$0.getInventory().hasAnyMatching($$0 -> $$0.is(ItemTags.COMPLETES_FIND_TREE_TUTORIAL));
/*    */   }
/*    */   
/*    */   public static boolean hasPunchedTreesPreviously(LocalPlayer $$0) {
/* 90 */     for (Holder<Block> $$1 : (Iterable<Holder<Block>>)BuiltInRegistries.BLOCK.getTagOrEmpty(BlockTags.COMPLETES_FIND_TREE_TUTORIAL)) {
/* 91 */       Block $$2 = (Block)$$1.value();
/* 92 */       if ($$0.getStats().getValue(Stats.BLOCK_MINED.get($$2)) > 0) {
/* 93 */         return true;
/*    */       }
/*    */     } 
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\tutorial\FindTreeTutorialStepInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */