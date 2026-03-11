/*    */ package net.minecraft.world.item.armortrim;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class TrimPatterns
/*    */ {
/* 19 */   public static final ResourceKey<TrimPattern> SENTRY = registryKey("sentry");
/* 20 */   public static final ResourceKey<TrimPattern> DUNE = registryKey("dune");
/* 21 */   public static final ResourceKey<TrimPattern> COAST = registryKey("coast");
/* 22 */   public static final ResourceKey<TrimPattern> WILD = registryKey("wild");
/* 23 */   public static final ResourceKey<TrimPattern> WARD = registryKey("ward");
/* 24 */   public static final ResourceKey<TrimPattern> EYE = registryKey("eye");
/* 25 */   public static final ResourceKey<TrimPattern> VEX = registryKey("vex");
/* 26 */   public static final ResourceKey<TrimPattern> TIDE = registryKey("tide");
/* 27 */   public static final ResourceKey<TrimPattern> SNOUT = registryKey("snout");
/* 28 */   public static final ResourceKey<TrimPattern> RIB = registryKey("rib");
/* 29 */   public static final ResourceKey<TrimPattern> SPIRE = registryKey("spire");
/* 30 */   public static final ResourceKey<TrimPattern> WAYFINDER = registryKey("wayfinder");
/* 31 */   public static final ResourceKey<TrimPattern> SHAPER = registryKey("shaper");
/* 32 */   public static final ResourceKey<TrimPattern> SILENCE = registryKey("silence");
/* 33 */   public static final ResourceKey<TrimPattern> RAISER = registryKey("raiser");
/* 34 */   public static final ResourceKey<TrimPattern> HOST = registryKey("host");
/*    */   
/*    */   public static void bootstrap(BootstapContext<TrimPattern> $$0) {
/* 37 */     register($$0, Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, SENTRY);
/* 38 */     register($$0, Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, DUNE);
/* 39 */     register($$0, Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, COAST);
/* 40 */     register($$0, Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, WILD);
/* 41 */     register($$0, Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, WARD);
/* 42 */     register($$0, Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, EYE);
/* 43 */     register($$0, Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, VEX);
/* 44 */     register($$0, Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, TIDE);
/* 45 */     register($$0, Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, SNOUT);
/* 46 */     register($$0, Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, RIB);
/* 47 */     register($$0, Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, SPIRE);
/* 48 */     register($$0, Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, WAYFINDER);
/* 49 */     register($$0, Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, SHAPER);
/* 50 */     register($$0, Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, SILENCE);
/* 51 */     register($$0, Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, RAISER);
/* 52 */     register($$0, Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, HOST);
/*    */   }
/*    */   
/*    */   public static Optional<Holder.Reference<TrimPattern>> getFromTemplate(RegistryAccess $$0, ItemStack $$1) {
/* 56 */     return $$0.registryOrThrow(Registries.TRIM_PATTERN).holders().filter($$1 -> $$0.is(((TrimPattern)$$1.value()).templateItem())).findFirst();
/*    */   }
/*    */   
/*    */   private static void register(BootstapContext<TrimPattern> $$0, Item $$1, ResourceKey<TrimPattern> $$2) {
/* 60 */     TrimPattern $$3 = new TrimPattern($$2.location(), BuiltInRegistries.ITEM.wrapAsHolder($$1), (Component)Component.translatable(Util.makeDescriptionId("trim_pattern", $$2.location())), false);
/* 61 */     $$0.register($$2, $$3);
/*    */   }
/*    */   
/*    */   private static ResourceKey<TrimPattern> registryKey(String $$0) {
/* 65 */     return ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\armortrim\TrimPatterns.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */