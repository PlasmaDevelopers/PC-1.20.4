/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.TreeMap;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class MultiVariantGenerator implements BlockStateGenerator {
/*    */   private final Block block;
/*    */   private final List<Variant> baseVariants;
/* 22 */   private final Set<Property<?>> seenProperties = Sets.newHashSet();
/* 23 */   private final List<PropertyDispatch> declaredPropertySets = Lists.newArrayList();
/*    */   
/*    */   private MultiVariantGenerator(Block $$0, List<Variant> $$1) {
/* 26 */     this.block = $$0;
/* 27 */     this.baseVariants = $$1;
/*    */   }
/*    */   
/*    */   public MultiVariantGenerator with(PropertyDispatch $$0) {
/* 31 */     $$0.getDefinedProperties().forEach($$0 -> {
/*    */           if (this.block.getStateDefinition().getProperty($$0.getName()) != $$0) {
/*    */             throw new IllegalStateException("Property " + $$0 + " is not defined for block " + this.block);
/*    */           }
/*    */           
/*    */           if (!this.seenProperties.add($$0)) {
/*    */             throw new IllegalStateException("Values of property " + $$0 + " already defined for block " + this.block);
/*    */           }
/*    */         });
/* 40 */     this.declaredPropertySets.add($$0);
/* 41 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement get() {
/* 46 */     Stream<Pair<Selector, List<Variant>>> $$0 = Stream.of(Pair.of(Selector.empty(), this.baseVariants));
/*    */     
/* 48 */     for (PropertyDispatch $$1 : this.declaredPropertySets) {
/* 49 */       Map<Selector, List<Variant>> $$2 = $$1.getEntries();
/* 50 */       $$0 = $$0.flatMap($$1 -> $$0.entrySet().stream().map(()));
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     Map<String, JsonElement> $$3 = new TreeMap<>();
/* 58 */     $$0.forEach($$1 -> $$0.put(((Selector)$$1.getFirst()).getKey(), Variant.convertList((List<Variant>)$$1.getSecond())));
/*    */ 
/*    */ 
/*    */     
/* 62 */     JsonObject $$4 = new JsonObject();
/* 63 */     $$4.add("variants", (JsonElement)Util.make(new JsonObject(), $$1 -> { Objects.requireNonNull($$1); $$0.forEach($$1::add);
/* 64 */           })); return (JsonElement)$$4;
/*    */   }
/*    */   
/*    */   private static List<Variant> mergeVariants(List<Variant> $$0, List<Variant> $$1) {
/* 68 */     ImmutableList.Builder<Variant> $$2 = ImmutableList.builder();
/*    */     
/* 70 */     $$0.forEach($$2 -> $$0.forEach(()));
/* 71 */     return (List<Variant>)$$2.build();
/*    */   }
/*    */ 
/*    */   
/*    */   public Block getBlock() {
/* 76 */     return this.block;
/*    */   }
/*    */   
/*    */   public static MultiVariantGenerator multiVariant(Block $$0) {
/* 80 */     return new MultiVariantGenerator($$0, (List<Variant>)ImmutableList.of(Variant.variant()));
/*    */   }
/*    */   
/*    */   public static MultiVariantGenerator multiVariant(Block $$0, Variant $$1) {
/* 84 */     return new MultiVariantGenerator($$0, (List<Variant>)ImmutableList.of($$1));
/*    */   }
/*    */   
/*    */   public static MultiVariantGenerator multiVariant(Block $$0, Variant... $$1) {
/* 88 */     return new MultiVariantGenerator($$0, (List<Variant>)ImmutableList.copyOf((Object[])$$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\MultiVariantGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */