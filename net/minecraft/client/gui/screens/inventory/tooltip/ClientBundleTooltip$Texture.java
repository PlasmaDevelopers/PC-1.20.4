/*     */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*     */ 
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum Texture
/*     */ {
/*  91 */   BLOCKED_SLOT(new ResourceLocation("container/bundle/blocked_slot"), 18, 20),
/*  92 */   SLOT(new ResourceLocation("container/bundle/slot"), 18, 20);
/*     */   
/*     */   public final ResourceLocation sprite;
/*     */   public final int w;
/*     */   public final int h;
/*     */   
/*     */   Texture(ResourceLocation $$0, int $$1, int $$2) {
/*  99 */     this.sprite = $$0;
/* 100 */     this.w = $$1;
/* 101 */     this.h = $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\ClientBundleTooltip$Texture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */