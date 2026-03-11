/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
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
/*    */ public class CompositeCondition
/*    */   implements Condition
/*    */ {
/*    */   private final Condition.Operation operation;
/*    */   private final List<Condition> subconditions;
/*    */   
/*    */   CompositeCondition(Condition.Operation $$0, List<Condition> $$1) {
/* 37 */     this.operation = $$0;
/* 38 */     this.subconditions = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(StateDefinition<?, ?> $$0) {
/* 43 */     this.subconditions.forEach($$1 -> $$1.validate($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement get() {
/* 48 */     JsonArray $$0 = new JsonArray();
/* 49 */     Objects.requireNonNull($$0); this.subconditions.stream().map(Supplier::get).forEach($$0::add);
/*    */     
/* 51 */     JsonObject $$1 = new JsonObject();
/* 52 */     $$1.add(this.operation.id, (JsonElement)$$0);
/* 53 */     return (JsonElement)$$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\Condition$CompositeCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */