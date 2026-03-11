/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.decoration.ArmorStand;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.SmithingMenu;
/*     */ import net.minecraft.world.item.ArmorItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.SmithingTemplateItem;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class SmithingScreen extends ItemCombinerScreen<SmithingMenu> {
/*  23 */   private static final ResourceLocation ERROR_SPRITE = new ResourceLocation("container/smithing/error");
/*  24 */   private static final ResourceLocation EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM = new ResourceLocation("item/empty_slot_smithing_template_armor_trim");
/*  25 */   private static final ResourceLocation EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE = new ResourceLocation("item/empty_slot_smithing_template_netherite_upgrade");
/*  26 */   private static final Component MISSING_TEMPLATE_TOOLTIP = (Component)Component.translatable("container.upgrade.missing_template_tooltip");
/*  27 */   private static final Component ERROR_TOOLTIP = (Component)Component.translatable("container.upgrade.error_tooltip");
/*  28 */   private static final List<ResourceLocation> EMPTY_SLOT_SMITHING_TEMPLATES = List.of(EMPTY_SLOT_SMITHING_TEMPLATE_ARMOR_TRIM, EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE);
/*     */   
/*     */   private static final int TITLE_LABEL_X = 44;
/*     */   
/*     */   private static final int TITLE_LABEL_Y = 15;
/*     */   
/*     */   private static final int ERROR_ICON_WIDTH = 28;
/*     */   
/*     */   private static final int ERROR_ICON_HEIGHT = 21;
/*     */   private static final int ERROR_ICON_X = 65;
/*     */   private static final int ERROR_ICON_Y = 46;
/*     */   private static final int TOOLTIP_WIDTH = 115;
/*     */   private static final int ARMOR_STAND_Y_ROT = 210;
/*     */   private static final int ARMOR_STAND_X_ROT = 25;
/*  42 */   private static final Vector3f ARMOR_STAND_TRANSLATION = new Vector3f();
/*  43 */   private static final Quaternionf ARMOR_STAND_ANGLE = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);
/*     */   
/*     */   private static final int ARMOR_STAND_SCALE = 25;
/*     */   private static final int ARMOR_STAND_OFFSET_Y = 75;
/*     */   private static final int ARMOR_STAND_OFFSET_X = 141;
/*  48 */   private final CyclingSlotBackground templateIcon = new CyclingSlotBackground(0);
/*  49 */   private final CyclingSlotBackground baseIcon = new CyclingSlotBackground(1);
/*  50 */   private final CyclingSlotBackground additionalIcon = new CyclingSlotBackground(2);
/*     */   @Nullable
/*     */   private ArmorStand armorStandPreview;
/*     */   
/*     */   public SmithingScreen(SmithingMenu $$0, Inventory $$1, Component $$2) {
/*  55 */     super($$0, $$1, $$2, new ResourceLocation("textures/gui/container/smithing.png"));
/*  56 */     this.titleLabelX = 44;
/*  57 */     this.titleLabelY = 15;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void subInit() {
/*  62 */     this.armorStandPreview = new ArmorStand((Level)this.minecraft.level, 0.0D, 0.0D, 0.0D);
/*  63 */     this.armorStandPreview.setNoBasePlate(true);
/*  64 */     this.armorStandPreview.setShowArms(true);
/*     */     
/*  66 */     this.armorStandPreview.yBodyRot = 210.0F;
/*  67 */     this.armorStandPreview.setXRot(25.0F);
/*  68 */     this.armorStandPreview.yHeadRot = this.armorStandPreview.getYRot();
/*  69 */     this.armorStandPreview.yHeadRotO = this.armorStandPreview.getYRot();
/*     */     
/*  71 */     updateArmorStandPreview(this.menu.getSlot(3).getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerTick() {
/*  76 */     super.containerTick();
/*     */     
/*  78 */     Optional<SmithingTemplateItem> $$0 = getTemplateItem();
/*  79 */     this.templateIcon.tick(EMPTY_SLOT_SMITHING_TEMPLATES);
/*  80 */     this.baseIcon.tick($$0.<List<ResourceLocation>>map(SmithingTemplateItem::getBaseSlotEmptyIcons).orElse(List.of()));
/*  81 */     this.additionalIcon.tick($$0.<List<ResourceLocation>>map(SmithingTemplateItem::getAdditionalSlotEmptyIcons).orElse(List.of()));
/*     */   }
/*     */   
/*     */   private Optional<SmithingTemplateItem> getTemplateItem() {
/*  85 */     ItemStack $$0 = this.menu.getSlot(0).getItem();
/*  86 */     if (!$$0.isEmpty()) { Item item = $$0.getItem(); if (item instanceof SmithingTemplateItem) { SmithingTemplateItem $$1 = (SmithingTemplateItem)item;
/*  87 */         return Optional.of($$1); }
/*     */        }
/*  89 */      return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  94 */     super.render($$0, $$1, $$2, $$3);
/*  95 */     renderOnboardingTooltips($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 100 */     super.renderBg($$0, $$1, $$2, $$3);
/*     */     
/* 102 */     this.templateIcon.render((AbstractContainerMenu)this.menu, $$0, $$1, this.leftPos, this.topPos);
/* 103 */     this.baseIcon.render((AbstractContainerMenu)this.menu, $$0, $$1, this.leftPos, this.topPos);
/* 104 */     this.additionalIcon.render((AbstractContainerMenu)this.menu, $$0, $$1, this.leftPos, this.topPos);
/*     */     
/* 106 */     InventoryScreen.renderEntityInInventory($$0, (this.leftPos + 141), (this.topPos + 75), 25, ARMOR_STAND_TRANSLATION, ARMOR_STAND_ANGLE, null, (LivingEntity)this.armorStandPreview);
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/* 111 */     if ($$1 == 3) {
/* 112 */       updateArmorStandPreview($$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateArmorStandPreview(ItemStack $$0) {
/* 117 */     if (this.armorStandPreview == null) {
/*     */       return;
/*     */     }
/*     */     
/* 121 */     for (EquipmentSlot $$1 : EquipmentSlot.values()) {
/* 122 */       this.armorStandPreview.setItemSlot($$1, ItemStack.EMPTY);
/*     */     }
/*     */     
/* 125 */     if (!$$0.isEmpty()) {
/* 126 */       ItemStack $$2 = $$0.copy();
/* 127 */       Item item = $$0.getItem(); if (item instanceof ArmorItem) { ArmorItem $$3 = (ArmorItem)item;
/* 128 */         this.armorStandPreview.setItemSlot($$3.getEquipmentSlot(), $$2); }
/*     */       else
/* 130 */       { this.armorStandPreview.setItemSlot(EquipmentSlot.OFFHAND, $$2); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderErrorIcon(GuiGraphics $$0, int $$1, int $$2) {
/* 137 */     if (hasRecipeError()) {
/* 138 */       $$0.blitSprite(ERROR_SPRITE, $$1 + 65, $$2 + 46, 28, 21);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderOnboardingTooltips(GuiGraphics $$0, int $$1, int $$2) {
/* 143 */     Optional<Component> $$3 = Optional.empty();
/*     */     
/* 145 */     if (hasRecipeError() && isHovering(65, 46, 28, 21, $$1, $$2)) {
/* 146 */       $$3 = Optional.of(ERROR_TOOLTIP);
/*     */     }
/*     */     
/* 149 */     if (this.hoveredSlot != null) {
/* 150 */       ItemStack $$4 = this.menu.getSlot(0).getItem();
/* 151 */       ItemStack $$5 = this.hoveredSlot.getItem();
/*     */       
/* 153 */       if ($$4.isEmpty()) {
/* 154 */         if (this.hoveredSlot.index == 0)
/* 155 */           $$3 = Optional.of(MISSING_TEMPLATE_TOOLTIP); 
/*     */       } else {
/* 157 */         Item item = $$4.getItem(); if (item instanceof SmithingTemplateItem) { SmithingTemplateItem $$6 = (SmithingTemplateItem)item; if ($$5.isEmpty())
/* 158 */             if (this.hoveredSlot.index == 1) {
/* 159 */               $$3 = Optional.of($$6.getBaseSlotDescription());
/* 160 */             } else if (this.hoveredSlot.index == 2) {
/* 161 */               $$3 = Optional.of($$6.getAdditionSlotDescription());
/*     */             }   }
/*     */       
/*     */       } 
/*     */     } 
/* 166 */     $$3.ifPresent($$3 -> $$0.renderTooltip(this.font, this.font.split((FormattedText)$$3, 115), $$1, $$2));
/*     */   }
/*     */   
/*     */   private boolean hasRecipeError() {
/* 170 */     return (this.menu.getSlot(0).hasItem() && this.menu
/* 171 */       .getSlot(1).hasItem() && this.menu
/* 172 */       .getSlot(2).hasItem() && 
/* 173 */       !this.menu.getSlot(this.menu.getResultSlot()).hasItem());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\SmithingScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */