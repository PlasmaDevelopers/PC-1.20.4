/*     */ package net.minecraft.client.renderer.block.model.multipart;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Streams;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.client.renderer.block.model.MultiVariant;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ 
/*     */ public class Selector
/*     */ {
/*     */   private final Condition condition;
/*     */   private final MultiVariant variant;
/*     */   
/*     */   public Selector(Condition $$0, MultiVariant $$1) {
/*  28 */     if ($$0 == null) {
/*  29 */       throw new IllegalArgumentException("Missing condition for selector");
/*     */     }
/*  31 */     if ($$1 == null) {
/*  32 */       throw new IllegalArgumentException("Missing variant for selector");
/*     */     }
/*  34 */     this.condition = $$0;
/*  35 */     this.variant = $$1;
/*     */   }
/*     */   
/*     */   public MultiVariant getVariant() {
/*  39 */     return this.variant;
/*     */   }
/*     */   
/*     */   public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> $$0) {
/*  43 */     return this.condition.getPredicate($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  48 */     return (this == $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  53 */     return System.identityHashCode(this);
/*     */   }
/*     */   
/*     */   public static class Deserializer
/*     */     implements JsonDeserializer<Selector> {
/*     */     public Selector deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/*  59 */       JsonObject $$3 = $$0.getAsJsonObject();
/*     */       
/*  61 */       return new Selector(getSelector($$3), (MultiVariant)$$2.deserialize($$3.get("apply"), MultiVariant.class));
/*     */     }
/*     */     
/*     */     private Condition getSelector(JsonObject $$0) {
/*  65 */       if ($$0.has("when")) {
/*  66 */         return getCondition(GsonHelper.getAsJsonObject($$0, "when"));
/*     */       }
/*     */       
/*  69 */       return Condition.TRUE;
/*     */     }
/*     */     
/*     */     @VisibleForTesting
/*     */     static Condition getCondition(JsonObject $$0) {
/*  74 */       Set<Map.Entry<String, JsonElement>> $$1 = $$0.entrySet();
/*     */       
/*  76 */       if ($$1.isEmpty()) {
/*  77 */         throw new JsonParseException("No elements found in selector");
/*     */       }
/*     */       
/*  80 */       if ($$1.size() == 1) {
/*  81 */         if ($$0.has("OR")) {
/*     */ 
/*     */           
/*  84 */           List<Condition> $$2 = (List<Condition>)Streams.stream((Iterable)GsonHelper.getAsJsonArray($$0, "OR")).map($$0 -> getCondition($$0.getAsJsonObject())).collect(Collectors.toList());
/*  85 */           return new OrCondition($$2);
/*  86 */         }  if ($$0.has("AND")) {
/*     */ 
/*     */           
/*  89 */           List<Condition> $$3 = (List<Condition>)Streams.stream((Iterable)GsonHelper.getAsJsonArray($$0, "AND")).map($$0 -> getCondition($$0.getAsJsonObject())).collect(Collectors.toList());
/*  90 */           return new AndCondition($$3);
/*     */         } 
/*  92 */         return getKeyValueCondition($$1.iterator().next());
/*     */       } 
/*     */       
/*  95 */       return new AndCondition((Iterable<? extends Condition>)$$1.stream().map(Deserializer::getKeyValueCondition).collect(Collectors.toList()));
/*     */     }
/*     */ 
/*     */     
/*     */     private static Condition getKeyValueCondition(Map.Entry<String, JsonElement> $$0) {
/* 100 */       return new KeyValueCondition($$0.getKey(), ((JsonElement)$$0.getValue()).getAsString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\multipart\Selector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */