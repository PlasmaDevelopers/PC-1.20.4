/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.MobEffectTextureManager;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffectUtil;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ 
/*     */ public abstract class EffectRenderingInventoryScreen<T extends AbstractContainerMenu>
/*     */   extends AbstractContainerScreen<T> {
/*  22 */   private static final ResourceLocation EFFECT_BACKGROUND_LARGE_SPRITE = new ResourceLocation("container/inventory/effect_background_large");
/*  23 */   private static final ResourceLocation EFFECT_BACKGROUND_SMALL_SPRITE = new ResourceLocation("container/inventory/effect_background_small");
/*     */   
/*     */   public EffectRenderingInventoryScreen(T $$0, Inventory $$1, Component $$2) {
/*  26 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  31 */     super.render($$0, $$1, $$2, $$3);
/*  32 */     renderEffects($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public boolean canSeeEffects() {
/*  36 */     int $$0 = this.leftPos + this.imageWidth + 2;
/*  37 */     int $$1 = this.width - $$0;
/*     */     
/*  39 */     return ($$1 >= 32);
/*     */   }
/*     */   
/*     */   private void renderEffects(GuiGraphics $$0, int $$1, int $$2) {
/*  43 */     int $$3 = this.leftPos + this.imageWidth + 2;
/*  44 */     int $$4 = this.width - $$3;
/*     */     
/*  46 */     Collection<MobEffectInstance> $$5 = this.minecraft.player.getActiveEffects();
/*     */     
/*  48 */     if ($$5.isEmpty() || $$4 < 32) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     boolean $$6 = ($$4 >= 120);
/*  53 */     int $$7 = 33;
/*  54 */     if ($$5.size() > 5) {
/*  55 */       $$7 = 132 / ($$5.size() - 1);
/*     */     }
/*     */     
/*  58 */     Iterable<MobEffectInstance> $$8 = Ordering.natural().sortedCopy($$5);
/*     */     
/*  60 */     renderBackgrounds($$0, $$3, $$7, $$8, $$6);
/*  61 */     renderIcons($$0, $$3, $$7, $$8, $$6);
/*     */     
/*  63 */     if ($$6) {
/*  64 */       renderLabels($$0, $$3, $$7, $$8);
/*  65 */     } else if ($$1 >= $$3 && $$1 <= $$3 + 33) {
/*  66 */       int $$9 = this.topPos;
/*  67 */       MobEffectInstance $$10 = null;
/*  68 */       for (MobEffectInstance $$11 : $$8) {
/*  69 */         if ($$2 >= $$9 && $$2 <= $$9 + $$7) {
/*  70 */           $$10 = $$11;
/*     */         }
/*  72 */         $$9 += $$7;
/*     */       } 
/*     */       
/*  75 */       if ($$10 != null) {
/*  76 */         List<Component> $$12 = List.of(getEffectName($$10), MobEffectUtil.formatDuration($$10, 1.0F, this.minecraft.level.tickRateManager().tickrate()));
/*  77 */         $$0.renderTooltip(this.font, $$12, Optional.empty(), $$1, $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderBackgrounds(GuiGraphics $$0, int $$1, int $$2, Iterable<MobEffectInstance> $$3, boolean $$4) {
/*  83 */     int $$5 = this.topPos;
/*  84 */     for (MobEffectInstance $$6 : $$3) {
/*  85 */       if ($$4) {
/*  86 */         $$0.blitSprite(EFFECT_BACKGROUND_LARGE_SPRITE, $$1, $$5, 120, 32);
/*     */       } else {
/*  88 */         $$0.blitSprite(EFFECT_BACKGROUND_SMALL_SPRITE, $$1, $$5, 32, 32);
/*     */       } 
/*  90 */       $$5 += $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderIcons(GuiGraphics $$0, int $$1, int $$2, Iterable<MobEffectInstance> $$3, boolean $$4) {
/*  95 */     MobEffectTextureManager $$5 = this.minecraft.getMobEffectTextures();
/*  96 */     int $$6 = this.topPos;
/*  97 */     for (MobEffectInstance $$7 : $$3) {
/*  98 */       MobEffect $$8 = $$7.getEffect();
/*  99 */       TextureAtlasSprite $$9 = $$5.get($$8);
/* 100 */       $$0.blit($$1 + ($$4 ? 6 : 7), $$6 + 7, 0, 18, 18, $$9);
/* 101 */       $$6 += $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderLabels(GuiGraphics $$0, int $$1, int $$2, Iterable<MobEffectInstance> $$3) {
/* 106 */     int $$4 = this.topPos;
/* 107 */     for (MobEffectInstance $$5 : $$3) {
/* 108 */       Component $$6 = getEffectName($$5);
/* 109 */       $$0.drawString(this.font, $$6, $$1 + 10 + 18, $$4 + 6, 16777215);
/*     */       
/* 111 */       Component $$7 = MobEffectUtil.formatDuration($$5, 1.0F, this.minecraft.level.tickRateManager().tickrate());
/* 112 */       $$0.drawString(this.font, $$7, $$1 + 10 + 18, $$4 + 6 + 10, 8355711);
/*     */       
/* 114 */       $$4 += $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Component getEffectName(MobEffectInstance $$0) {
/* 119 */     MutableComponent $$1 = $$0.getEffect().getDisplayName().copy();
/* 120 */     if ($$0.getAmplifier() >= 1 && $$0.getAmplifier() <= 9) {
/* 121 */       $$1.append(CommonComponents.SPACE).append((Component)Component.translatable("enchantment.level." + $$0.getAmplifier() + 1));
/*     */     }
/* 123 */     return (Component)$$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\EffectRenderingInventoryScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */