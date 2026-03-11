/*     */ package net.minecraft.data.models.blockstates;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ 
/*     */ public class MultiPartGenerator implements BlockStateGenerator {
/*     */   private final Block block;
/*  17 */   private final List<Entry> parts = Lists.newArrayList();
/*     */   
/*     */   private MultiPartGenerator(Block $$0) {
/*  20 */     this.block = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/*  25 */     return this.block;
/*     */   }
/*     */   
/*     */   public static MultiPartGenerator multiPart(Block $$0) {
/*  29 */     return new MultiPartGenerator($$0);
/*     */   }
/*     */   
/*     */   public MultiPartGenerator with(List<Variant> $$0) {
/*  33 */     this.parts.add(new Entry($$0));
/*  34 */     return this;
/*     */   }
/*     */   
/*     */   public MultiPartGenerator with(Variant $$0) {
/*  38 */     return with((List<Variant>)ImmutableList.of($$0));
/*     */   }
/*     */   
/*     */   public MultiPartGenerator with(Condition $$0, List<Variant> $$1) {
/*  42 */     this.parts.add(new ConditionalEntry($$0, $$1));
/*  43 */     return this;
/*     */   }
/*     */   
/*     */   public MultiPartGenerator with(Condition $$0, Variant... $$1) {
/*  47 */     return with($$0, (List<Variant>)ImmutableList.copyOf((Object[])$$1));
/*     */   }
/*     */   
/*     */   public MultiPartGenerator with(Condition $$0, Variant $$1) {
/*  51 */     return with($$0, (List<Variant>)ImmutableList.of($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement get() {
/*  56 */     StateDefinition<Block, BlockState> $$0 = this.block.getStateDefinition();
/*  57 */     this.parts.forEach($$1 -> $$1.validate($$0));
/*     */     
/*  59 */     JsonArray $$1 = new JsonArray();
/*  60 */     Objects.requireNonNull($$1); this.parts.stream().map(Entry::get).forEach($$1::add);
/*     */     
/*  62 */     JsonObject $$2 = new JsonObject();
/*  63 */     $$2.add("multipart", (JsonElement)$$1);
/*  64 */     return (JsonElement)$$2;
/*     */   }
/*     */   
/*     */   private static class Entry implements Supplier<JsonElement> {
/*     */     private final List<Variant> variants;
/*     */     
/*     */     Entry(List<Variant> $$0) {
/*  71 */       this.variants = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate(StateDefinition<?, ?> $$0) {}
/*     */ 
/*     */     
/*     */     public void decorate(JsonObject $$0) {}
/*     */ 
/*     */     
/*     */     public JsonElement get() {
/*  82 */       JsonObject $$0 = new JsonObject();
/*  83 */       decorate($$0);
/*  84 */       $$0.add("apply", Variant.convertList(this.variants));
/*  85 */       return (JsonElement)$$0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ConditionalEntry extends Entry {
/*     */     private final Condition condition;
/*     */     
/*     */     ConditionalEntry(Condition $$0, List<Variant> $$1) {
/*  93 */       super($$1);
/*  94 */       this.condition = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate(StateDefinition<?, ?> $$0) {
/*  99 */       this.condition.validate($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void decorate(JsonObject $$0) {
/* 104 */       $$0.add("when", this.condition.get());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\MultiPartGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */