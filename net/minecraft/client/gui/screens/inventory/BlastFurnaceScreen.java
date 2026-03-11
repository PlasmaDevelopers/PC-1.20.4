/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
/*    */ import net.minecraft.client.gui.screens.recipebook.BlastingRecipeBookComponent;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.BlastFurnaceMenu;
/*    */ 
/*    */ public class BlastFurnaceScreen extends AbstractFurnaceScreen<BlastFurnaceMenu> {
/* 10 */   private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/blast_furnace/lit_progress");
/* 11 */   private static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/blast_furnace/burn_progress");
/* 12 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/blast_furnace.png");
/*    */   
/*    */   public BlastFurnaceScreen(BlastFurnaceMenu $$0, Inventory $$1, Component $$2) {
/* 15 */     super($$0, (AbstractFurnaceRecipeBookComponent)new BlastingRecipeBookComponent(), $$1, $$2, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BlastFurnaceScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */