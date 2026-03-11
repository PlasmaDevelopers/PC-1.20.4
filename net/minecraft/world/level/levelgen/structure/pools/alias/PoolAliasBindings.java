/*    */ package net.minecraft.world.level.levelgen.structure.pools.alias;
/*    */ 
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.data.worldgen.Pools;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ public class PoolAliasBindings {
/*    */   public static Codec<? extends PoolAliasBinding> bootstrap(Registry<Codec<? extends PoolAliasBinding>> $$0) {
/* 16 */     Registry.register($$0, "random", Random.CODEC);
/* 17 */     Registry.register($$0, "random_group", RandomGroup.CODEC);
/* 18 */     return (Codec<? extends PoolAliasBinding>)Registry.register($$0, "direct", Direct.CODEC);
/*    */   }
/*    */   
/*    */   public static void registerTargetsAsPools(BootstapContext<StructureTemplatePool> $$0, Holder<StructureTemplatePool> $$1, List<PoolAliasBinding> $$2) {
/* 22 */     $$2.stream()
/* 23 */       .flatMap(PoolAliasBinding::allTargets)
/* 24 */       .map($$0 -> $$0.location().getPath())
/* 25 */       .forEach($$2 -> Pools.register($$0, $$2, new StructureTemplatePool($$1, List.of(Pair.of(StructurePoolElement.single($$2), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\alias\PoolAliasBindings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */