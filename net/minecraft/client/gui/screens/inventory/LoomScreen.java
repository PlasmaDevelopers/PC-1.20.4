/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.blockentity.BannerRenderer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.LoomMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.BannerItem;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BannerPatterns;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ 
/*     */ public class LoomScreen extends AbstractContainerScreen<LoomMenu> {
/*  40 */   private static final ResourceLocation BANNER_SLOT_SPRITE = new ResourceLocation("container/loom/banner_slot");
/*  41 */   private static final ResourceLocation DYE_SLOT_SPRITE = new ResourceLocation("container/loom/dye_slot");
/*  42 */   private static final ResourceLocation PATTERN_SLOT_SPRITE = new ResourceLocation("container/loom/pattern_slot");
/*  43 */   private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/loom/scroller");
/*  44 */   private static final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation("container/loom/scroller_disabled");
/*  45 */   private static final ResourceLocation PATTERN_SELECTED_SPRITE = new ResourceLocation("container/loom/pattern_selected");
/*  46 */   private static final ResourceLocation PATTERN_HIGHLIGHTED_SPRITE = new ResourceLocation("container/loom/pattern_highlighted");
/*  47 */   private static final ResourceLocation PATTERN_SPRITE = new ResourceLocation("container/loom/pattern");
/*  48 */   private static final ResourceLocation ERROR_SPRITE = new ResourceLocation("container/loom/error");
/*  49 */   private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/loom.png");
/*     */   
/*     */   private static final int PATTERN_COLUMNS = 4;
/*     */   
/*     */   private static final int PATTERN_ROWS = 4;
/*     */   
/*     */   private static final int SCROLLER_WIDTH = 12;
/*     */   private static final int SCROLLER_HEIGHT = 15;
/*     */   private static final int PATTERN_IMAGE_SIZE = 14;
/*     */   private static final int SCROLLER_FULL_HEIGHT = 56;
/*     */   private static final int PATTERNS_X = 60;
/*     */   private static final int PATTERNS_Y = 13;
/*     */   private ModelPart flag;
/*     */   @Nullable
/*     */   private List<Pair<Holder<BannerPattern>, DyeColor>> resultBannerPatterns;
/*  64 */   private ItemStack bannerStack = ItemStack.EMPTY;
/*  65 */   private ItemStack dyeStack = ItemStack.EMPTY;
/*  66 */   private ItemStack patternStack = ItemStack.EMPTY;
/*     */   
/*     */   private boolean displayPatterns;
/*     */   private boolean hasMaxPatterns;
/*     */   private float scrollOffs;
/*     */   private boolean scrolling;
/*     */   private int startRow;
/*     */   
/*     */   public LoomScreen(LoomMenu $$0, Inventory $$1, Component $$2) {
/*  75 */     super($$0, $$1, $$2);
/*  76 */     $$0.registerUpdateListener(this::containerChanged);
/*  77 */     this.titleLabelY -= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  82 */     super.init();
/*  83 */     this.flag = this.minecraft.getEntityModels().bakeLayer(ModelLayers.BANNER).getChild("flag");
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  88 */     super.render($$0, $$1, $$2, $$3);
/*  89 */     renderTooltip($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private int totalRowCount() {
/*  93 */     return Mth.positiveCeilDiv(this.menu.getSelectablePatterns().size(), 4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  98 */     int $$4 = this.leftPos;
/*  99 */     int $$5 = this.topPos;
/* 100 */     $$0.blit(BG_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */     
/* 102 */     Slot $$6 = this.menu.getBannerSlot();
/* 103 */     Slot $$7 = this.menu.getDyeSlot();
/* 104 */     Slot $$8 = this.menu.getPatternSlot();
/* 105 */     Slot $$9 = this.menu.getResultSlot();
/* 106 */     if (!$$6.hasItem()) {
/* 107 */       $$0.blitSprite(BANNER_SLOT_SPRITE, $$4 + $$6.x, $$5 + $$6.y, 16, 16);
/*     */     }
/* 109 */     if (!$$7.hasItem()) {
/* 110 */       $$0.blitSprite(DYE_SLOT_SPRITE, $$4 + $$7.x, $$5 + $$7.y, 16, 16);
/*     */     }
/* 112 */     if (!$$8.hasItem()) {
/* 113 */       $$0.blitSprite(PATTERN_SLOT_SPRITE, $$4 + $$8.x, $$5 + $$8.y, 16, 16);
/*     */     }
/*     */     
/* 116 */     int $$10 = (int)(41.0F * this.scrollOffs);
/* 117 */     ResourceLocation $$11 = this.displayPatterns ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
/* 118 */     $$0.blitSprite($$11, $$4 + 119, $$5 + 13 + $$10, 12, 15);
/*     */     
/* 120 */     Lighting.setupForFlatItems();
/* 121 */     if (this.resultBannerPatterns != null && !this.hasMaxPatterns) {
/* 122 */       $$0.pose().pushPose();
/* 123 */       $$0.pose().translate(($$4 + 139), ($$5 + 52), 0.0F);
/* 124 */       $$0.pose().scale(24.0F, -24.0F, 1.0F);
/* 125 */       $$0.pose().translate(0.5F, 0.5F, 0.5F);
/* 126 */       float $$12 = 0.6666667F;
/* 127 */       $$0.pose().scale(0.6666667F, -0.6666667F, -0.6666667F);
/* 128 */       this.flag.xRot = 0.0F;
/* 129 */       this.flag.y = -32.0F;
/* 130 */       BannerRenderer.renderPatterns($$0.pose(), (MultiBufferSource)$$0.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, this.flag, ModelBakery.BANNER_BASE, true, this.resultBannerPatterns);
/* 131 */       $$0.pose().popPose();
/* 132 */       $$0.flush();
/* 133 */     } else if (this.hasMaxPatterns) {
/* 134 */       $$0.blitSprite(ERROR_SPRITE, $$4 + $$9.x - 5, $$5 + $$9.y - 5, 26, 26);
/*     */     } 
/* 136 */     if (this.displayPatterns) {
/* 137 */       int $$13 = $$4 + 60;
/* 138 */       int $$14 = $$5 + 13;
/*     */       
/* 140 */       List<Holder<BannerPattern>> $$15 = this.menu.getSelectablePatterns();
/*     */       
/* 142 */       for (int $$16 = 0; $$16 < 4; $$16++) {
/* 143 */         for (int $$17 = 0; $$17 < 4; $$17++) {
/* 144 */           ResourceLocation $$25; int $$18 = $$16 + this.startRow;
/* 145 */           int $$19 = $$18 * 4 + $$17;
/* 146 */           if ($$19 >= $$15.size()) {
/*     */             // Byte code: goto -> 663
/*     */           }
/*     */           
/* 150 */           int $$20 = $$13 + $$17 * 14;
/* 151 */           int $$21 = $$14 + $$16 * 14;
/*     */           
/* 153 */           boolean $$22 = ($$2 >= $$20 && $$3 >= $$21 && $$2 < $$20 + 14 && $$3 < $$21 + 14);
/*     */           
/* 155 */           if ($$19 == this.menu.getSelectedBannerPatternIndex()) {
/* 156 */             ResourceLocation $$23 = PATTERN_SELECTED_SPRITE;
/* 157 */           } else if ($$22) {
/* 158 */             ResourceLocation $$24 = PATTERN_HIGHLIGHTED_SPRITE;
/*     */           } else {
/* 160 */             $$25 = PATTERN_SPRITE;
/*     */           } 
/* 162 */           $$0.blitSprite($$25, $$20, $$21, 14, 14);
/* 163 */           renderPattern($$0, $$15.get($$19), $$20, $$21);
/*     */         } 
/*     */       } 
/*     */     } 
/* 167 */     Lighting.setupFor3DItems();
/*     */   }
/*     */   
/*     */   private void renderPattern(GuiGraphics $$0, Holder<BannerPattern> $$1, int $$2, int $$3) {
/* 171 */     CompoundTag $$4 = new CompoundTag();
/*     */ 
/*     */ 
/*     */     
/* 175 */     ListTag $$5 = (new BannerPattern.Builder()).addPattern(BannerPatterns.BASE, DyeColor.GRAY).addPattern($$1, DyeColor.WHITE).toListTag();
/* 176 */     $$4.put("Patterns", (Tag)$$5);
/*     */     
/* 178 */     ItemStack $$6 = new ItemStack((ItemLike)Items.GRAY_BANNER);
/* 179 */     BlockItem.setBlockEntityData($$6, BlockEntityType.BANNER, $$4);
/*     */     
/* 181 */     PoseStack $$7 = new PoseStack();
/* 182 */     $$7.pushPose();
/* 183 */     $$7.translate($$2 + 0.5F, ($$3 + 16), 0.0F);
/* 184 */     $$7.scale(6.0F, -6.0F, 1.0F);
/* 185 */     $$7.translate(0.5F, 0.5F, 0.0F);
/* 186 */     $$7.translate(0.5F, 0.5F, 0.5F);
/* 187 */     float $$8 = 0.6666667F;
/* 188 */     $$7.scale(0.6666667F, -0.6666667F, -0.6666667F);
/*     */     
/* 190 */     this.flag.xRot = 0.0F;
/* 191 */     this.flag.y = -32.0F;
/* 192 */     List<Pair<Holder<BannerPattern>, DyeColor>> $$9 = BannerBlockEntity.createPatterns(DyeColor.GRAY, BannerBlockEntity.getItemPatterns($$6));
/* 193 */     BannerRenderer.renderPatterns($$7, (MultiBufferSource)$$0.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, this.flag, ModelBakery.BANNER_BASE, true, $$9);
/*     */     
/* 195 */     $$7.popPose();
/* 196 */     $$0.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 201 */     this.scrolling = false;
/* 202 */     if (this.displayPatterns) {
/* 203 */       int $$3 = this.leftPos + 60;
/* 204 */       int $$4 = this.topPos + 13;
/*     */       
/* 206 */       for (int $$5 = 0; $$5 < 4; $$5++) {
/* 207 */         for (int $$6 = 0; $$6 < 4; $$6++) {
/* 208 */           double $$7 = $$0 - ($$3 + $$6 * 14);
/* 209 */           double $$8 = $$1 - ($$4 + $$5 * 14);
/* 210 */           int $$9 = $$5 + this.startRow;
/* 211 */           int $$10 = $$9 * 4 + $$6;
/* 212 */           if ($$7 >= 0.0D && $$8 >= 0.0D && $$7 < 14.0D && $$8 < 14.0D && this.menu.clickMenuButton((Player)this.minecraft.player, $$10)) {
/* 213 */             Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0F));
/* 214 */             this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, $$10);
/* 215 */             return true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 220 */       $$3 = this.leftPos + 119;
/* 221 */       $$4 = this.topPos + 9;
/* 222 */       if ($$0 >= $$3 && $$0 < ($$3 + 12) && $$1 >= $$4 && $$1 < ($$4 + 56)) {
/* 223 */         this.scrolling = true;
/*     */       }
/*     */     } 
/*     */     
/* 227 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 232 */     int $$5 = totalRowCount() - 4;
/* 233 */     if (this.scrolling && this.displayPatterns && $$5 > 0) {
/* 234 */       int $$6 = this.topPos + 13;
/* 235 */       int $$7 = $$6 + 56;
/* 236 */       this.scrollOffs = ((float)$$1 - $$6 - 7.5F) / (($$7 - $$6) - 15.0F);
/* 237 */       this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
/* 238 */       this.startRow = Math.max((int)((this.scrollOffs * $$5) + 0.5D), 0);
/* 239 */       return true;
/*     */     } 
/* 241 */     return super.mouseDragged($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 246 */     int $$4 = totalRowCount() - 4;
/* 247 */     if (this.displayPatterns && $$4 > 0) {
/* 248 */       float $$5 = (float)$$3 / $$4;
/* 249 */       this.scrollOffs = Mth.clamp(this.scrollOffs - $$5, 0.0F, 1.0F);
/* 250 */       this.startRow = Math.max((int)(this.scrollOffs * $$4 + 0.5F), 0);
/*     */     } 
/* 252 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4) {
/* 257 */     return ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + this.imageWidth) || $$1 >= ($$3 + this.imageHeight));
/*     */   }
/*     */   
/*     */   private void containerChanged() {
/* 261 */     ItemStack $$0 = this.menu.getResultSlot().getItem();
/* 262 */     if ($$0.isEmpty()) {
/* 263 */       this.resultBannerPatterns = null;
/*     */     } else {
/* 265 */       this.resultBannerPatterns = BannerBlockEntity.createPatterns(((BannerItem)$$0.getItem()).getColor(), BannerBlockEntity.getItemPatterns($$0));
/*     */     } 
/*     */     
/* 268 */     ItemStack $$1 = this.menu.getBannerSlot().getItem();
/* 269 */     ItemStack $$2 = this.menu.getDyeSlot().getItem();
/* 270 */     ItemStack $$3 = this.menu.getPatternSlot().getItem();
/*     */     
/* 272 */     CompoundTag $$4 = BlockItem.getBlockEntityData($$1);
/* 273 */     this.hasMaxPatterns = ($$4 != null && $$4.contains("Patterns", 9) && !$$1.isEmpty() && $$4.getList("Patterns", 10).size() >= 6);
/*     */     
/* 275 */     if (this.hasMaxPatterns) {
/* 276 */       this.resultBannerPatterns = null;
/*     */     }
/*     */     
/* 279 */     if (!ItemStack.matches($$1, this.bannerStack) || !ItemStack.matches($$2, this.dyeStack) || !ItemStack.matches($$3, this.patternStack)) {
/* 280 */       this.displayPatterns = (!$$1.isEmpty() && !$$2.isEmpty() && !this.hasMaxPatterns && !this.menu.getSelectablePatterns().isEmpty());
/*     */     }
/*     */     
/* 283 */     if (this.startRow >= totalRowCount()) {
/* 284 */       this.startRow = 0;
/* 285 */       this.scrollOffs = 0.0F;
/*     */     } 
/* 287 */     this.bannerStack = $$1.copy();
/* 288 */     this.dyeStack = $$2.copy();
/* 289 */     this.patternStack = $$3.copy();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\LoomScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */