/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import com.mojang.blaze3d.platform.Window;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.BufferUploader;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
/*     */ import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
/*     */ import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
/*     */ import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.GameRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FastColor;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.inventory.tooltip.TooltipComponent;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector2ic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiGraphics
/*     */ {
/*     */   public static final float MAX_GUI_Z = 10000.0F;
/*     */   public static final float MIN_GUI_Z = -10000.0F;
/*     */   private static final int EXTRA_SPACE_AFTER_FIRST_TOOLTIP_LINE = 2;
/*     */   private final Minecraft minecraft;
/*     */   private final PoseStack pose;
/*     */   private final MultiBufferSource.BufferSource bufferSource;
/*  68 */   private final ScissorStack scissorStack = new ScissorStack();
/*     */   
/*     */   private final GuiSpriteManager sprites;
/*     */   private boolean managed;
/*     */   
/*     */   private GuiGraphics(Minecraft $$0, PoseStack $$1, MultiBufferSource.BufferSource $$2) {
/*  74 */     this.minecraft = $$0;
/*  75 */     this.pose = $$1;
/*  76 */     this.bufferSource = $$2;
/*  77 */     this.sprites = $$0.getGuiSprites();
/*     */   }
/*     */   
/*     */   public GuiGraphics(Minecraft $$0, MultiBufferSource.BufferSource $$1) {
/*  81 */     this($$0, new PoseStack(), $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void drawManaged(Runnable $$0) {
/*  88 */     flush();
/*  89 */     this.managed = true;
/*  90 */     $$0.run();
/*  91 */     this.managed = false;
/*  92 */     flush();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private void flushIfUnmanaged() {
/*  97 */     if (!this.managed) {
/*  98 */       flush();
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private void flushIfManaged() {
/* 104 */     if (this.managed) {
/* 105 */       flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public int guiWidth() {
/* 110 */     return this.minecraft.getWindow().getGuiScaledWidth();
/*     */   }
/*     */   
/*     */   public int guiHeight() {
/* 114 */     return this.minecraft.getWindow().getGuiScaledHeight();
/*     */   }
/*     */   
/*     */   public PoseStack pose() {
/* 118 */     return this.pose;
/*     */   }
/*     */   
/*     */   public MultiBufferSource.BufferSource bufferSource() {
/* 122 */     return this.bufferSource;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 128 */     RenderSystem.disableDepthTest();
/* 129 */     this.bufferSource.endBatch();
/* 130 */     RenderSystem.enableDepthTest();
/*     */   }
/*     */   
/*     */   public void hLine(int $$0, int $$1, int $$2, int $$3) {
/* 134 */     hLine(RenderType.gui(), $$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void hLine(RenderType $$0, int $$1, int $$2, int $$3, int $$4) {
/* 138 */     if ($$2 < $$1) {
/* 139 */       int $$5 = $$1;
/* 140 */       $$1 = $$2;
/* 141 */       $$2 = $$5;
/*     */     } 
/* 143 */     fill($$0, $$1, $$3, $$2 + 1, $$3 + 1, $$4);
/*     */   }
/*     */   
/*     */   public void vLine(int $$0, int $$1, int $$2, int $$3) {
/* 147 */     vLine(RenderType.gui(), $$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void vLine(RenderType $$0, int $$1, int $$2, int $$3, int $$4) {
/* 151 */     if ($$3 < $$2) {
/* 152 */       int $$5 = $$2;
/* 153 */       $$2 = $$3;
/* 154 */       $$3 = $$5;
/*     */     } 
/* 156 */     fill($$0, $$1, $$2 + 1, $$1 + 1, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void enableScissor(int $$0, int $$1, int $$2, int $$3) {
/* 160 */     applyScissor(this.scissorStack.push(new ScreenRectangle($$0, $$1, $$2 - $$0, $$3 - $$1)));
/*     */   }
/*     */   
/*     */   public void disableScissor() {
/* 164 */     applyScissor(this.scissorStack.pop());
/*     */   }
/*     */   
/*     */   private void applyScissor(@Nullable ScreenRectangle $$0) {
/* 168 */     flushIfManaged();
/* 169 */     if ($$0 != null) {
/* 170 */       Window $$1 = Minecraft.getInstance().getWindow();
/* 171 */       int $$2 = $$1.getHeight();
/* 172 */       double $$3 = $$1.getGuiScale();
/*     */       
/* 174 */       double $$4 = $$0.left() * $$3;
/* 175 */       double $$5 = $$2 - $$0.bottom() * $$3;
/* 176 */       double $$6 = $$0.width() * $$3;
/* 177 */       double $$7 = $$0.height() * $$3;
/* 178 */       RenderSystem.enableScissor((int)$$4, (int)$$5, Math.max(0, (int)$$6), Math.max(0, (int)$$7));
/*     */     } else {
/* 180 */       RenderSystem.disableScissor();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setColor(float $$0, float $$1, float $$2, float $$3) {
/* 185 */     flushIfManaged();
/* 186 */     RenderSystem.setShaderColor($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void fill(int $$0, int $$1, int $$2, int $$3, int $$4) {
/* 190 */     fill($$0, $$1, $$2, $$3, 0, $$4);
/*     */   }
/*     */   
/*     */   public void fill(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 194 */     fill(RenderType.gui(), $$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public void fill(RenderType $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 198 */     fill($$0, $$1, $$2, $$3, $$4, 0, $$5);
/*     */   }
/*     */   
/*     */   public void fill(RenderType $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 202 */     Matrix4f $$7 = this.pose.last().pose();
/* 203 */     if ($$1 < $$3) {
/* 204 */       int $$8 = $$1;
/* 205 */       $$1 = $$3;
/* 206 */       $$3 = $$8;
/*     */     } 
/* 208 */     if ($$2 < $$4) {
/* 209 */       int $$9 = $$2;
/* 210 */       $$2 = $$4;
/* 211 */       $$4 = $$9;
/*     */     } 
/* 213 */     float $$10 = FastColor.ARGB32.alpha($$6) / 255.0F;
/* 214 */     float $$11 = FastColor.ARGB32.red($$6) / 255.0F;
/* 215 */     float $$12 = FastColor.ARGB32.green($$6) / 255.0F;
/* 216 */     float $$13 = FastColor.ARGB32.blue($$6) / 255.0F;
/* 217 */     VertexConsumer $$14 = this.bufferSource.getBuffer($$0);
/* 218 */     $$14.vertex($$7, $$1, $$2, $$5).color($$11, $$12, $$13, $$10).endVertex();
/* 219 */     $$14.vertex($$7, $$1, $$4, $$5).color($$11, $$12, $$13, $$10).endVertex();
/* 220 */     $$14.vertex($$7, $$3, $$4, $$5).color($$11, $$12, $$13, $$10).endVertex();
/* 221 */     $$14.vertex($$7, $$3, $$2, $$5).color($$11, $$12, $$13, $$10).endVertex();
/* 222 */     flushIfUnmanaged();
/*     */   }
/*     */   
/*     */   public void fillGradient(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 226 */     fillGradient($$0, $$1, $$2, $$3, 0, $$4, $$5);
/*     */   }
/*     */   
/*     */   public void fillGradient(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 230 */     fillGradient(RenderType.gui(), $$0, $$1, $$2, $$3, $$5, $$6, $$4);
/*     */   }
/*     */   
/*     */   public void fillGradient(RenderType $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 234 */     VertexConsumer $$8 = this.bufferSource.getBuffer($$0);
/* 235 */     fillGradient($$8, $$1, $$2, $$3, $$4, $$7, $$5, $$6);
/* 236 */     flushIfUnmanaged();
/*     */   }
/*     */   
/*     */   private void fillGradient(VertexConsumer $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 240 */     float $$8 = FastColor.ARGB32.alpha($$6) / 255.0F;
/* 241 */     float $$9 = FastColor.ARGB32.red($$6) / 255.0F;
/* 242 */     float $$10 = FastColor.ARGB32.green($$6) / 255.0F;
/* 243 */     float $$11 = FastColor.ARGB32.blue($$6) / 255.0F;
/*     */     
/* 245 */     float $$12 = FastColor.ARGB32.alpha($$7) / 255.0F;
/* 246 */     float $$13 = FastColor.ARGB32.red($$7) / 255.0F;
/* 247 */     float $$14 = FastColor.ARGB32.green($$7) / 255.0F;
/* 248 */     float $$15 = FastColor.ARGB32.blue($$7) / 255.0F;
/*     */     
/* 250 */     Matrix4f $$16 = this.pose.last().pose();
/* 251 */     $$0.vertex($$16, $$1, $$2, $$5).color($$9, $$10, $$11, $$8).endVertex();
/* 252 */     $$0.vertex($$16, $$1, $$4, $$5).color($$13, $$14, $$15, $$12).endVertex();
/* 253 */     $$0.vertex($$16, $$3, $$4, $$5).color($$13, $$14, $$15, $$12).endVertex();
/* 254 */     $$0.vertex($$16, $$3, $$2, $$5).color($$9, $$10, $$11, $$8).endVertex();
/*     */   }
/*     */   
/*     */   public void drawCenteredString(Font $$0, String $$1, int $$2, int $$3, int $$4) {
/* 258 */     drawString($$0, $$1, $$2 - $$0.width($$1) / 2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(Font $$0, Component $$1, int $$2, int $$3, int $$4) {
/* 262 */     FormattedCharSequence $$5 = $$1.getVisualOrderText();
/* 263 */     drawString($$0, $$5, $$2 - $$0.width($$5) / 2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(Font $$0, FormattedCharSequence $$1, int $$2, int $$3, int $$4) {
/* 267 */     drawString($$0, $$1, $$2 - $$0.width($$1) / 2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public int drawString(Font $$0, @Nullable String $$1, int $$2, int $$3, int $$4) {
/* 271 */     return drawString($$0, $$1, $$2, $$3, $$4, true);
/*     */   }
/*     */   
/*     */   public int drawString(Font $$0, @Nullable String $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 275 */     if ($$1 == null) {
/* 276 */       return 0;
/*     */     }
/*     */     
/* 279 */     int $$6 = $$0.drawInBatch($$1, $$2, $$3, $$4, $$5, this.pose.last().pose(), (MultiBufferSource)this.bufferSource, Font.DisplayMode.NORMAL, 0, 15728880, $$0.isBidirectional());
/* 280 */     flushIfUnmanaged();
/* 281 */     return $$6;
/*     */   }
/*     */   
/*     */   public int drawString(Font $$0, FormattedCharSequence $$1, int $$2, int $$3, int $$4) {
/* 285 */     return drawString($$0, $$1, $$2, $$3, $$4, true);
/*     */   }
/*     */   
/*     */   public int drawString(Font $$0, FormattedCharSequence $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 289 */     int $$6 = $$0.drawInBatch($$1, $$2, $$3, $$4, $$5, this.pose.last().pose(), (MultiBufferSource)this.bufferSource, Font.DisplayMode.NORMAL, 0, 15728880);
/* 290 */     flushIfUnmanaged();
/* 291 */     return $$6;
/*     */   }
/*     */   
/*     */   public int drawString(Font $$0, Component $$1, int $$2, int $$3, int $$4) {
/* 295 */     return drawString($$0, $$1, $$2, $$3, $$4, true);
/*     */   }
/*     */   
/*     */   public int drawString(Font $$0, Component $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 299 */     return drawString($$0, $$1.getVisualOrderText(), $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public void drawWordWrap(Font $$0, FormattedText $$1, int $$2, int $$3, int $$4, int $$5) {
/* 303 */     for (FormattedCharSequence $$6 : $$0.split($$1, $$4)) {
/* 304 */       drawString($$0, $$6, $$2, $$3, $$5, false);
/* 305 */       Objects.requireNonNull($$0); $$3 += 9;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void blit(int $$0, int $$1, int $$2, int $$3, int $$4, TextureAtlasSprite $$5) {
/* 310 */     blitSprite($$5, $$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void blit(int $$0, int $$1, int $$2, int $$3, int $$4, TextureAtlasSprite $$5, float $$6, float $$7, float $$8, float $$9) {
/* 314 */     innerBlit($$5
/* 315 */         .atlasLocation(), $$0, $$0 + $$3, $$1, $$1 + $$4, $$2, $$5
/*     */ 
/*     */ 
/*     */         
/* 319 */         .getU0(), $$5.getU1(), $$5
/* 320 */         .getV0(), $$5.getV1(), $$6, $$7, $$8, $$9);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOutline(int $$0, int $$1, int $$2, int $$3, int $$4) {
/* 326 */     fill($$0, $$1, $$0 + $$2, $$1 + 1, $$4);
/* 327 */     fill($$0, $$1 + $$3 - 1, $$0 + $$2, $$1 + $$3, $$4);
/* 328 */     fill($$0, $$1 + 1, $$0 + 1, $$1 + $$3 - 1, $$4);
/* 329 */     fill($$0 + $$2 - 1, $$1 + 1, $$0 + $$2, $$1 + $$3 - 1, $$4);
/*     */   }
/*     */   
/*     */   public void blitSprite(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4) {
/* 333 */     blitSprite($$0, $$1, $$2, 0, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void blitSprite(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 337 */     TextureAtlasSprite $$6 = this.sprites.getSprite($$0);
/* 338 */     GuiSpriteScaling $$7 = this.sprites.getSpriteScaling($$6);
/* 339 */     if ($$7 instanceof GuiSpriteScaling.Stretch)
/* 340 */     { blitSprite($$6, $$1, $$2, $$3, $$4, $$5); }
/* 341 */     else if ($$7 instanceof GuiSpriteScaling.Tile) { GuiSpriteScaling.Tile $$8 = (GuiSpriteScaling.Tile)$$7;
/* 342 */       blitTiledSprite($$6, $$1, $$2, $$3, $$4, $$5, 0, 0, $$8.width(), $$8.height(), $$8.width(), $$8.height()); }
/* 343 */     else if ($$7 instanceof GuiSpriteScaling.NineSlice) { GuiSpriteScaling.NineSlice $$9 = (GuiSpriteScaling.NineSlice)$$7;
/* 344 */       blitNineSlicedSprite($$6, $$9, $$1, $$2, $$3, $$4, $$5); }
/*     */   
/*     */   }
/*     */   
/*     */   public void blitSprite(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/* 349 */     blitSprite($$0, $$1, $$2, $$3, $$4, $$5, $$6, 0, $$7, $$8);
/*     */   }
/*     */   
/*     */   public void blitSprite(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9) {
/* 353 */     TextureAtlasSprite $$10 = this.sprites.getSprite($$0);
/* 354 */     GuiSpriteScaling $$11 = this.sprites.getSpriteScaling($$10);
/* 355 */     if ($$11 instanceof GuiSpriteScaling.Stretch) {
/* 356 */       blitSprite($$10, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */     } else {
/*     */       
/* 359 */       blitSprite($$10, $$5, $$6, $$7, $$8, $$9);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void blitSprite(TextureAtlasSprite $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9) {
/* 364 */     if ($$8 == 0 || $$9 == 0) {
/*     */       return;
/*     */     }
/* 367 */     innerBlit($$0
/* 368 */         .atlasLocation(), $$5, $$5 + $$8, $$6, $$6 + $$9, $$7, $$0
/*     */ 
/*     */ 
/*     */         
/* 372 */         .getU($$3 / $$1), $$0
/* 373 */         .getU(($$3 + $$8) / $$1), $$0
/* 374 */         .getV($$4 / $$2), $$0
/* 375 */         .getV(($$4 + $$9) / $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   private void blitSprite(TextureAtlasSprite $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 380 */     if ($$4 == 0 || $$5 == 0) {
/*     */       return;
/*     */     }
/* 383 */     innerBlit($$0
/* 384 */         .atlasLocation(), $$1, $$1 + $$4, $$2, $$2 + $$5, $$3, $$0
/*     */ 
/*     */ 
/*     */         
/* 388 */         .getU0(), $$0.getU1(), $$0
/* 389 */         .getV0(), $$0.getV1());
/*     */   }
/*     */ 
/*     */   
/*     */   public void blit(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 394 */     blit($$0, $$1, $$2, 0, $$3, $$4, $$5, $$6, 256, 256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void blit(ResourceLocation $$0, int $$1, int $$2, int $$3, float $$4, float $$5, int $$6, int $$7, int $$8, int $$9) {
/* 405 */     blit($$0, $$1, $$1 + $$6, $$2, $$2 + $$7, $$3, $$6, $$7, $$4, $$5, $$8, $$9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void blit(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, float $$5, float $$6, int $$7, int $$8, int $$9, int $$10) {
/* 417 */     blit($$0, $$1, $$1 + $$3, $$2, $$2 + $$4, 0, $$7, $$8, $$5, $$6, $$9, $$10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void blit(ResourceLocation $$0, int $$1, int $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8) {
/* 429 */     blit($$0, $$1, $$2, $$5, $$6, $$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   void blit(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, float $$8, float $$9, int $$10, int $$11) {
/* 433 */     innerBlit($$0, $$1, $$2, $$3, $$4, $$5, ($$8 + 0.0F) / $$10, ($$8 + $$6) / $$10, ($$9 + 0.0F) / $$11, ($$9 + $$7) / $$11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void innerBlit(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5, float $$6, float $$7, float $$8, float $$9) {
/* 442 */     RenderSystem.setShaderTexture(0, $$0);
/* 443 */     RenderSystem.setShader(GameRenderer::getPositionTexShader);
/*     */     
/* 445 */     Matrix4f $$10 = this.pose.last().pose();
/* 446 */     BufferBuilder $$11 = Tesselator.getInstance().getBuilder();
/* 447 */     $$11.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
/* 448 */     $$11.vertex($$10, $$1, $$3, $$5).uv($$6, $$8).endVertex();
/* 449 */     $$11.vertex($$10, $$1, $$4, $$5).uv($$6, $$9).endVertex();
/* 450 */     $$11.vertex($$10, $$2, $$4, $$5).uv($$7, $$9).endVertex();
/* 451 */     $$11.vertex($$10, $$2, $$3, $$5).uv($$7, $$8).endVertex();
/*     */     
/* 453 */     BufferUploader.drawWithShader($$11.end());
/*     */   }
/*     */   
/*     */   void innerBlit(ResourceLocation $$0, int $$1, int $$2, int $$3, int $$4, int $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11, float $$12, float $$13) {
/* 457 */     RenderSystem.setShaderTexture(0, $$0);
/* 458 */     RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
/* 459 */     RenderSystem.enableBlend();
/*     */     
/* 461 */     Matrix4f $$14 = this.pose.last().pose();
/* 462 */     BufferBuilder $$15 = Tesselator.getInstance().getBuilder();
/* 463 */     $$15.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
/* 464 */     $$15.vertex($$14, $$1, $$3, $$5).color($$10, $$11, $$12, $$13).uv($$6, $$8).endVertex();
/* 465 */     $$15.vertex($$14, $$1, $$4, $$5).color($$10, $$11, $$12, $$13).uv($$6, $$9).endVertex();
/* 466 */     $$15.vertex($$14, $$2, $$4, $$5).color($$10, $$11, $$12, $$13).uv($$7, $$9).endVertex();
/* 467 */     $$15.vertex($$14, $$2, $$3, $$5).color($$10, $$11, $$12, $$13).uv($$7, $$8).endVertex();
/*     */     
/* 469 */     BufferUploader.drawWithShader($$15.end());
/*     */     
/* 471 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private void blitNineSlicedSprite(TextureAtlasSprite $$0, GuiSpriteScaling.NineSlice $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 475 */     GuiSpriteScaling.NineSlice.Border $$7 = $$1.border();
/* 476 */     int $$8 = Math.min($$7.left(), $$5 / 2);
/* 477 */     int $$9 = Math.min($$7.right(), $$5 / 2);
/* 478 */     int $$10 = Math.min($$7.top(), $$6 / 2);
/* 479 */     int $$11 = Math.min($$7.bottom(), $$6 / 2);
/* 480 */     if ($$5 == $$1.width() && $$6 == $$1.height()) {
/* 481 */       blitSprite($$0, $$1.width(), $$1.height(), 0, 0, $$2, $$3, $$4, $$5, $$6); return;
/*     */     } 
/* 483 */     if ($$6 == $$1.height()) {
/* 484 */       blitSprite($$0, $$1.width(), $$1.height(), 0, 0, $$2, $$3, $$4, $$8, $$6);
/* 485 */       blitTiledSprite($$0, $$2 + $$8, $$3, $$4, $$5 - $$9 - $$8, $$6, $$8, 0, $$1.width() - $$9 - $$8, $$1.height(), $$1.width(), $$1.height());
/* 486 */       blitSprite($$0, $$1.width(), $$1.height(), $$1.width() - $$9, 0, $$2 + $$5 - $$9, $$3, $$4, $$9, $$6); return;
/*     */     } 
/* 488 */     if ($$5 == $$1.width()) {
/* 489 */       blitSprite($$0, $$1.width(), $$1.height(), 0, 0, $$2, $$3, $$4, $$5, $$10);
/* 490 */       blitTiledSprite($$0, $$2, $$3 + $$10, $$4, $$5, $$6 - $$11 - $$10, 0, $$10, $$1.width(), $$1.height() - $$11 - $$10, $$1.width(), $$1.height());
/* 491 */       blitSprite($$0, $$1.width(), $$1.height(), 0, $$1.height() - $$11, $$2, $$3 + $$6 - $$11, $$4, $$5, $$11);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 496 */     blitSprite($$0, $$1.width(), $$1.height(), 0, 0, $$2, $$3, $$4, $$8, $$10);
/*     */     
/* 498 */     blitTiledSprite($$0, $$2 + $$8, $$3, $$4, $$5 - $$9 - $$8, $$10, $$8, 0, $$1.width() - $$9 - $$8, $$10, $$1.width(), $$1.height());
/*     */     
/* 500 */     blitSprite($$0, $$1.width(), $$1.height(), $$1.width() - $$9, 0, $$2 + $$5 - $$9, $$3, $$4, $$9, $$10);
/*     */     
/* 502 */     blitSprite($$0, $$1.width(), $$1.height(), 0, $$1.height() - $$11, $$2, $$3 + $$6 - $$11, $$4, $$8, $$11);
/*     */     
/* 504 */     blitTiledSprite($$0, $$2 + $$8, $$3 + $$6 - $$11, $$4, $$5 - $$9 - $$8, $$11, $$8, $$1.height() - $$11, $$1.width() - $$9 - $$8, $$11, $$1.width(), $$1.height());
/*     */     
/* 506 */     blitSprite($$0, $$1.width(), $$1.height(), $$1.width() - $$9, $$1.height() - $$11, $$2 + $$5 - $$9, $$3 + $$6 - $$11, $$4, $$9, $$11);
/*     */     
/* 508 */     blitTiledSprite($$0, $$2, $$3 + $$10, $$4, $$8, $$6 - $$11 - $$10, 0, $$10, $$8, $$1.height() - $$11 - $$10, $$1.width(), $$1.height());
/*     */     
/* 510 */     blitTiledSprite($$0, $$2 + $$8, $$3 + $$10, $$4, $$5 - $$9 - $$8, $$6 - $$11 - $$10, $$8, $$10, $$1.width() - $$9 - $$8, $$1.height() - $$11 - $$10, $$1.width(), $$1.height());
/*     */     
/* 512 */     blitTiledSprite($$0, $$2 + $$5 - $$9, $$3 + $$10, $$4, $$8, $$6 - $$11 - $$10, $$1.width() - $$9, $$10, $$9, $$1.height() - $$11 - $$10, $$1.width(), $$1.height());
/*     */   }
/*     */   
/*     */   private void blitTiledSprite(TextureAtlasSprite $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11) {
/* 516 */     if ($$4 <= 0 || $$5 <= 0) {
/*     */       return;
/*     */     }
/* 519 */     if ($$8 <= 0 || $$9 <= 0)
/* 520 */       throw new IllegalArgumentException("Tiled sprite texture size must be positive, got " + $$8 + "x" + $$9); 
/*     */     int $$12;
/* 522 */     for ($$12 = 0; $$12 < $$4; $$12 += $$8) {
/* 523 */       int $$13 = Math.min($$8, $$4 - $$12); int $$14;
/* 524 */       for ($$14 = 0; $$14 < $$5; $$14 += $$9) {
/* 525 */         int $$15 = Math.min($$9, $$5 - $$14);
/* 526 */         blitSprite($$0, $$10, $$11, $$6, $$7, $$1 + $$12, $$2 + $$14, $$3, $$13, $$15);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderItem(ItemStack $$0, int $$1, int $$2) {
/* 532 */     renderItem((LivingEntity)this.minecraft.player, (Level)this.minecraft.level, $$0, $$1, $$2, 0);
/*     */   }
/*     */   
/*     */   public void renderItem(ItemStack $$0, int $$1, int $$2, int $$3) {
/* 536 */     renderItem((LivingEntity)this.minecraft.player, (Level)this.minecraft.level, $$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void renderItem(ItemStack $$0, int $$1, int $$2, int $$3, int $$4) {
/* 540 */     renderItem((LivingEntity)this.minecraft.player, (Level)this.minecraft.level, $$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void renderFakeItem(ItemStack $$0, int $$1, int $$2) {
/* 544 */     renderFakeItem($$0, $$1, $$2, 0);
/*     */   }
/*     */   
/*     */   public void renderFakeItem(ItemStack $$0, int $$1, int $$2, int $$3) {
/* 548 */     renderItem(null, (Level)this.minecraft.level, $$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void renderItem(LivingEntity $$0, ItemStack $$1, int $$2, int $$3, int $$4) {
/* 552 */     renderItem($$0, $$0.level(), $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private void renderItem(@Nullable LivingEntity $$0, @Nullable Level $$1, ItemStack $$2, int $$3, int $$4, int $$5) {
/* 556 */     renderItem($$0, $$1, $$2, $$3, $$4, $$5, 0);
/*     */   }
/*     */   
/*     */   private void renderItem(@Nullable LivingEntity $$0, @Nullable Level $$1, ItemStack $$2, int $$3, int $$4, int $$5, int $$6) {
/* 560 */     if ($$2.isEmpty()) {
/*     */       return;
/*     */     }
/* 563 */     BakedModel $$7 = this.minecraft.getItemRenderer().getModel($$2, $$1, $$0, $$5);
/* 564 */     this.pose.pushPose();
/* 565 */     this.pose.translate(($$3 + 8), ($$4 + 8), (150 + ($$7.isGui3d() ? $$6 : 0)));
/*     */     
/*     */     try {
/* 568 */       this.pose.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
/* 569 */       this.pose.scale(16.0F, 16.0F, 16.0F);
/*     */       
/* 571 */       boolean $$8 = !$$7.usesBlockLight();
/* 572 */       if ($$8) {
/* 573 */         Lighting.setupForFlatItems();
/*     */       }
/*     */       
/* 576 */       this.minecraft.getItemRenderer().render($$2, ItemDisplayContext.GUI, false, this.pose, (MultiBufferSource)bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, $$7);
/* 577 */       flush();
/*     */       
/* 579 */       if ($$8) {
/* 580 */         Lighting.setupFor3DItems();
/*     */       }
/* 582 */     } catch (Throwable $$9) {
/* 583 */       CrashReport $$10 = CrashReport.forThrowable($$9, "Rendering item");
/* 584 */       CrashReportCategory $$11 = $$10.addCategory("Item being rendered");
/*     */       
/* 586 */       $$11.setDetail("Item Type", () -> String.valueOf($$0.getItem()));
/* 587 */       $$11.setDetail("Item Damage", () -> String.valueOf($$0.getDamageValue()));
/* 588 */       $$11.setDetail("Item NBT", () -> String.valueOf($$0.getTag()));
/* 589 */       $$11.setDetail("Item Foil", () -> String.valueOf($$0.hasFoil()));
/*     */       
/* 591 */       throw new ReportedException($$10);
/*     */     } 
/* 593 */     this.pose.popPose();
/*     */   }
/*     */   
/*     */   public void renderItemDecorations(Font $$0, ItemStack $$1, int $$2, int $$3) {
/* 597 */     renderItemDecorations($$0, $$1, $$2, $$3, null);
/*     */   }
/*     */   
/*     */   public void renderItemDecorations(Font $$0, ItemStack $$1, int $$2, int $$3, @Nullable String $$4) {
/* 601 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 605 */     this.pose.pushPose();
/*     */     
/* 607 */     if ($$1.getCount() != 1 || $$4 != null) {
/* 608 */       String $$5 = ($$4 == null) ? String.valueOf($$1.getCount()) : $$4;
/*     */       
/* 610 */       this.pose.translate(0.0F, 0.0F, 200.0F);
/* 611 */       drawString($$0, $$5, $$2 + 19 - 2 - $$0.width($$5), $$3 + 6 + 3, 16777215, true);
/*     */     } 
/*     */     
/* 614 */     if ($$1.isBarVisible()) {
/* 615 */       int $$6 = $$1.getBarWidth();
/* 616 */       int $$7 = $$1.getBarColor();
/*     */       
/* 618 */       int $$8 = $$2 + 2;
/* 619 */       int $$9 = $$3 + 13;
/* 620 */       fill(RenderType.guiOverlay(), $$8, $$9, $$8 + 13, $$9 + 2, -16777216);
/* 621 */       fill(RenderType.guiOverlay(), $$8, $$9, $$8 + $$6, $$9 + 1, $$7 | 0xFF000000);
/*     */     } 
/*     */     
/* 624 */     LocalPlayer $$10 = this.minecraft.player;
/* 625 */     float $$11 = ($$10 == null) ? 0.0F : $$10.getCooldowns().getCooldownPercent($$1.getItem(), this.minecraft.getFrameTime());
/*     */     
/* 627 */     if ($$11 > 0.0F) {
/* 628 */       int $$12 = $$3 + Mth.floor(16.0F * (1.0F - $$11));
/* 629 */       int $$13 = $$12 + Mth.ceil(16.0F * $$11);
/* 630 */       fill(RenderType.guiOverlay(), $$2, $$12, $$2 + 16, $$13, 2147483647);
/*     */     } 
/*     */     
/* 633 */     this.pose.popPose();
/*     */   }
/*     */   
/*     */   public void renderTooltip(Font $$0, ItemStack $$1, int $$2, int $$3) {
/* 637 */     renderTooltip($$0, Screen.getTooltipFromItem(this.minecraft, $$1), $$1.getTooltipImage(), $$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTooltip(Font $$0, List<Component> $$1, Optional<TooltipComponent> $$2, int $$3, int $$4) {
/* 644 */     List<ClientTooltipComponent> $$5 = (List<ClientTooltipComponent>)$$1.stream().map(Component::getVisualOrderText).map(ClientTooltipComponent::create).collect(Collectors.toList());
/* 645 */     $$2.ifPresent($$1 -> $$0.add(1, ClientTooltipComponent.create($$1)));
/* 646 */     renderTooltipInternal($$0, $$5, $$3, $$4, DefaultTooltipPositioner.INSTANCE);
/*     */   }
/*     */   
/*     */   public void renderTooltip(Font $$0, Component $$1, int $$2, int $$3) {
/* 650 */     renderTooltip($$0, List.of($$1.getVisualOrderText()), $$2, $$3);
/*     */   }
/*     */   
/*     */   public void renderComponentTooltip(Font $$0, List<Component> $$1, int $$2, int $$3) {
/* 654 */     renderTooltip($$0, Lists.transform($$1, Component::getVisualOrderText), $$2, $$3);
/*     */   }
/*     */   
/*     */   public void renderTooltip(Font $$0, List<? extends FormattedCharSequence> $$1, int $$2, int $$3) {
/* 658 */     renderTooltipInternal($$0, (List<ClientTooltipComponent>)$$1.stream().map(ClientTooltipComponent::create).collect(Collectors.toList()), $$2, $$3, DefaultTooltipPositioner.INSTANCE);
/*     */   }
/*     */   
/*     */   public void renderTooltip(Font $$0, List<FormattedCharSequence> $$1, ClientTooltipPositioner $$2, int $$3, int $$4) {
/* 662 */     renderTooltipInternal($$0, (List<ClientTooltipComponent>)$$1.stream().map(ClientTooltipComponent::create).collect(Collectors.toList()), $$3, $$4, $$2);
/*     */   }
/*     */   
/*     */   private void renderTooltipInternal(Font $$0, List<ClientTooltipComponent> $$1, int $$2, int $$3, ClientTooltipPositioner $$4) {
/* 666 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 670 */     int $$5 = 0;
/* 671 */     int $$6 = ($$1.size() == 1) ? -2 : 0;
/* 672 */     for (ClientTooltipComponent $$7 : $$1) {
/* 673 */       int $$8 = $$7.getWidth($$0);
/* 674 */       if ($$8 > $$5) {
/* 675 */         $$5 = $$8;
/*     */       }
/* 677 */       $$6 += $$7.getHeight();
/*     */     } 
/*     */     
/* 680 */     int $$9 = $$5;
/* 681 */     int $$10 = $$6;
/*     */     
/* 683 */     Vector2ic $$11 = $$4.positionTooltip(guiWidth(), guiHeight(), $$2, $$3, $$9, $$10);
/* 684 */     int $$12 = $$11.x();
/* 685 */     int $$13 = $$11.y();
/*     */     
/* 687 */     this.pose.pushPose();
/*     */ 
/*     */     
/* 690 */     int $$14 = 400;
/*     */     
/* 692 */     drawManaged(() -> TooltipRenderUtil.renderTooltipBackground(this, $$0, $$1, $$2, $$3, 400));
/*     */     
/* 694 */     this.pose.translate(0.0F, 0.0F, 400.0F);
/*     */ 
/*     */     
/* 697 */     int $$15 = $$13;
/* 698 */     for (int $$16 = 0; $$16 < $$1.size(); $$16++) {
/* 699 */       ClientTooltipComponent $$17 = $$1.get($$16);
/* 700 */       $$17.renderText($$0, $$12, $$15, this.pose.last().pose(), this.bufferSource);
/* 701 */       $$15 += $$17.getHeight() + (($$16 == 0) ? 2 : 0);
/*     */     } 
/*     */ 
/*     */     
/* 705 */     $$15 = $$13;
/* 706 */     for (int $$18 = 0; $$18 < $$1.size(); $$18++) {
/* 707 */       ClientTooltipComponent $$19 = $$1.get($$18);
/* 708 */       $$19.renderImage($$0, $$12, $$15, this);
/* 709 */       $$15 += $$19.getHeight() + (($$18 == 0) ? 2 : 0);
/*     */     } 
/*     */     
/* 712 */     this.pose.popPose();
/*     */   }
/*     */   
/*     */   public void renderComponentHoverEffect(Font $$0, @Nullable Style $$1, int $$2, int $$3) {
/* 716 */     if ($$1 == null || $$1.getHoverEvent() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 720 */     HoverEvent $$4 = $$1.getHoverEvent();
/* 721 */     HoverEvent.ItemStackInfo $$5 = (HoverEvent.ItemStackInfo)$$4.getValue(HoverEvent.Action.SHOW_ITEM);
/*     */     
/* 723 */     if ($$5 != null) {
/* 724 */       renderTooltip($$0, $$5.getItemStack(), $$2, $$3);
/*     */     } else {
/* 726 */       HoverEvent.EntityTooltipInfo $$6 = (HoverEvent.EntityTooltipInfo)$$4.getValue(HoverEvent.Action.SHOW_ENTITY);
/* 727 */       if ($$6 != null) {
/* 728 */         if (this.minecraft.options.advancedItemTooltips) {
/* 729 */           renderComponentTooltip($$0, $$6.getTooltipLines(), $$2, $$3);
/*     */         }
/*     */       } else {
/* 732 */         Component $$7 = (Component)$$4.getValue(HoverEvent.Action.SHOW_TEXT);
/* 733 */         if ($$7 != null)
/* 734 */           renderTooltip($$0, $$0.split((FormattedText)$$7, Math.max(guiWidth() / 2, 200)), $$2, $$3); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class ScissorStack
/*     */   {
/* 741 */     private final Deque<ScreenRectangle> stack = new ArrayDeque<>();
/*     */     
/*     */     public ScreenRectangle push(ScreenRectangle $$0) {
/* 744 */       ScreenRectangle $$1 = this.stack.peekLast();
/* 745 */       if ($$1 != null) {
/* 746 */         ScreenRectangle $$2 = Objects.<ScreenRectangle>requireNonNullElse($$0.intersection($$1), ScreenRectangle.empty());
/* 747 */         this.stack.addLast($$2);
/* 748 */         return $$2;
/*     */       } 
/* 750 */       this.stack.addLast($$0);
/* 751 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public ScreenRectangle pop() {
/* 757 */       if (this.stack.isEmpty()) {
/* 758 */         throw new IllegalStateException("Scissor stack underflow");
/*     */       }
/* 760 */       this.stack.removeLast();
/* 761 */       return this.stack.peekLast();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\GuiGraphics.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */