/*     */ package net.minecraft.client.gui.screens.recipebook;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class RecipeButton
/*     */   extends AbstractWidget
/*     */ {
/*  23 */   private static final ResourceLocation SLOT_MANY_CRAFTABLE_SPRITE = new ResourceLocation("recipe_book/slot_many_craftable");
/*  24 */   private static final ResourceLocation SLOT_CRAFTABLE_SPRITE = new ResourceLocation("recipe_book/slot_craftable");
/*  25 */   private static final ResourceLocation SLOT_MANY_UNCRAFTABLE_SPRITE = new ResourceLocation("recipe_book/slot_many_uncraftable");
/*  26 */   private static final ResourceLocation SLOT_UNCRAFTABLE_SPRITE = new ResourceLocation("recipe_book/slot_uncraftable");
/*     */   private static final float ANIMATION_TIME = 15.0F;
/*     */   private static final int BACKGROUND_SIZE = 25;
/*     */   public static final int TICKS_TO_SWAP = 30;
/*  30 */   private static final Component MORE_RECIPES_TOOLTIP = (Component)Component.translatable("gui.recipebook.moreRecipes");
/*     */   
/*     */   private RecipeBookMenu<?> menu;
/*     */   
/*     */   private RecipeBook book;
/*     */   private RecipeCollection collection;
/*     */   private float time;
/*     */   private float animationTime;
/*     */   private int currentIndex;
/*     */   
/*     */   public RecipeButton() {
/*  41 */     super(0, 0, 25, 25, CommonComponents.EMPTY);
/*     */   }
/*     */   
/*     */   public void init(RecipeCollection $$0, RecipeBookPage $$1) {
/*  45 */     this.collection = $$0;
/*  46 */     this.menu = (RecipeBookMenu)($$1.getMinecraft()).player.containerMenu;
/*  47 */     this.book = $$1.getRecipeBook();
/*     */     
/*  49 */     List<RecipeHolder<?>> $$2 = $$0.getRecipes(this.book.isFiltering(this.menu));
/*  50 */     for (RecipeHolder<?> $$3 : $$2) {
/*  51 */       if (this.book.willHighlight($$3)) {
/*  52 */         $$1.recipesShown($$2);
/*  53 */         this.animationTime = 15.0F;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public RecipeCollection getCollection() {
/*  60 */     return this.collection;
/*     */   }
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     ResourceLocation $$7;
/*  65 */     if (!Screen.hasControlDown()) {
/*  66 */       this.time += $$3;
/*     */     }
/*     */ 
/*     */     
/*  70 */     if (this.collection.hasCraftable()) {
/*  71 */       if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
/*  72 */         ResourceLocation $$4 = SLOT_MANY_CRAFTABLE_SPRITE;
/*     */       } else {
/*  74 */         ResourceLocation $$5 = SLOT_CRAFTABLE_SPRITE;
/*     */       }
/*     */     
/*  77 */     } else if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
/*  78 */       ResourceLocation $$6 = SLOT_MANY_UNCRAFTABLE_SPRITE;
/*     */     } else {
/*  80 */       $$7 = SLOT_UNCRAFTABLE_SPRITE;
/*     */     } 
/*     */ 
/*     */     
/*  84 */     boolean $$8 = (this.animationTime > 0.0F);
/*  85 */     if ($$8) {
/*  86 */       float $$9 = 1.0F + 0.1F * (float)Math.sin((this.animationTime / 15.0F * 3.1415927F));
/*     */       
/*  88 */       $$0.pose().pushPose();
/*  89 */       $$0.pose().translate((getX() + 8), (getY() + 12), 0.0F);
/*  90 */       $$0.pose().scale($$9, $$9, 1.0F);
/*  91 */       $$0.pose().translate(-(getX() + 8), -(getY() + 12), 0.0F);
/*     */       
/*  93 */       this.animationTime -= $$3;
/*     */     } 
/*     */     
/*  96 */     $$0.blitSprite($$7, getX(), getY(), this.width, this.height);
/*     */     
/*  98 */     List<RecipeHolder<?>> $$10 = getOrderedRecipes();
/*  99 */     this.currentIndex = Mth.floor(this.time / 30.0F) % $$10.size();
/*     */     
/* 101 */     ItemStack $$11 = ((RecipeHolder)$$10.get(this.currentIndex)).value().getResultItem(this.collection.registryAccess());
/* 102 */     int $$12 = 4;
/* 103 */     if (this.collection.hasSingleResultItem() && getOrderedRecipes().size() > 1) {
/* 104 */       $$0.renderItem($$11, getX() + $$12 + 1, getY() + $$12 + 1, 0, 10);
/* 105 */       $$12--;
/*     */     } 
/*     */     
/* 108 */     $$0.renderFakeItem($$11, getX() + $$12, getY() + $$12);
/*     */     
/* 110 */     if ($$8) {
/* 111 */       $$0.pose().popPose();
/*     */     }
/*     */   }
/*     */   
/*     */   private List<RecipeHolder<?>> getOrderedRecipes() {
/* 116 */     List<RecipeHolder<?>> $$0 = this.collection.getDisplayRecipes(true);
/* 117 */     if (!this.book.isFiltering(this.menu)) {
/* 118 */       $$0.addAll(this.collection.getDisplayRecipes(false));
/*     */     }
/* 120 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean isOnlyOption() {
/* 124 */     return (getOrderedRecipes().size() == 1);
/*     */   }
/*     */   
/*     */   public RecipeHolder<?> getRecipe() {
/* 128 */     List<RecipeHolder<?>> $$0 = getOrderedRecipes();
/* 129 */     return $$0.get(this.currentIndex);
/*     */   }
/*     */   
/*     */   public List<Component> getTooltipText() {
/* 133 */     ItemStack $$0 = ((RecipeHolder)getOrderedRecipes().get(this.currentIndex)).value().getResultItem(this.collection.registryAccess());
/*     */     
/* 135 */     List<Component> $$1 = Lists.newArrayList(Screen.getTooltipFromItem(Minecraft.getInstance(), $$0));
/* 136 */     if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
/* 137 */       $$1.add(MORE_RECIPES_TOOLTIP);
/*     */     }
/*     */     
/* 140 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 145 */     ItemStack $$1 = ((RecipeHolder)getOrderedRecipes().get(this.currentIndex)).value().getResultItem(this.collection.registryAccess());
/*     */     
/* 147 */     $$0.add(NarratedElementType.TITLE, (Component)Component.translatable("narration.recipe", new Object[] { $$1.getHoverName() }));
/* 148 */     if (this.collection.getRecipes(this.book.isFiltering(this.menu)).size() > 1) {
/* 149 */       $$0.add(NarratedElementType.USAGE, new Component[] {
/* 150 */             (Component)Component.translatable("narration.button.usage.hovered"), 
/* 151 */             (Component)Component.translatable("narration.recipe.usage.more")
/*     */           });
/*     */     } else {
/* 154 */       $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.button.usage.hovered"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 161 */     return 25;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidClickButton(int $$0) {
/* 166 */     return ($$0 == 0 || $$0 == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\recipebook\RecipeButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */