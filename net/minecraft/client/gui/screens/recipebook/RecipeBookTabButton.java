/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.ClientRecipeBook;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.RecipeBookCategories;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.StateSwitchingButton;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class RecipeBookTabButton
/*     */   extends StateSwitchingButton
/*     */ {
/*  20 */   private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("recipe_book/tab"), new ResourceLocation("recipe_book/tab_selected"));
/*     */ 
/*     */   
/*     */   private final RecipeBookCategories category;
/*     */   
/*     */   private static final float ANIMATION_TIME = 15.0F;
/*     */   
/*     */   private float animationTime;
/*     */ 
/*     */   
/*     */   public RecipeBookTabButton(RecipeBookCategories $$0) {
/*  31 */     super(0, 0, 35, 27, false);
/*  32 */     this.category = $$0;
/*     */     
/*  34 */     initTextureValues(SPRITES);
/*     */   }
/*     */   
/*     */   public void startAnimation(Minecraft $$0) {
/*  38 */     ClientRecipeBook $$1 = $$0.player.getRecipeBook();
/*  39 */     List<RecipeCollection> $$2 = $$1.getCollection(this.category);
/*  40 */     if (!($$0.player.containerMenu instanceof RecipeBookMenu)) {
/*     */       return;
/*     */     }
/*  43 */     for (RecipeCollection $$3 : $$2) {
/*  44 */       for (RecipeHolder<?> $$4 : $$3.getRecipes($$1.isFiltering((RecipeBookMenu)$$0.player.containerMenu))) {
/*  45 */         if ($$1.willHighlight($$4)) {
/*  46 */           this.animationTime = 15.0F;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  56 */     if (this.sprites == null) {
/*     */       return;
/*     */     }
/*     */     
/*  60 */     if (this.animationTime > 0.0F) {
/*  61 */       float $$4 = 1.0F + 0.1F * (float)Math.sin((this.animationTime / 15.0F * 3.1415927F));
/*  62 */       $$0.pose().pushPose();
/*  63 */       $$0.pose().translate((getX() + 8), (getY() + 12), 0.0F);
/*  64 */       $$0.pose().scale(1.0F, $$4, 1.0F);
/*  65 */       $$0.pose().translate(-(getX() + 8), -(getY() + 12), 0.0F);
/*     */     } 
/*     */     
/*  68 */     Minecraft $$5 = Minecraft.getInstance();
/*  69 */     RenderSystem.disableDepthTest();
/*     */     
/*  71 */     ResourceLocation $$6 = this.sprites.get(true, this.isStateTriggered);
/*     */     
/*  73 */     int $$7 = getX();
/*  74 */     if (this.isStateTriggered) {
/*  75 */       $$7 -= 2;
/*     */     }
/*     */     
/*  78 */     $$0.blitSprite($$6, $$7, getY(), this.width, this.height);
/*  79 */     RenderSystem.enableDepthTest();
/*     */     
/*  81 */     renderIcon($$0, $$5.getItemRenderer());
/*     */     
/*  83 */     if (this.animationTime > 0.0F) {
/*  84 */       $$0.pose().popPose();
/*  85 */       this.animationTime -= $$3;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderIcon(GuiGraphics $$0, ItemRenderer $$1) {
/*  90 */     List<ItemStack> $$2 = this.category.getIconItems();
/*     */     
/*  92 */     int $$3 = this.isStateTriggered ? -2 : 0;
/*     */     
/*  94 */     if ($$2.size() == 1) {
/*  95 */       $$0.renderFakeItem($$2.get(0), getX() + 9 + $$3, getY() + 5);
/*  96 */     } else if ($$2.size() == 2) {
/*  97 */       $$0.renderFakeItem($$2.get(0), getX() + 3 + $$3, getY() + 5);
/*  98 */       $$0.renderFakeItem($$2.get(1), getX() + 14 + $$3, getY() + 5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RecipeBookCategories getCategory() {
/* 103 */     return this.category;
/*     */   }
/*     */   
/*     */   public boolean updateVisibility(ClientRecipeBook $$0) {
/* 107 */     List<RecipeCollection> $$1 = $$0.getCollection(this.category);
/* 108 */     this.visible = false;
/*     */     
/* 110 */     if ($$1 != null) {
/* 111 */       for (RecipeCollection $$2 : $$1) {
/* 112 */         if ($$2.hasKnownRecipes() && $$2.hasFitting()) {
/* 113 */           this.visible = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 119 */     return this.visible;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\RecipeBookTabButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */