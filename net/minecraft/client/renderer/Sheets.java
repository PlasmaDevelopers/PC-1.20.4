/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
/*     */ import net.minecraft.world.level.block.state.properties.ChestType;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sheets
/*     */ {
/*  29 */   public static final ResourceLocation SHULKER_SHEET = new ResourceLocation("textures/atlas/shulker_boxes.png");
/*  30 */   public static final ResourceLocation BED_SHEET = new ResourceLocation("textures/atlas/beds.png");
/*  31 */   public static final ResourceLocation BANNER_SHEET = new ResourceLocation("textures/atlas/banner_patterns.png");
/*  32 */   public static final ResourceLocation SHIELD_SHEET = new ResourceLocation("textures/atlas/shield_patterns.png");
/*  33 */   public static final ResourceLocation SIGN_SHEET = new ResourceLocation("textures/atlas/signs.png");
/*  34 */   public static final ResourceLocation CHEST_SHEET = new ResourceLocation("textures/atlas/chest.png");
/*  35 */   public static final ResourceLocation ARMOR_TRIMS_SHEET = new ResourceLocation("textures/atlas/armor_trims.png");
/*  36 */   public static final ResourceLocation DECORATED_POT_SHEET = new ResourceLocation("textures/atlas/decorated_pot.png");
/*     */   
/*  38 */   private static final RenderType SHULKER_BOX_SHEET_TYPE = RenderType.entityCutoutNoCull(SHULKER_SHEET);
/*  39 */   private static final RenderType BED_SHEET_TYPE = RenderType.entitySolid(BED_SHEET);
/*  40 */   private static final RenderType BANNER_SHEET_TYPE = RenderType.entityNoOutline(BANNER_SHEET);
/*  41 */   private static final RenderType SHIELD_SHEET_TYPE = RenderType.entityNoOutline(SHIELD_SHEET);
/*  42 */   private static final RenderType SIGN_SHEET_TYPE = RenderType.entityCutoutNoCull(SIGN_SHEET);
/*  43 */   private static final RenderType CHEST_SHEET_TYPE = RenderType.entityCutout(CHEST_SHEET);
/*  44 */   private static final RenderType ARMOR_TRIMS_SHEET_TYPE = RenderType.armorCutoutNoCull(ARMOR_TRIMS_SHEET);
/*  45 */   private static final RenderType ARMOR_TRIMS_DECAL_SHEET_TYPE = RenderType.createArmorDecalCutoutNoCull(ARMOR_TRIMS_SHEET);
/*     */   
/*  47 */   private static final RenderType SOLID_BLOCK_SHEET = RenderType.entitySolid(TextureAtlas.LOCATION_BLOCKS);
/*  48 */   private static final RenderType CUTOUT_BLOCK_SHEET = RenderType.entityCutout(TextureAtlas.LOCATION_BLOCKS);
/*  49 */   private static final RenderType TRANSLUCENT_ITEM_CULL_BLOCK_SHEET = RenderType.itemEntityTranslucentCull(TextureAtlas.LOCATION_BLOCKS);
/*  50 */   private static final RenderType TRANSLUCENT_CULL_BLOCK_SHEET = RenderType.entityTranslucentCull(TextureAtlas.LOCATION_BLOCKS);
/*     */   
/*  52 */   public static final Material DEFAULT_SHULKER_TEXTURE_LOCATION = new Material(SHULKER_SHEET, new ResourceLocation("entity/shulker/shulker"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final List<Material> SHULKER_TEXTURE_LOCATION;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  70 */     SHULKER_TEXTURE_LOCATION = (List<Material>)Stream.<String>of(new String[] { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black" }).map($$0 -> new Material(SHULKER_SHEET, new ResourceLocation("entity/shulker/shulker_" + $$0))).collect(ImmutableList.toImmutableList());
/*     */   }
/*  72 */   public static final Map<WoodType, Material> SIGN_MATERIALS = (Map<WoodType, Material>)WoodType.values().collect(Collectors.toMap(Function.identity(), Sheets::createSignMaterial));
/*     */   
/*  74 */   public static final Map<WoodType, Material> HANGING_SIGN_MATERIALS = (Map<WoodType, Material>)WoodType.values().collect(Collectors.toMap(Function.identity(), Sheets::createHangingSignMaterial));
/*     */   
/*  76 */   public static final Map<ResourceKey<BannerPattern>, Material> BANNER_MATERIALS = (Map<ResourceKey<BannerPattern>, Material>)BuiltInRegistries.BANNER_PATTERN.registryKeySet().stream().collect(Collectors.toMap(Function.identity(), Sheets::createBannerMaterial));
/*     */   
/*  78 */   public static final Map<ResourceKey<BannerPattern>, Material> SHIELD_MATERIALS = (Map<ResourceKey<BannerPattern>, Material>)BuiltInRegistries.BANNER_PATTERN.registryKeySet().stream().collect(Collectors.toMap(Function.identity(), Sheets::createShieldMaterial));
/*     */   
/*  80 */   public static final Map<ResourceKey<String>, Material> DECORATED_POT_MATERIALS = (Map<ResourceKey<String>, Material>)BuiltInRegistries.DECORATED_POT_PATTERNS.registryKeySet().stream().collect(Collectors.toMap(Function.identity(), Sheets::createDecoratedPotMaterial)); public static final Material[] BED_TEXTURES;
/*     */   static {
/*  82 */     BED_TEXTURES = (Material[])Arrays.<DyeColor>stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map($$0 -> new Material(BED_SHEET, new ResourceLocation("entity/bed/" + $$0.getName()))).toArray($$0 -> new Material[$$0]);
/*     */   }
/*  84 */   public static final Material CHEST_TRAP_LOCATION = chestMaterial("trapped");
/*     */   
/*  86 */   public static final Material CHEST_TRAP_LOCATION_LEFT = chestMaterial("trapped_left");
/*  87 */   public static final Material CHEST_TRAP_LOCATION_RIGHT = chestMaterial("trapped_right");
/*  88 */   public static final Material CHEST_XMAS_LOCATION = chestMaterial("christmas");
/*  89 */   public static final Material CHEST_XMAS_LOCATION_LEFT = chestMaterial("christmas_left");
/*  90 */   public static final Material CHEST_XMAS_LOCATION_RIGHT = chestMaterial("christmas_right");
/*  91 */   public static final Material CHEST_LOCATION = chestMaterial("normal");
/*  92 */   public static final Material CHEST_LOCATION_LEFT = chestMaterial("normal_left");
/*  93 */   public static final Material CHEST_LOCATION_RIGHT = chestMaterial("normal_right");
/*  94 */   public static final Material ENDER_CHEST_LOCATION = chestMaterial("ender");
/*     */   
/*     */   public static RenderType bannerSheet() {
/*  97 */     return BANNER_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType shieldSheet() {
/* 101 */     return SHIELD_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType bedSheet() {
/* 105 */     return BED_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType shulkerBoxSheet() {
/* 109 */     return SHULKER_BOX_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType signSheet() {
/* 113 */     return SIGN_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType hangingSignSheet() {
/* 117 */     return SIGN_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType chestSheet() {
/* 121 */     return CHEST_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType armorTrimsSheet(boolean $$0) {
/* 125 */     return $$0 ? ARMOR_TRIMS_DECAL_SHEET_TYPE : ARMOR_TRIMS_SHEET_TYPE;
/*     */   }
/*     */   
/*     */   public static RenderType solidBlockSheet() {
/* 129 */     return SOLID_BLOCK_SHEET;
/*     */   }
/*     */   
/*     */   public static RenderType cutoutBlockSheet() {
/* 133 */     return CUTOUT_BLOCK_SHEET;
/*     */   }
/*     */   
/*     */   public static RenderType translucentItemSheet() {
/* 137 */     return TRANSLUCENT_ITEM_CULL_BLOCK_SHEET;
/*     */   }
/*     */   
/*     */   public static RenderType translucentCullBlockSheet() {
/* 141 */     return TRANSLUCENT_CULL_BLOCK_SHEET;
/*     */   }
/*     */   
/*     */   public static void getAllMaterials(Consumer<Material> $$0) {
/* 145 */     $$0.accept(DEFAULT_SHULKER_TEXTURE_LOCATION);
/* 146 */     SHULKER_TEXTURE_LOCATION.forEach($$0);
/* 147 */     BANNER_MATERIALS.values().forEach($$0);
/* 148 */     SHIELD_MATERIALS.values().forEach($$0);
/* 149 */     SIGN_MATERIALS.values().forEach($$0);
/* 150 */     HANGING_SIGN_MATERIALS.values().forEach($$0);
/*     */     
/* 152 */     for (Material $$1 : BED_TEXTURES) {
/* 153 */       $$0.accept($$1);
/*     */     }
/*     */     
/* 156 */     $$0.accept(CHEST_TRAP_LOCATION);
/* 157 */     $$0.accept(CHEST_TRAP_LOCATION_LEFT);
/* 158 */     $$0.accept(CHEST_TRAP_LOCATION_RIGHT);
/*     */     
/* 160 */     $$0.accept(CHEST_XMAS_LOCATION);
/* 161 */     $$0.accept(CHEST_XMAS_LOCATION_LEFT);
/* 162 */     $$0.accept(CHEST_XMAS_LOCATION_RIGHT);
/*     */     
/* 164 */     $$0.accept(CHEST_LOCATION);
/* 165 */     $$0.accept(CHEST_LOCATION_LEFT);
/* 166 */     $$0.accept(CHEST_LOCATION_RIGHT);
/*     */     
/* 168 */     $$0.accept(ENDER_CHEST_LOCATION);
/*     */   }
/*     */   
/*     */   private static Material createSignMaterial(WoodType $$0) {
/* 172 */     return new Material(SIGN_SHEET, new ResourceLocation("entity/signs/" + $$0.name()));
/*     */   }
/*     */   
/*     */   private static Material createHangingSignMaterial(WoodType $$0) {
/* 176 */     return new Material(SIGN_SHEET, new ResourceLocation("entity/signs/hanging/" + $$0.name()));
/*     */   }
/*     */   
/*     */   public static Material getSignMaterial(WoodType $$0) {
/* 180 */     return SIGN_MATERIALS.get($$0);
/*     */   }
/*     */   
/*     */   public static Material getHangingSignMaterial(WoodType $$0) {
/* 184 */     return HANGING_SIGN_MATERIALS.get($$0);
/*     */   }
/*     */   
/*     */   private static Material createBannerMaterial(ResourceKey<BannerPattern> $$0) {
/* 188 */     return new Material(BANNER_SHEET, BannerPattern.location($$0, true));
/*     */   }
/*     */   
/*     */   public static Material getBannerMaterial(ResourceKey<BannerPattern> $$0) {
/* 192 */     return BANNER_MATERIALS.get($$0);
/*     */   }
/*     */   
/*     */   private static Material createShieldMaterial(ResourceKey<BannerPattern> $$0) {
/* 196 */     return new Material(SHIELD_SHEET, BannerPattern.location($$0, false));
/*     */   }
/*     */   
/*     */   public static Material getShieldMaterial(ResourceKey<BannerPattern> $$0) {
/* 200 */     return SHIELD_MATERIALS.get($$0);
/*     */   }
/*     */   
/*     */   private static Material chestMaterial(String $$0) {
/* 204 */     return new Material(CHEST_SHEET, new ResourceLocation("entity/chest/" + $$0));
/*     */   }
/*     */   
/*     */   private static Material createDecoratedPotMaterial(ResourceKey<String> $$0) {
/* 208 */     return new Material(DECORATED_POT_SHEET, DecoratedPotPatterns.location($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Material getDecoratedPotMaterial(@Nullable ResourceKey<String> $$0) {
/* 213 */     if ($$0 == null) {
/* 214 */       return null;
/*     */     }
/* 216 */     return DECORATED_POT_MATERIALS.get($$0);
/*     */   }
/*     */   
/*     */   public static Material chooseMaterial(BlockEntity $$0, ChestType $$1, boolean $$2) {
/* 220 */     if ($$0 instanceof net.minecraft.world.level.block.entity.EnderChestBlockEntity)
/* 221 */       return ENDER_CHEST_LOCATION; 
/* 222 */     if ($$2)
/* 223 */       return chooseMaterial($$1, CHEST_XMAS_LOCATION, CHEST_XMAS_LOCATION_LEFT, CHEST_XMAS_LOCATION_RIGHT); 
/* 224 */     if ($$0 instanceof net.minecraft.world.level.block.entity.TrappedChestBlockEntity) {
/* 225 */       return chooseMaterial($$1, CHEST_TRAP_LOCATION, CHEST_TRAP_LOCATION_LEFT, CHEST_TRAP_LOCATION_RIGHT);
/*     */     }
/* 227 */     return chooseMaterial($$1, CHEST_LOCATION, CHEST_LOCATION_LEFT, CHEST_LOCATION_RIGHT);
/*     */   }
/*     */   
/*     */   private static Material chooseMaterial(ChestType $$0, Material $$1, Material $$2, Material $$3) {
/* 231 */     switch ($$0) {
/*     */       case LEFT:
/* 233 */         return $$2;
/*     */       case RIGHT:
/* 235 */         return $$3;
/*     */     } 
/*     */     
/* 238 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\Sheets.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */