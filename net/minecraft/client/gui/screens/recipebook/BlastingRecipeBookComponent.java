/*    */ package net.minecraft.client.gui.screens.recipebook;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
/*    */ 
/*    */ public class BlastingRecipeBookComponent
/*    */   extends AbstractFurnaceRecipeBookComponent {
/* 10 */   private static final Component FILTER_NAME = (Component)Component.translatable("gui.recipebook.toggleRecipes.blastable");
/*    */ 
/*    */   
/*    */   protected Component getRecipeFilterName() {
/* 14 */     return FILTER_NAME;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Set<Item> getFuelItems() {
/* 19 */     return AbstractFurnaceBlockEntity.getFuel().keySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\BlastingRecipeBookComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */