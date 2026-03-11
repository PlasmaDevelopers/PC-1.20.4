/*    */ package net.minecraft.world.level.levelgen.structure.pools.alias;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface PoolAliasLookup {
/*    */   public static final PoolAliasLookup EMPTY;
/*    */   
/*    */   static {
/* 15 */     EMPTY = ($$0 -> $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   static PoolAliasLookup create(List<PoolAliasBinding> $$0, BlockPos $$1, long $$2) {
/* 20 */     if ($$0.isEmpty()) {
/* 21 */       return EMPTY;
/*    */     }
/*    */     
/* 24 */     RandomSource $$3 = RandomSource.create($$2).forkPositional().at($$1);
/* 25 */     ImmutableMap.Builder<ResourceKey<StructureTemplatePool>, ResourceKey<StructureTemplatePool>> $$4 = ImmutableMap.builder();
/* 26 */     $$0.forEach($$2 -> { Objects.requireNonNull($$1); $$2.forEachResolved($$0, $$1::put);
/* 27 */         }); ImmutableMap immutableMap = $$4.build();
/*    */     
/* 29 */     return $$1 -> (ResourceKey)Objects.<ResourceKey>requireNonNull((ResourceKey)$$0.getOrDefault($$1, $$1), ());
/*    */   }
/*    */   
/*    */   ResourceKey<StructureTemplatePool> lookup(ResourceKey<StructureTemplatePool> paramResourceKey);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\alias\PoolAliasLookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */