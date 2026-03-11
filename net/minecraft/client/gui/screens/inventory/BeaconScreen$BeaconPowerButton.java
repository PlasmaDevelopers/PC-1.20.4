/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.effect.MobEffect;
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
/*     */ class BeaconPowerButton
/*     */   extends BeaconScreen.BeaconScreenButton
/*     */ {
/*     */   private final boolean isPrimary;
/*     */   protected final int tier;
/*     */   private MobEffect effect;
/*     */   private TextureAtlasSprite sprite;
/*     */   
/*     */   public BeaconPowerButton(int $$0, int $$1, MobEffect $$2, boolean $$3, int $$4) {
/* 203 */     super($$0, $$1);
/* 204 */     this.isPrimary = $$3;
/* 205 */     this.tier = $$4;
/* 206 */     setEffect($$2);
/*     */   }
/*     */   
/*     */   protected void setEffect(MobEffect $$0) {
/* 210 */     this.effect = $$0;
/* 211 */     this.sprite = Minecraft.getInstance().getMobEffectTextures().get($$0);
/* 212 */     setTooltip(Tooltip.create((Component)createEffectDescription($$0), null));
/*     */   }
/*     */   
/*     */   protected MutableComponent createEffectDescription(MobEffect $$0) {
/* 216 */     return Component.translatable($$0.getDescriptionId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPress() {
/* 221 */     if (isSelected()) {
/*     */       return;
/*     */     }
/*     */     
/* 225 */     if (this.isPrimary) {
/* 226 */       BeaconScreen.this.primary = this.effect;
/*     */     } else {
/* 228 */       BeaconScreen.this.secondary = this.effect;
/*     */     } 
/* 230 */     BeaconScreen.this.updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderIcon(GuiGraphics $$0) {
/* 235 */     $$0.blit(getX() + 2, getY() + 2, 0, 18, 18, this.sprite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateStatus(int $$0) {
/* 240 */     this.active = (this.tier < $$0);
/* 241 */     setSelected((this.effect == (this.isPrimary ? BeaconScreen.this.primary : BeaconScreen.this.secondary)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected MutableComponent createNarrationMessage() {
/* 246 */     return createEffectDescription(this.effect);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BeaconScreen$BeaconPowerButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */