/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
/*     */ import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.ClickType;
/*     */ import net.minecraft.world.inventory.InventoryMenu;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class InventoryScreen extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener {
/*     */   private float xMouse;
/*  29 */   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent(); private float yMouse;
/*     */   private boolean widthTooNarrow;
/*     */   private boolean buttonClicked;
/*     */   
/*     */   public InventoryScreen(Player $$0) {
/*  34 */     super($$0.inventoryMenu, $$0.getInventory(), (Component)Component.translatable("container.crafting"));
/*  35 */     this.titleLabelX = 97;
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerTick() {
/*  40 */     if (this.minecraft.gameMode.hasInfiniteItems()) {
/*  41 */       this.minecraft.setScreen(new CreativeModeInventoryScreen((Player)this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), ((Boolean)this.minecraft.options.operatorItemsTab().get()).booleanValue()));
/*     */       
/*     */       return;
/*     */     } 
/*  45 */     this.recipeBookComponent.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  50 */     if (this.minecraft.gameMode.hasInfiniteItems()) {
/*  51 */       this.minecraft.setScreen(new CreativeModeInventoryScreen((Player)this.minecraft.player, this.minecraft.player.connection.enabledFeatures(), ((Boolean)this.minecraft.options.operatorItemsTab().get()).booleanValue()));
/*     */       
/*     */       return;
/*     */     } 
/*  55 */     super.init();
/*  56 */     this.widthTooNarrow = (this.width < 379);
/*  57 */     this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, (RecipeBookMenu)this.menu);
/*  58 */     this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
/*     */     
/*  60 */     addRenderableWidget((GuiEventListener)new ImageButton(this.leftPos + 104, this.height / 2 - 22, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, $$0 -> {
/*     */             this.recipeBookComponent.toggleVisibility();
/*     */             
/*     */             this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
/*     */             
/*     */             $$0.setPosition(this.leftPos + 104, this.height / 2 - 22);
/*     */             this.buttonClicked = true;
/*     */           }));
/*  68 */     addWidget((GuiEventListener)this.recipeBookComponent);
/*  69 */     setInitialFocus((GuiEventListener)this.recipeBookComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLabels(GuiGraphics $$0, int $$1, int $$2) {
/*  74 */     $$0.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  79 */     if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
/*  80 */       renderBackground($$0, $$1, $$2, $$3);
/*  81 */       this.recipeBookComponent.render($$0, $$1, $$2, $$3);
/*     */     } else {
/*  83 */       super.render($$0, $$1, $$2, $$3);
/*  84 */       this.recipeBookComponent.render($$0, $$1, $$2, $$3);
/*  85 */       this.recipeBookComponent.renderGhostRecipe($$0, this.leftPos, this.topPos, false, $$3);
/*     */     } 
/*     */     
/*  88 */     renderTooltip($$0, $$1, $$2);
/*  89 */     this.recipeBookComponent.renderTooltip($$0, this.leftPos, this.topPos, $$1, $$2);
/*     */     
/*  91 */     this.xMouse = $$1;
/*  92 */     this.yMouse = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  97 */     int $$4 = this.leftPos;
/*  98 */     int $$5 = this.topPos;
/*  99 */     $$0.blit(INVENTORY_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */     
/* 101 */     renderEntityInInventoryFollowsMouse($$0, $$4 + 26, $$5 + 8, $$4 + 75, $$5 + 78, 30, 0.0625F, this.xMouse, this.yMouse, (LivingEntity)this.minecraft.player);
/*     */   }
/*     */   
/*     */   public static void renderEntityInInventoryFollowsMouse(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, float $$6, float $$7, float $$8, LivingEntity $$9) {
/* 105 */     float $$10 = ($$1 + $$3) / 2.0F;
/* 106 */     float $$11 = ($$2 + $$4) / 2.0F;
/*     */     
/* 108 */     $$0.enableScissor($$1, $$2, $$3, $$4);
/*     */     
/* 110 */     float $$12 = (float)Math.atan((($$10 - $$7) / 40.0F));
/* 111 */     float $$13 = (float)Math.atan((($$11 - $$8) / 40.0F));
/*     */     
/* 113 */     Quaternionf $$14 = (new Quaternionf()).rotateZ(3.1415927F);
/* 114 */     Quaternionf $$15 = (new Quaternionf()).rotateX($$13 * 20.0F * 0.017453292F);
/* 115 */     $$14.mul((Quaternionfc)$$15);
/*     */     
/* 117 */     float $$16 = $$9.yBodyRot;
/* 118 */     float $$17 = $$9.getYRot();
/* 119 */     float $$18 = $$9.getXRot();
/* 120 */     float $$19 = $$9.yHeadRotO;
/* 121 */     float $$20 = $$9.yHeadRot;
/*     */     
/* 123 */     $$9.yBodyRot = 180.0F + $$12 * 20.0F;
/* 124 */     $$9.setYRot(180.0F + $$12 * 40.0F);
/* 125 */     $$9.setXRot(-$$13 * 20.0F);
/* 126 */     $$9.yHeadRot = $$9.getYRot();
/* 127 */     $$9.yHeadRotO = $$9.getYRot();
/*     */     
/* 129 */     Vector3f $$21 = new Vector3f(0.0F, $$9.getBbHeight() / 2.0F + $$6, 0.0F);
/* 130 */     renderEntityInInventory($$0, $$10, $$11, $$5, $$21, $$14, $$15, $$9);
/*     */     
/* 132 */     $$9.yBodyRot = $$16;
/* 133 */     $$9.setYRot($$17);
/* 134 */     $$9.setXRot($$18);
/* 135 */     $$9.yHeadRotO = $$19;
/* 136 */     $$9.yHeadRot = $$20;
/*     */     
/* 138 */     $$0.disableScissor();
/*     */   }
/*     */   
/*     */   public static void renderEntityInInventory(GuiGraphics $$0, float $$1, float $$2, int $$3, Vector3f $$4, Quaternionf $$5, @Nullable Quaternionf $$6, LivingEntity $$7) {
/* 142 */     $$0.pose().pushPose();
/* 143 */     $$0.pose().translate($$1, $$2, 50.0D);
/* 144 */     $$0.pose().mulPoseMatrix((new Matrix4f()).scaling($$3, $$3, -$$3));
/* 145 */     $$0.pose().translate($$4.x, $$4.y, $$4.z);
/* 146 */     $$0.pose().mulPose($$5);
/*     */     
/* 148 */     Lighting.setupForEntityInInventory();
/*     */     
/* 150 */     EntityRenderDispatcher $$8 = Minecraft.getInstance().getEntityRenderDispatcher();
/* 151 */     if ($$6 != null) {
/* 152 */       $$6.conjugate();
/* 153 */       $$8.overrideCameraOrientation($$6);
/*     */     } 
/* 155 */     $$8.setRenderShadow(false);
/* 156 */     RenderSystem.runAsFancy(() -> $$0.render((Entity)$$1, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, $$2.pose(), (MultiBufferSource)$$2.bufferSource(), 15728880));
/* 157 */     $$0.flush();
/* 158 */     $$8.setRenderShadow(true);
/*     */     
/* 160 */     $$0.pose().popPose();
/*     */     
/* 162 */     Lighting.setupFor3DItems();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isHovering(int $$0, int $$1, int $$2, int $$3, double $$4, double $$5) {
/* 167 */     return ((!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering($$0, $$1, $$2, $$3, $$4, $$5));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 172 */     if (this.recipeBookComponent.mouseClicked($$0, $$1, $$2)) {
/* 173 */       setFocused((GuiEventListener)this.recipeBookComponent);
/* 174 */       return true;
/*     */     } 
/*     */     
/* 177 */     if (this.widthTooNarrow && this.recipeBookComponent.isVisible()) {
/* 178 */       return false;
/*     */     }
/*     */     
/* 181 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 186 */     if (this.buttonClicked) {
/* 187 */       this.buttonClicked = false;
/* 188 */       return true;
/*     */     } 
/*     */     
/* 191 */     return super.mouseReleased($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4) {
/* 196 */     boolean $$5 = ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + this.imageWidth) || $$1 >= ($$3 + this.imageHeight));
/* 197 */     return (this.recipeBookComponent.hasClickedOutside($$0, $$1, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, $$4) && $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slotClicked(Slot $$0, int $$1, int $$2, ClickType $$3) {
/* 202 */     super.slotClicked($$0, $$1, $$2, $$3);
/*     */     
/* 204 */     this.recipeBookComponent.slotClicked($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recipesUpdated() {
/* 209 */     this.recipeBookComponent.recipesUpdated();
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeBookComponent getRecipeBookComponent() {
/* 214 */     return this.recipeBookComponent;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\InventoryScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */