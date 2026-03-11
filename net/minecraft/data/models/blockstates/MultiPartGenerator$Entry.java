/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Entry
/*    */   implements Supplier<JsonElement>
/*    */ {
/*    */   private final List<Variant> variants;
/*    */   
/*    */   Entry(List<Variant> $$0) {
/* 71 */     this.variants = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(StateDefinition<?, ?> $$0) {}
/*    */ 
/*    */   
/*    */   public void decorate(JsonObject $$0) {}
/*    */ 
/*    */   
/*    */   public JsonElement get() {
/* 82 */     JsonObject $$0 = new JsonObject();
/* 83 */     decorate($$0);
/* 84 */     $$0.add("apply", Variant.convertList(this.variants));
/* 85 */     return (JsonElement)$$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\MultiPartGenerator$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */