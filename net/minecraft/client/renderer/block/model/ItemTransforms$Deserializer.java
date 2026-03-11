/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
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
/*    */ public class Deserializer
/*    */   implements JsonDeserializer<ItemTransforms>
/*    */ {
/*    */   public ItemTransforms deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 71 */     JsonObject $$3 = $$0.getAsJsonObject();
/*    */     
/* 73 */     ItemTransform $$4 = getTransform($$2, $$3, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
/* 74 */     ItemTransform $$5 = getTransform($$2, $$3, ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
/* 75 */     if ($$5 == ItemTransform.NO_TRANSFORM) {
/* 76 */       $$5 = $$4;
/*    */     }
/* 78 */     ItemTransform $$6 = getTransform($$2, $$3, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND);
/* 79 */     ItemTransform $$7 = getTransform($$2, $$3, ItemDisplayContext.FIRST_PERSON_LEFT_HAND);
/* 80 */     if ($$7 == ItemTransform.NO_TRANSFORM) {
/* 81 */       $$7 = $$6;
/*    */     }
/* 83 */     ItemTransform $$8 = getTransform($$2, $$3, ItemDisplayContext.HEAD);
/* 84 */     ItemTransform $$9 = getTransform($$2, $$3, ItemDisplayContext.GUI);
/* 85 */     ItemTransform $$10 = getTransform($$2, $$3, ItemDisplayContext.GROUND);
/* 86 */     ItemTransform $$11 = getTransform($$2, $$3, ItemDisplayContext.FIXED);
/*    */     
/* 88 */     return new ItemTransforms($$5, $$4, $$7, $$6, $$8, $$9, $$10, $$11);
/*    */   }
/*    */   
/*    */   private ItemTransform getTransform(JsonDeserializationContext $$0, JsonObject $$1, ItemDisplayContext $$2) {
/* 92 */     String $$3 = $$2.getSerializedName();
/* 93 */     if ($$1.has($$3)) {
/* 94 */       return (ItemTransform)$$0.deserialize($$1.get($$3), ItemTransform.class);
/*    */     }
/* 96 */     return ItemTransform.NO_TRANSFORM;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemTransforms$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */