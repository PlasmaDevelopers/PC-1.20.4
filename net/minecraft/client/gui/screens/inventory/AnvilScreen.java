/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundRenameItemPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AnvilMenu;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class AnvilScreen extends ItemCombinerScreen<AnvilMenu> {
/*  18 */   private static final ResourceLocation TEXT_FIELD_SPRITE = new ResourceLocation("container/anvil/text_field");
/*  19 */   private static final ResourceLocation TEXT_FIELD_DISABLED_SPRITE = new ResourceLocation("container/anvil/text_field_disabled");
/*  20 */   private static final ResourceLocation ERROR_SPRITE = new ResourceLocation("container/anvil/error");
/*  21 */   private static final ResourceLocation ANVIL_LOCATION = new ResourceLocation("textures/gui/container/anvil.png");
/*  22 */   private static final Component TOO_EXPENSIVE_TEXT = (Component)Component.translatable("container.repair.expensive");
/*     */   private EditBox name;
/*     */   private final Player player;
/*     */   
/*     */   public AnvilScreen(AnvilMenu $$0, Inventory $$1, Component $$2) {
/*  27 */     super($$0, $$1, $$2, ANVIL_LOCATION);
/*  28 */     this.player = $$1.player;
/*  29 */     this.titleLabelX = 60;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void subInit() {
/*  34 */     int $$0 = (this.width - this.imageWidth) / 2;
/*  35 */     int $$1 = (this.height - this.imageHeight) / 2;
/*     */     
/*  37 */     this.name = new EditBox(this.font, $$0 + 62, $$1 + 24, 103, 12, (Component)Component.translatable("container.repair"));
/*  38 */     this.name.setCanLoseFocus(false);
/*  39 */     this.name.setTextColor(-1);
/*  40 */     this.name.setTextColorUneditable(-1);
/*  41 */     this.name.setBordered(false);
/*  42 */     this.name.setMaxLength(50);
/*  43 */     this.name.setResponder(this::onNameChanged);
/*  44 */     this.name.setValue("");
/*  45 */     addWidget((GuiEventListener)this.name);
/*  46 */     setInitialFocus((GuiEventListener)this.name);
/*  47 */     this.name.setEditable(this.menu.getSlot(0).hasItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/*  52 */     String $$3 = this.name.getValue();
/*  53 */     init($$0, $$1, $$2);
/*  54 */     this.name.setValue($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  59 */     if ($$0 == 256) {
/*  60 */       this.minecraft.player.closeContainer();
/*     */     }
/*     */     
/*  63 */     if (this.name.keyPressed($$0, $$1, $$2) || this.name.canConsumeInput()) {
/*  64 */       return true;
/*     */     }
/*  66 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void onNameChanged(String $$0) {
/*  70 */     Slot $$1 = this.menu.getSlot(0);
/*  71 */     if (!$$1.hasItem()) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     String $$2 = $$0;
/*  76 */     if (!$$1.getItem().hasCustomHoverName() && $$2.equals($$1.getItem().getHoverName().getString())) {
/*  77 */       $$2 = "";
/*     */     }
/*     */     
/*  80 */     if (this.menu.setItemName($$2)) {
/*  81 */       this.minecraft.player.connection.send((Packet)new ServerboundRenameItemPacket($$2));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLabels(GuiGraphics $$0, int $$1, int $$2) {
/*  87 */     super.renderLabels($$0, $$1, $$2);
/*     */     
/*  89 */     int $$3 = this.menu.getCost();
/*  90 */     if ($$3 > 0) {
/*  91 */       MutableComponent mutableComponent; int $$4 = 8453920;
/*     */       
/*  93 */       if ($$3 >= 40 && !(this.minecraft.player.getAbilities()).instabuild) {
/*  94 */         Component $$5 = TOO_EXPENSIVE_TEXT;
/*  95 */         $$4 = 16736352;
/*  96 */       } else if (!this.menu.getSlot(2).hasItem()) {
/*  97 */         Component $$6 = null;
/*     */       } else {
/*  99 */         mutableComponent = Component.translatable("container.repair.cost", new Object[] { Integer.valueOf($$3) });
/* 100 */         if (!this.menu.getSlot(2).mayPickup(this.player)) {
/* 101 */           $$4 = 16736352;
/*     */         }
/*     */       } 
/*     */       
/* 105 */       if (mutableComponent != null) {
/* 106 */         int $$8 = this.imageWidth - 8 - this.font.width((FormattedText)mutableComponent) - 2;
/* 107 */         int $$9 = 69;
/* 108 */         $$0.fill($$8 - 2, 67, this.imageWidth - 8, 79, 1325400064);
/* 109 */         $$0.drawString(this.font, (Component)mutableComponent, $$8, 69, $$4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 116 */     super.renderBg($$0, $$1, $$2, $$3);
/* 117 */     $$0.blitSprite(this.menu.getSlot(0).hasItem() ? TEXT_FIELD_SPRITE : TEXT_FIELD_DISABLED_SPRITE, this.leftPos + 59, this.topPos + 20, 110, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFg(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 122 */     this.name.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderErrorIcon(GuiGraphics $$0, int $$1, int $$2) {
/* 127 */     if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
/* 128 */       $$0.blitSprite(ERROR_SPRITE, $$1 + 99, $$2 + 45, 28, 21);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/* 134 */     if ($$1 == 0) {
/* 135 */       this.name.setValue($$2.isEmpty() ? "" : $$2.getHoverName().getString());
/* 136 */       this.name.setEditable(!$$2.isEmpty());
/* 137 */       setFocused((GuiEventListener)this.name);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\AnvilScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */