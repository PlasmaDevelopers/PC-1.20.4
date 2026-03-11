/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
/*    */ import net.minecraft.client.gui.screens.recipebook.SmokingRecipeBookComponent;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.SmokerMenu;
/*    */ 
/*    */ public class SmokerScreen extends AbstractFurnaceScreen<SmokerMenu> {
/* 10 */   private static final ResourceLocation LIT_PROGRESS_SPRITE = new ResourceLocation("container/smoker/lit_progress");
/* 11 */   private static final ResourceLocation BURN_PROGRESS_SPRITE = new ResourceLocation("container/smoker/burn_progress");
/* 12 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/smoker.png");
/*    */   
/*    */   public SmokerScreen(SmokerMenu $$0, Inventory $$1, Component $$2) {
/* 15 */     super($$0, (AbstractFurnaceRecipeBookComponent)new SmokingRecipeBookComponent(), $$1, $$2, TEXTURE, LIT_PROGRESS_SPRITE, BURN_PROGRESS_SPRITE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\SmokerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */