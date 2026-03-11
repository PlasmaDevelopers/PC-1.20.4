/*    */ package net.minecraft.world.level.levelgen.structure.pools.alias;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.data.worldgen.Pools;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*    */ import net.minecraft.util.random.WeightedEntry;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ 
/*    */ public interface PoolAliasBinding
/*    */ {
/* 19 */   public static final Codec<PoolAliasBinding> CODEC = BuiltInRegistries.POOL_ALIAS_BINDING_TYPE.byNameCodec().dispatch(PoolAliasBinding::codec, Function.identity());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static Direct direct(String $$0, String $$1) {
/* 29 */     return direct(Pools.createKey($$0), Pools.createKey($$1));
/*    */   }
/*    */   
/*    */   static Direct direct(ResourceKey<StructureTemplatePool> $$0, ResourceKey<StructureTemplatePool> $$1) {
/* 33 */     return new Direct($$0, $$1);
/*    */   }
/*    */   
/*    */   static Random random(String $$0, SimpleWeightedRandomList<String> $$1) {
/* 37 */     SimpleWeightedRandomList.Builder<ResourceKey<StructureTemplatePool>> $$2 = SimpleWeightedRandomList.builder();
/* 38 */     $$1.unwrap().forEach($$1 -> $$0.add(Pools.createKey((String)$$1.getData()), $$1.getWeight().asInt()));
/*    */     
/* 40 */     return random(Pools.createKey($$0), $$2.build());
/*    */   }
/*    */   
/*    */   static Random random(ResourceKey<StructureTemplatePool> $$0, SimpleWeightedRandomList<ResourceKey<StructureTemplatePool>> $$1) {
/* 44 */     return new Random($$0, $$1);
/*    */   }
/*    */   
/*    */   static RandomGroup randomGroup(SimpleWeightedRandomList<List<PoolAliasBinding>> $$0) {
/* 48 */     return new RandomGroup($$0);
/*    */   }
/*    */   
/*    */   void forEachResolved(RandomSource paramRandomSource, BiConsumer<ResourceKey<StructureTemplatePool>, ResourceKey<StructureTemplatePool>> paramBiConsumer);
/*    */   
/*    */   Stream<ResourceKey<StructureTemplatePool>> allTargets();
/*    */   
/*    */   Codec<? extends PoolAliasBinding> codec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\alias\PoolAliasBinding.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */