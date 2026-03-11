/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractButton;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.BeaconMenu;
/*     */ import net.minecraft.world.inventory.ContainerListener;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.entity.BeaconBlockEntity;
/*     */ 
/*     */ public class BeaconScreen extends AbstractContainerScreen<BeaconMenu> {
/*  30 */   private static final ResourceLocation BEACON_LOCATION = new ResourceLocation("textures/gui/container/beacon.png");
/*  31 */   static final ResourceLocation BUTTON_DISABLED_SPRITE = new ResourceLocation("container/beacon/button_disabled");
/*  32 */   static final ResourceLocation BUTTON_SELECTED_SPRITE = new ResourceLocation("container/beacon/button_selected");
/*  33 */   static final ResourceLocation BUTTON_HIGHLIGHTED_SPRITE = new ResourceLocation("container/beacon/button_highlighted");
/*  34 */   static final ResourceLocation BUTTON_SPRITE = new ResourceLocation("container/beacon/button");
/*  35 */   static final ResourceLocation CONFIRM_SPRITE = new ResourceLocation("container/beacon/confirm");
/*  36 */   static final ResourceLocation CANCEL_SPRITE = new ResourceLocation("container/beacon/cancel");
/*  37 */   private static final Component PRIMARY_EFFECT_LABEL = (Component)Component.translatable("block.minecraft.beacon.primary");
/*  38 */   private static final Component SECONDARY_EFFECT_LABEL = (Component)Component.translatable("block.minecraft.beacon.secondary");
/*     */   
/*  40 */   private final List<BeaconButton> beaconButtons = Lists.newArrayList();
/*     */   @Nullable
/*     */   MobEffect primary;
/*     */   @Nullable
/*     */   MobEffect secondary;
/*     */   
/*     */   public BeaconScreen(final BeaconMenu menu, Inventory $$1, Component $$2) {
/*  47 */     super(menu, $$1, $$2);
/*     */     
/*  49 */     this.imageWidth = 230;
/*  50 */     this.imageHeight = 219;
/*     */     
/*  52 */     menu.addSlotListener(new ContainerListener()
/*     */         {
/*     */           public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {
/*  59 */             BeaconScreen.this.primary = menu.getPrimaryEffect();
/*  60 */             BeaconScreen.this.secondary = menu.getSecondaryEffect();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private <T extends net.minecraft.client.gui.components.AbstractWidget & BeaconButton> void addBeaconButton(T $$0) {
/*  66 */     addRenderableWidget((GuiEventListener)$$0);
/*  67 */     this.beaconButtons.add((BeaconButton)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  72 */     super.init();
/*     */     
/*  74 */     this.beaconButtons.clear();
/*  75 */     addBeaconButton(new BeaconConfirmButton(this.leftPos + 164, this.topPos + 107));
/*  76 */     addBeaconButton(new BeaconCancelButton(this.leftPos + 190, this.topPos + 107));
/*     */     
/*  78 */     for (int $$0 = 0; $$0 <= 2; $$0++) {
/*  79 */       int $$1 = (BeaconBlockEntity.BEACON_EFFECTS[$$0]).length;
/*  80 */       int $$2 = $$1 * 22 + ($$1 - 1) * 2;
/*     */       
/*  82 */       for (int $$3 = 0; $$3 < $$1; $$3++) {
/*  83 */         MobEffect $$4 = BeaconBlockEntity.BEACON_EFFECTS[$$0][$$3];
/*  84 */         BeaconPowerButton $$5 = new BeaconPowerButton(this.leftPos + 76 + $$3 * 24 - $$2 / 2, this.topPos + 22 + $$0 * 25, $$4, true, $$0);
/*  85 */         $$5.active = false;
/*  86 */         addBeaconButton($$5);
/*     */       } 
/*     */     } 
/*  89 */     int $$6 = 3;
/*     */     
/*  91 */     int $$7 = (BeaconBlockEntity.BEACON_EFFECTS[3]).length + 1;
/*  92 */     int $$8 = $$7 * 22 + ($$7 - 1) * 2;
/*     */     
/*  94 */     for (int $$9 = 0; $$9 < $$7 - 1; $$9++) {
/*  95 */       MobEffect $$10 = BeaconBlockEntity.BEACON_EFFECTS[3][$$9];
/*  96 */       BeaconPowerButton $$11 = new BeaconPowerButton(this.leftPos + 167 + $$9 * 24 - $$8 / 2, this.topPos + 47, $$10, false, 3);
/*  97 */       $$11.active = false;
/*  98 */       addBeaconButton($$11);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     BeaconPowerButton $$12 = new BeaconUpgradePowerButton(this.leftPos + 167 + ($$7 - 1) * 24 - $$8 / 2, this.topPos + 47, BeaconBlockEntity.BEACON_EFFECTS[0][0]);
/* 103 */     $$12.visible = false;
/* 104 */     addBeaconButton($$12);
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerTick() {
/* 109 */     super.containerTick();
/* 110 */     updateButtons();
/*     */   }
/*     */   
/*     */   void updateButtons() {
/* 114 */     int $$0 = this.menu.getLevels();
/* 115 */     this.beaconButtons.forEach($$1 -> $$1.updateStatus($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLabels(GuiGraphics $$0, int $$1, int $$2) {
/* 120 */     $$0.drawCenteredString(this.font, PRIMARY_EFFECT_LABEL, 62, 10, 14737632);
/* 121 */     $$0.drawCenteredString(this.font, SECONDARY_EFFECT_LABEL, 169, 10, 14737632);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 126 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 127 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 128 */     $$0.blit(BEACON_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */     
/* 130 */     $$0.pose().pushPose();
/* 131 */     $$0.pose().translate(0.0F, 0.0F, 100.0F);
/* 132 */     $$0.renderItem(new ItemStack((ItemLike)Items.NETHERITE_INGOT), $$4 + 20, $$5 + 109);
/* 133 */     $$0.renderItem(new ItemStack((ItemLike)Items.EMERALD), $$4 + 41, $$5 + 109);
/* 134 */     $$0.renderItem(new ItemStack((ItemLike)Items.DIAMOND), $$4 + 41 + 22, $$5 + 109);
/* 135 */     $$0.renderItem(new ItemStack((ItemLike)Items.GOLD_INGOT), $$4 + 42 + 44, $$5 + 109);
/* 136 */     $$0.renderItem(new ItemStack((ItemLike)Items.IRON_INGOT), $$4 + 42 + 66, $$5 + 109);
/* 137 */     $$0.pose().popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 142 */     super.render($$0, $$1, $$2, $$3);
/* 143 */     renderTooltip($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class BeaconScreenButton
/*     */     extends AbstractButton
/*     */     implements BeaconButton
/*     */   {
/*     */     private boolean selected;
/*     */     
/*     */     protected BeaconScreenButton(int $$0, int $$1) {
/* 154 */       super($$0, $$1, 22, 22, CommonComponents.EMPTY);
/*     */     }
/*     */     
/*     */     protected BeaconScreenButton(int $$0, int $$1, Component $$2) {
/* 158 */       super($$0, $$1, 22, 22, $$2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */       ResourceLocation $$7;
/* 165 */       if (!this.active) {
/* 166 */         ResourceLocation $$4 = BeaconScreen.BUTTON_DISABLED_SPRITE;
/* 167 */       } else if (this.selected) {
/* 168 */         ResourceLocation $$5 = BeaconScreen.BUTTON_SELECTED_SPRITE;
/* 169 */       } else if (isHoveredOrFocused()) {
/* 170 */         ResourceLocation $$6 = BeaconScreen.BUTTON_HIGHLIGHTED_SPRITE;
/*     */       } else {
/* 172 */         $$7 = BeaconScreen.BUTTON_SPRITE;
/*     */       } 
/*     */       
/* 175 */       $$0.blitSprite($$7, getX(), getY(), this.width, this.height);
/*     */       
/* 177 */       renderIcon($$0);
/*     */     }
/*     */     
/*     */     protected abstract void renderIcon(GuiGraphics param1GuiGraphics);
/*     */     
/*     */     public boolean isSelected() {
/* 183 */       return this.selected;
/*     */     }
/*     */     
/*     */     public void setSelected(boolean $$0) {
/* 187 */       this.selected = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 192 */       defaultButtonNarrationText($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BeaconPowerButton extends BeaconScreenButton {
/*     */     private final boolean isPrimary;
/*     */     protected final int tier;
/*     */     private MobEffect effect;
/*     */     private TextureAtlasSprite sprite;
/*     */     
/*     */     public BeaconPowerButton(int $$0, int $$1, MobEffect $$2, boolean $$3, int $$4) {
/* 203 */       super($$0, $$1);
/* 204 */       this.isPrimary = $$3;
/* 205 */       this.tier = $$4;
/* 206 */       setEffect($$2);
/*     */     }
/*     */     
/*     */     protected void setEffect(MobEffect $$0) {
/* 210 */       this.effect = $$0;
/* 211 */       this.sprite = Minecraft.getInstance().getMobEffectTextures().get($$0);
/* 212 */       setTooltip(Tooltip.create((Component)createEffectDescription($$0), null));
/*     */     }
/*     */     
/*     */     protected MutableComponent createEffectDescription(MobEffect $$0) {
/* 216 */       return Component.translatable($$0.getDescriptionId());
/*     */     }
/*     */ 
/*     */     
/*     */     public void onPress() {
/* 221 */       if (isSelected()) {
/*     */         return;
/*     */       }
/*     */       
/* 225 */       if (this.isPrimary) {
/* 226 */         BeaconScreen.this.primary = this.effect;
/*     */       } else {
/* 228 */         BeaconScreen.this.secondary = this.effect;
/*     */       } 
/* 230 */       BeaconScreen.this.updateButtons();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderIcon(GuiGraphics $$0) {
/* 235 */       $$0.blit(getX() + 2, getY() + 2, 0, 18, 18, this.sprite);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateStatus(int $$0) {
/* 240 */       this.active = (this.tier < $$0);
/* 241 */       setSelected((this.effect == (this.isPrimary ? BeaconScreen.this.primary : BeaconScreen.this.secondary)));
/*     */     }
/*     */ 
/*     */     
/*     */     protected MutableComponent createNarrationMessage() {
/* 246 */       return createEffectDescription(this.effect);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BeaconUpgradePowerButton extends BeaconPowerButton {
/*     */     public BeaconUpgradePowerButton(int $$0, int $$1, MobEffect $$2) {
/* 252 */       super($$0, $$1, $$2, false, 3);
/*     */     }
/*     */ 
/*     */     
/*     */     protected MutableComponent createEffectDescription(MobEffect $$0) {
/* 257 */       return Component.translatable($$0.getDescriptionId()).append(" II");
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateStatus(int $$0) {
/* 262 */       if (BeaconScreen.this.primary != null) {
/* 263 */         this.visible = true;
/* 264 */         setEffect(BeaconScreen.this.primary);
/* 265 */         super.updateStatus($$0);
/*     */       } else {
/* 267 */         this.visible = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract class BeaconSpriteScreenButton extends BeaconScreenButton {
/*     */     private final ResourceLocation sprite;
/*     */     
/*     */     protected BeaconSpriteScreenButton(int $$0, int $$1, ResourceLocation $$2, Component $$3) {
/* 276 */       super($$0, $$1, $$3);
/* 277 */       this.sprite = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderIcon(GuiGraphics $$0) {
/* 282 */       $$0.blitSprite(this.sprite, getX() + 2, getY() + 2, 18, 18);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BeaconConfirmButton extends BeaconSpriteScreenButton {
/*     */     public BeaconConfirmButton(int $$0, int $$1) {
/* 288 */       super($$0, $$1, BeaconScreen.CONFIRM_SPRITE, CommonComponents.GUI_DONE);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onPress() {
/* 293 */       BeaconScreen.this.minecraft.getConnection().send((Packet)new ServerboundSetBeaconPacket(Optional.ofNullable(BeaconScreen.this.primary), Optional.ofNullable(BeaconScreen.this.secondary)));
/* 294 */       BeaconScreen.this.minecraft.player.closeContainer();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateStatus(int $$0) {
/* 299 */       this.active = (BeaconScreen.this.menu.hasPayment() && BeaconScreen.this.primary != null);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BeaconCancelButton extends BeaconSpriteScreenButton {
/*     */     public BeaconCancelButton(int $$0, int $$1) {
/* 305 */       super($$0, $$1, BeaconScreen.CANCEL_SPRITE, CommonComponents.GUI_CANCEL);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onPress() {
/* 310 */       BeaconScreen.this.minecraft.player.closeContainer();
/*     */     }
/*     */     
/*     */     public void updateStatus(int $$0) {}
/*     */   }
/*     */   
/*     */   private static interface BeaconButton {
/*     */     void updateStatus(int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BeaconScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */