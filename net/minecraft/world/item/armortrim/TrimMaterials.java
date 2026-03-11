/*    */ package net.minecraft.world.item.armortrim;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ArmorMaterials;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class TrimMaterials
/*    */ {
/* 21 */   public static final ResourceKey<TrimMaterial> QUARTZ = registryKey("quartz");
/* 22 */   public static final ResourceKey<TrimMaterial> IRON = registryKey("iron");
/* 23 */   public static final ResourceKey<TrimMaterial> NETHERITE = registryKey("netherite");
/* 24 */   public static final ResourceKey<TrimMaterial> REDSTONE = registryKey("redstone");
/* 25 */   public static final ResourceKey<TrimMaterial> COPPER = registryKey("copper");
/* 26 */   public static final ResourceKey<TrimMaterial> GOLD = registryKey("gold");
/* 27 */   public static final ResourceKey<TrimMaterial> EMERALD = registryKey("emerald");
/* 28 */   public static final ResourceKey<TrimMaterial> DIAMOND = registryKey("diamond");
/* 29 */   public static final ResourceKey<TrimMaterial> LAPIS = registryKey("lapis");
/* 30 */   public static final ResourceKey<TrimMaterial> AMETHYST = registryKey("amethyst");
/*    */   
/*    */   public static void bootstrap(BootstapContext<TrimMaterial> $$0) {
/* 33 */     register($$0, QUARTZ, Items.QUARTZ, Style.EMPTY.withColor(14931140), 0.1F);
/* 34 */     register($$0, IRON, Items.IRON_INGOT, Style.EMPTY.withColor(15527148), 0.2F, Map.of(ArmorMaterials.IRON, "iron_darker"));
/* 35 */     register($$0, NETHERITE, Items.NETHERITE_INGOT, Style.EMPTY.withColor(6445145), 0.3F, Map.of(ArmorMaterials.NETHERITE, "netherite_darker"));
/* 36 */     register($$0, REDSTONE, Items.REDSTONE, Style.EMPTY.withColor(9901575), 0.4F);
/* 37 */     register($$0, COPPER, Items.COPPER_INGOT, Style.EMPTY.withColor(11823181), 0.5F);
/* 38 */     register($$0, GOLD, Items.GOLD_INGOT, Style.EMPTY.withColor(14594349), 0.6F, Map.of(ArmorMaterials.GOLD, "gold_darker"));
/* 39 */     register($$0, EMERALD, Items.EMERALD, Style.EMPTY.withColor(1155126), 0.7F);
/* 40 */     register($$0, DIAMOND, Items.DIAMOND, Style.EMPTY.withColor(7269586), 0.8F, Map.of(ArmorMaterials.DIAMOND, "diamond_darker"));
/* 41 */     register($$0, LAPIS, Items.LAPIS_LAZULI, Style.EMPTY.withColor(4288151), 0.9F);
/* 42 */     register($$0, AMETHYST, Items.AMETHYST_SHARD, Style.EMPTY.withColor(10116294), 1.0F);
/*    */   }
/*    */   
/*    */   public static Optional<Holder.Reference<TrimMaterial>> getFromIngredient(RegistryAccess $$0, ItemStack $$1) {
/* 46 */     return $$0.registryOrThrow(Registries.TRIM_MATERIAL).holders().filter($$1 -> $$0.is(((TrimMaterial)$$1.value()).ingredient())).findFirst();
/*    */   }
/*    */   
/*    */   private static void register(BootstapContext<TrimMaterial> $$0, ResourceKey<TrimMaterial> $$1, Item $$2, Style $$3, float $$4) {
/* 50 */     register($$0, $$1, $$2, $$3, $$4, Map.of());
/*    */   }
/*    */   
/*    */   private static void register(BootstapContext<TrimMaterial> $$0, ResourceKey<TrimMaterial> $$1, Item $$2, Style $$3, float $$4, Map<ArmorMaterials, String> $$5) {
/* 54 */     TrimMaterial $$6 = TrimMaterial.create($$1.location().getPath(), $$2, $$4, (Component)Component.translatable(Util.makeDescriptionId("trim_material", $$1.location())).withStyle($$3), $$5);
/* 55 */     $$0.register($$1, $$6);
/*    */   }
/*    */   
/*    */   private static ResourceKey<TrimMaterial> registryKey(String $$0) {
/* 59 */     return ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\armortrim\TrimMaterials.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */