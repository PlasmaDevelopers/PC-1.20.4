/*     */ package net.minecraft.world.item.armortrim;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.item.ArmorMaterial;
/*     */ import net.minecraft.world.item.ArmorMaterials;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class ArmorTrim {
/*     */   static {
/*  28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)TrimMaterial.CODEC.fieldOf("material").forGetter(ArmorTrim::material), (App)TrimPattern.CODEC.fieldOf("pattern").forGetter(ArmorTrim::pattern)).apply((Applicative)$$0, ArmorTrim::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<ArmorTrim> CODEC;
/*  33 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   public static final String TAG_TRIM_ID = "Trim";
/*  35 */   private static final Component UPGRADE_TITLE = (Component)Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.upgrade"))).withStyle(ChatFormatting.GRAY);
/*     */   
/*     */   private final Holder<TrimMaterial> material;
/*     */   private final Holder<TrimPattern> pattern;
/*     */   private final Function<ArmorMaterial, ResourceLocation> innerTexture;
/*     */   private final Function<ArmorMaterial, ResourceLocation> outerTexture;
/*     */   
/*     */   public ArmorTrim(Holder<TrimMaterial> $$0, Holder<TrimPattern> $$1) {
/*  43 */     this.material = $$0;
/*  44 */     this.pattern = $$1;
/*  45 */     this.innerTexture = Util.memoize($$1 -> {
/*     */           ResourceLocation $$2 = ((TrimPattern)$$0.value()).assetId();
/*     */           String $$3 = getColorPaletteSuffix($$1);
/*     */           return $$2.withPath(());
/*     */         });
/*  50 */     this.outerTexture = Util.memoize($$1 -> {
/*     */           ResourceLocation $$2 = ((TrimPattern)$$0.value()).assetId();
/*     */           String $$3 = getColorPaletteSuffix($$1);
/*     */           return $$2.withPath(());
/*     */         });
/*     */   }
/*     */   
/*     */   private String getColorPaletteSuffix(ArmorMaterial $$0) {
/*  58 */     Map<ArmorMaterials, String> $$1 = ((TrimMaterial)this.material.value()).overrideArmorMaterials();
/*  59 */     if ($$0 instanceof ArmorMaterials && $$1.containsKey($$0)) {
/*  60 */       return $$1.get($$0);
/*     */     }
/*  62 */     return ((TrimMaterial)this.material.value()).assetName();
/*     */   }
/*     */   
/*     */   public boolean hasPatternAndMaterial(Holder<TrimPattern> $$0, Holder<TrimMaterial> $$1) {
/*  66 */     return ($$0 == this.pattern && $$1 == this.material);
/*     */   }
/*     */   
/*     */   public Holder<TrimPattern> pattern() {
/*  70 */     return this.pattern;
/*     */   }
/*     */   
/*     */   public Holder<TrimMaterial> material() {
/*  74 */     return this.material;
/*     */   }
/*     */   
/*     */   public ResourceLocation innerTexture(ArmorMaterial $$0) {
/*  78 */     return this.innerTexture.apply($$0);
/*     */   }
/*     */   
/*     */   public ResourceLocation outerTexture(ArmorMaterial $$0) {
/*  82 */     return this.outerTexture.apply($$0);
/*     */   }
/*     */   
/*     */   public boolean equals(Object $$0) {
/*     */     ArmorTrim $$1;
/*  87 */     if ($$0 instanceof ArmorTrim) { $$1 = (ArmorTrim)$$0; }
/*  88 */     else { return false; }
/*     */ 
/*     */     
/*  91 */     return ($$1.pattern == this.pattern && $$1.material == this.material);
/*     */   }
/*     */   
/*     */   public static boolean setTrim(RegistryAccess $$0, ItemStack $$1, ArmorTrim $$2) {
/*  95 */     if ($$1.is(ItemTags.TRIMMABLE_ARMOR)) {
/*  96 */       $$1.getOrCreateTag().put("Trim", CODEC.encodeStart((DynamicOps)RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)$$0), $$2).result().orElseThrow());
/*  97 */       return true;
/*     */     } 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public static Optional<ArmorTrim> getTrim(RegistryAccess $$0, ItemStack $$1, boolean $$2) {
/* 104 */     if ($$1.is(ItemTags.TRIMMABLE_ARMOR) && 
/* 105 */       $$1.getTag() != null && $$1.getTag().contains("Trim")) {
/* 106 */       CompoundTag $$3 = $$1.getTagElement("Trim");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       ArmorTrim $$4 = CODEC.parse((DynamicOps)RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)$$0), $$3).resultOrPartial($$1 -> { if (!$$0) LOGGER.warn($$1);  }).orElse(null);
/* 112 */       return Optional.ofNullable($$4);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public static void appendUpgradeHoverText(ItemStack $$0, RegistryAccess $$1, List<Component> $$2) {
/* 120 */     Optional<ArmorTrim> $$3 = getTrim($$1, $$0, true);
/* 121 */     if ($$3.isPresent()) {
/* 122 */       ArmorTrim $$4 = $$3.get();
/* 123 */       $$2.add(UPGRADE_TITLE);
/* 124 */       $$2.add(CommonComponents.space().append(((TrimPattern)$$4.pattern().value()).copyWithStyle($$4.material())));
/* 125 */       $$2.add(CommonComponents.space().append(((TrimMaterial)$$4.material().value()).description()));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\armortrim\ArmorTrim.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */