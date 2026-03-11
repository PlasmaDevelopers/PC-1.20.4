/*    */ package net.minecraft.world.entity.npc;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.Biomes;
/*    */ 
/*    */ public final class VillagerType {
/* 16 */   public static final VillagerType DESERT = register("desert");
/* 17 */   public static final VillagerType JUNGLE = register("jungle");
/* 18 */   public static final VillagerType PLAINS = register("plains");
/* 19 */   public static final VillagerType SAVANNA = register("savanna");
/* 20 */   public static final VillagerType SNOW = register("snow");
/* 21 */   public static final VillagerType SWAMP = register("swamp");
/* 22 */   public static final VillagerType TAIGA = register("taiga");
/*    */   private final String name;
/*    */   private static final Map<ResourceKey<Biome>, VillagerType> BY_BIOME;
/*    */   
/*    */   private VillagerType(String $$0) {
/* 27 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return this.name;
/*    */   }
/*    */   
/*    */   private static VillagerType register(String $$0) {
/* 36 */     return (VillagerType)Registry.register((Registry)BuiltInRegistries.VILLAGER_TYPE, new ResourceLocation($$0), new VillagerType($$0));
/*    */   }
/*    */   static {
/* 39 */     BY_BIOME = (Map<ResourceKey<Biome>, VillagerType>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */           $$0.put(Biomes.BADLANDS, DESERT);
/*    */           $$0.put(Biomes.DESERT, DESERT);
/*    */           $$0.put(Biomes.ERODED_BADLANDS, DESERT);
/*    */           $$0.put(Biomes.WOODED_BADLANDS, DESERT);
/*    */           $$0.put(Biomes.BAMBOO_JUNGLE, JUNGLE);
/*    */           $$0.put(Biomes.JUNGLE, JUNGLE);
/*    */           $$0.put(Biomes.SPARSE_JUNGLE, JUNGLE);
/*    */           $$0.put(Biomes.SAVANNA_PLATEAU, SAVANNA);
/*    */           $$0.put(Biomes.SAVANNA, SAVANNA);
/*    */           $$0.put(Biomes.WINDSWEPT_SAVANNA, SAVANNA);
/*    */           $$0.put(Biomes.DEEP_FROZEN_OCEAN, SNOW);
/*    */           $$0.put(Biomes.FROZEN_OCEAN, SNOW);
/*    */           $$0.put(Biomes.FROZEN_RIVER, SNOW);
/*    */           $$0.put(Biomes.ICE_SPIKES, SNOW);
/*    */           $$0.put(Biomes.SNOWY_BEACH, SNOW);
/*    */           $$0.put(Biomes.SNOWY_TAIGA, SNOW);
/*    */           $$0.put(Biomes.SNOWY_PLAINS, SNOW);
/*    */           $$0.put(Biomes.GROVE, SNOW);
/*    */           $$0.put(Biomes.SNOWY_SLOPES, SNOW);
/*    */           $$0.put(Biomes.FROZEN_PEAKS, SNOW);
/*    */           $$0.put(Biomes.JAGGED_PEAKS, SNOW);
/*    */           $$0.put(Biomes.SWAMP, SWAMP);
/*    */           $$0.put(Biomes.MANGROVE_SWAMP, SWAMP);
/*    */           $$0.put(Biomes.OLD_GROWTH_SPRUCE_TAIGA, TAIGA);
/*    */           $$0.put(Biomes.OLD_GROWTH_PINE_TAIGA, TAIGA);
/*    */           $$0.put(Biomes.WINDSWEPT_GRAVELLY_HILLS, TAIGA);
/*    */           $$0.put(Biomes.WINDSWEPT_HILLS, TAIGA);
/*    */           $$0.put(Biomes.TAIGA, TAIGA);
/*    */           $$0.put(Biomes.WINDSWEPT_FOREST, TAIGA);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static VillagerType byBiome(Holder<Biome> $$0) {
/* 79 */     Objects.requireNonNull(BY_BIOME); return $$0.unwrapKey().map(BY_BIOME::get).orElse(PLAINS);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\VillagerType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */