/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.armortrim.TrimPattern;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class SmithingTemplateItem
/*     */   extends Item {
/*  16 */   private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
/*  17 */   private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;
/*  18 */   private static final Component INGREDIENTS_TITLE = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.ingredients"))).withStyle(TITLE_FORMAT);
/*  19 */   private static final Component APPLIES_TO_TITLE = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.applies_to"))).withStyle(TITLE_FORMAT);
/*  20 */   private static final Component NETHERITE_UPGRADE = (Component)Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation("netherite_upgrade"))).withStyle(TITLE_FORMAT);
/*  21 */   private static final Component ARMOR_TRIM_APPLIES_TO = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.armor_trim.applies_to"))).withStyle(DESCRIPTION_FORMAT);
/*  22 */   private static final Component ARMOR_TRIM_INGREDIENTS = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.armor_trim.ingredients"))).withStyle(DESCRIPTION_FORMAT);
/*  23 */   private static final Component ARMOR_TRIM_BASE_SLOT_DESCRIPTION = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.armor_trim.base_slot_description")));
/*  24 */   private static final Component ARMOR_TRIM_ADDITIONS_SLOT_DESCRIPTION = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.armor_trim.additions_slot_description")));
/*  25 */   private static final Component NETHERITE_UPGRADE_APPLIES_TO = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.netherite_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMAT);
/*  26 */   private static final Component NETHERITE_UPGRADE_INGREDIENTS = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.netherite_upgrade.ingredients"))).withStyle(DESCRIPTION_FORMAT);
/*  27 */   private static final Component NETHERITE_UPGRADE_BASE_SLOT_DESCRIPTION = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.netherite_upgrade.base_slot_description")));
/*  28 */   private static final Component NETHERITE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.netherite_upgrade.additions_slot_description")));
/*  29 */   private static final ResourceLocation EMPTY_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
/*  30 */   private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
/*  31 */   private static final ResourceLocation EMPTY_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
/*  32 */   private static final ResourceLocation EMPTY_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
/*  33 */   private static final ResourceLocation EMPTY_SLOT_HOE = new ResourceLocation("item/empty_slot_hoe");
/*  34 */   private static final ResourceLocation EMPTY_SLOT_AXE = new ResourceLocation("item/empty_slot_axe");
/*  35 */   private static final ResourceLocation EMPTY_SLOT_SWORD = new ResourceLocation("item/empty_slot_sword");
/*  36 */   private static final ResourceLocation EMPTY_SLOT_SHOVEL = new ResourceLocation("item/empty_slot_shovel");
/*  37 */   private static final ResourceLocation EMPTY_SLOT_PICKAXE = new ResourceLocation("item/empty_slot_pickaxe");
/*  38 */   private static final ResourceLocation EMPTY_SLOT_INGOT = new ResourceLocation("item/empty_slot_ingot");
/*  39 */   private static final ResourceLocation EMPTY_SLOT_REDSTONE_DUST = new ResourceLocation("item/empty_slot_redstone_dust");
/*  40 */   private static final ResourceLocation EMPTY_SLOT_QUARTZ = new ResourceLocation("item/empty_slot_quartz");
/*  41 */   private static final ResourceLocation EMPTY_SLOT_EMERALD = new ResourceLocation("item/empty_slot_emerald");
/*  42 */   private static final ResourceLocation EMPTY_SLOT_DIAMOND = new ResourceLocation("item/empty_slot_diamond");
/*  43 */   private static final ResourceLocation EMPTY_SLOT_LAPIS_LAZULI = new ResourceLocation("item/empty_slot_lapis_lazuli");
/*  44 */   private static final ResourceLocation EMPTY_SLOT_AMETHYST_SHARD = new ResourceLocation("item/empty_slot_amethyst_shard");
/*     */   
/*     */   private final Component appliesTo;
/*     */   
/*     */   private final Component ingredients;
/*     */   private final Component upgradeDescription;
/*     */   private final Component baseSlotDescription;
/*     */   private final Component additionsSlotDescription;
/*     */   private final List<ResourceLocation> baseSlotEmptyIcons;
/*     */   private final List<ResourceLocation> additionalSlotEmptyIcons;
/*     */   
/*     */   public SmithingTemplateItem(Component $$0, Component $$1, Component $$2, Component $$3, Component $$4, List<ResourceLocation> $$5, List<ResourceLocation> $$6) {
/*  56 */     super(new Item.Properties());
/*     */     
/*  58 */     this.appliesTo = $$0;
/*  59 */     this.ingredients = $$1;
/*  60 */     this.upgradeDescription = $$2;
/*  61 */     this.baseSlotDescription = $$3;
/*  62 */     this.additionsSlotDescription = $$4;
/*  63 */     this.baseSlotEmptyIcons = $$5;
/*  64 */     this.additionalSlotEmptyIcons = $$6;
/*     */   }
/*     */   
/*     */   public static SmithingTemplateItem createArmorTrimTemplate(ResourceKey<TrimPattern> $$0) {
/*  68 */     return createArmorTrimTemplate($$0.location());
/*     */   }
/*     */   
/*     */   public static SmithingTemplateItem createArmorTrimTemplate(ResourceLocation $$0) {
/*  72 */     return new SmithingTemplateItem(ARMOR_TRIM_APPLIES_TO, ARMOR_TRIM_INGREDIENTS, (Component)Component.translatable(Util.makeDescriptionId("trim_pattern", $$0)).withStyle(TITLE_FORMAT), ARMOR_TRIM_BASE_SLOT_DESCRIPTION, ARMOR_TRIM_ADDITIONS_SLOT_DESCRIPTION, 
/*  73 */         createTrimmableArmorIconList(), createTrimmableMaterialIconList());
/*     */   }
/*     */   
/*     */   public static SmithingTemplateItem createNetheriteUpgradeTemplate() {
/*  77 */     return new SmithingTemplateItem(NETHERITE_UPGRADE_APPLIES_TO, NETHERITE_UPGRADE_INGREDIENTS, NETHERITE_UPGRADE, NETHERITE_UPGRADE_BASE_SLOT_DESCRIPTION, NETHERITE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION, 
/*  78 */         createNetheriteUpgradeIconList(), createNetheriteUpgradeMaterialList());
/*     */   }
/*     */   
/*     */   private static List<ResourceLocation> createTrimmableArmorIconList() {
/*  82 */     return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_BOOTS);
/*     */   }
/*     */   
/*     */   private static List<ResourceLocation> createTrimmableMaterialIconList() {
/*  86 */     return List.of(EMPTY_SLOT_INGOT, EMPTY_SLOT_REDSTONE_DUST, EMPTY_SLOT_LAPIS_LAZULI, EMPTY_SLOT_QUARTZ, EMPTY_SLOT_DIAMOND, EMPTY_SLOT_EMERALD, EMPTY_SLOT_AMETHYST_SHARD);
/*     */   }
/*     */   
/*     */   private static List<ResourceLocation> createNetheriteUpgradeIconList() {
/*  90 */     return List.of(EMPTY_SLOT_HELMET, EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE, EMPTY_SLOT_PICKAXE, EMPTY_SLOT_LEGGINGS, EMPTY_SLOT_AXE, EMPTY_SLOT_BOOTS, EMPTY_SLOT_HOE, EMPTY_SLOT_SHOVEL);
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<ResourceLocation> createNetheriteUpgradeMaterialList() {
/*  95 */     return List.of(EMPTY_SLOT_INGOT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 100 */     super.appendHoverText($$0, $$1, $$2, $$3);
/*     */     
/* 102 */     $$2.add(this.upgradeDescription);
/* 103 */     $$2.add(CommonComponents.EMPTY);
/* 104 */     $$2.add(APPLIES_TO_TITLE);
/* 105 */     $$2.add(CommonComponents.space().append(this.appliesTo));
/* 106 */     $$2.add(INGREDIENTS_TITLE);
/* 107 */     $$2.add(CommonComponents.space().append(this.ingredients));
/*     */   }
/*     */   
/*     */   public Component getBaseSlotDescription() {
/* 111 */     return this.baseSlotDescription;
/*     */   }
/*     */   
/*     */   public Component getAdditionSlotDescription() {
/* 115 */     return this.additionsSlotDescription;
/*     */   }
/*     */   
/*     */   public List<ResourceLocation> getBaseSlotEmptyIcons() {
/* 119 */     return this.baseSlotEmptyIcons;
/*     */   }
/*     */   
/*     */   public List<ResourceLocation> getAdditionalSlotEmptyIcons() {
/* 123 */     return this.additionalSlotEmptyIcons;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SmithingTemplateItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */