/*    */ package net.minecraft.client.gui.components.toasts;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public class RecipeToast
/*    */   implements Toast {
/* 13 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("toast/recipe");
/*    */   private static final long DISPLAY_TIME = 5000L;
/* 15 */   private static final Component TITLE_TEXT = (Component)Component.translatable("recipe.toast.title");
/* 16 */   private static final Component DESCRIPTION_TEXT = (Component)Component.translatable("recipe.toast.description");
/*    */   
/* 18 */   private final List<RecipeHolder<?>> recipes = Lists.newArrayList();
/*    */   private long lastChanged;
/*    */   private boolean changed;
/*    */   
/*    */   public RecipeToast(RecipeHolder<?> $$0) {
/* 23 */     this.recipes.add($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Toast.Visibility render(GuiGraphics $$0, ToastComponent $$1, long $$2) {
/* 28 */     if (this.changed) {
/* 29 */       this.lastChanged = $$2;
/* 30 */       this.changed = false;
/*    */     } 
/*    */     
/* 33 */     if (this.recipes.isEmpty()) {
/* 34 */       return Toast.Visibility.HIDE;
/*    */     }
/*    */     
/* 37 */     $$0.blitSprite(BACKGROUND_SPRITE, 0, 0, width(), height());
/*    */     
/* 39 */     $$0.drawString(($$1.getMinecraft()).font, TITLE_TEXT, 30, 7, -11534256, false);
/* 40 */     $$0.drawString(($$1.getMinecraft()).font, DESCRIPTION_TEXT, 30, 18, -16777216, false);
/*    */     
/* 42 */     RecipeHolder<?> $$3 = this.recipes.get((int)($$2 / Math.max(1.0D, 5000.0D * $$1.getNotificationDisplayTimeMultiplier() / this.recipes.size()) % this.recipes.size()));
/* 43 */     ItemStack $$4 = $$3.value().getToastSymbol();
/*    */     
/* 45 */     $$0.pose().pushPose();
/* 46 */     $$0.pose().scale(0.6F, 0.6F, 1.0F);
/* 47 */     $$0.renderFakeItem($$4, 3, 3);
/* 48 */     $$0.pose().popPose();
/*    */     
/* 50 */     $$0.renderFakeItem($$3.value().getResultItem(($$1.getMinecraft()).level.registryAccess()), 8, 8);
/* 51 */     return (($$2 - this.lastChanged) >= 5000.0D * $$1.getNotificationDisplayTimeMultiplier()) ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
/*    */   }
/*    */   
/*    */   private void addItem(RecipeHolder<?> $$0) {
/* 55 */     this.recipes.add($$0);
/* 56 */     this.changed = true;
/*    */   }
/*    */   
/*    */   public static void addOrUpdate(ToastComponent $$0, RecipeHolder<?> $$1) {
/* 60 */     RecipeToast $$2 = $$0.<RecipeToast>getToast(RecipeToast.class, NO_TOKEN);
/* 61 */     if ($$2 == null) {
/* 62 */       $$0.addToast(new RecipeToast($$1));
/*    */     } else {
/* 64 */       $$2.addItem($$1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\RecipeToast.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */