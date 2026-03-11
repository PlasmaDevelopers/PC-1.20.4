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
/*    */ public class ItemTransforms
/*    */ {
/* 13 */   public static final ItemTransforms NO_TRANSFORMS = new ItemTransforms();
/*    */   
/*    */   public final ItemTransform thirdPersonLeftHand;
/*    */   public final ItemTransform thirdPersonRightHand;
/*    */   public final ItemTransform firstPersonLeftHand;
/*    */   public final ItemTransform firstPersonRightHand;
/*    */   public final ItemTransform head;
/*    */   public final ItemTransform gui;
/*    */   public final ItemTransform ground;
/*    */   public final ItemTransform fixed;
/*    */   
/*    */   private ItemTransforms() {
/* 25 */     this(ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM);
/*    */   }
/*    */   
/*    */   public ItemTransforms(ItemTransforms $$0) {
/* 29 */     this.thirdPersonLeftHand = $$0.thirdPersonLeftHand;
/* 30 */     this.thirdPersonRightHand = $$0.thirdPersonRightHand;
/* 31 */     this.firstPersonLeftHand = $$0.firstPersonLeftHand;
/* 32 */     this.firstPersonRightHand = $$0.firstPersonRightHand;
/* 33 */     this.head = $$0.head;
/* 34 */     this.gui = $$0.gui;
/* 35 */     this.ground = $$0.ground;
/* 36 */     this.fixed = $$0.fixed;
/*    */   }
/*    */   
/*    */   public ItemTransforms(ItemTransform $$0, ItemTransform $$1, ItemTransform $$2, ItemTransform $$3, ItemTransform $$4, ItemTransform $$5, ItemTransform $$6, ItemTransform $$7) {
/* 40 */     this.thirdPersonLeftHand = $$0;
/* 41 */     this.thirdPersonRightHand = $$1;
/* 42 */     this.firstPersonLeftHand = $$2;
/* 43 */     this.firstPersonRightHand = $$3;
/* 44 */     this.head = $$4;
/* 45 */     this.gui = $$5;
/* 46 */     this.ground = $$6;
/* 47 */     this.fixed = $$7;
/*    */   }
/*    */   
/*    */   public ItemTransform getTransform(ItemDisplayContext $$0) {
/* 51 */     switch ($$0) { case THIRD_PERSON_LEFT_HAND: case THIRD_PERSON_RIGHT_HAND: case FIRST_PERSON_LEFT_HAND: case FIRST_PERSON_RIGHT_HAND: case HEAD: case GUI: case GROUND: case FIXED:  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 60 */       ItemTransform.NO_TRANSFORM;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasTransform(ItemDisplayContext $$0) {
/* 65 */     return (getTransform($$0) != ItemTransform.NO_TRANSFORM);
/*    */   }
/*    */   
/*    */   protected static class Deserializer
/*    */     implements JsonDeserializer<ItemTransforms> {
/*    */     public ItemTransforms deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 71 */       JsonObject $$3 = $$0.getAsJsonObject();
/*    */       
/* 73 */       ItemTransform $$4 = getTransform($$2, $$3, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
/* 74 */       ItemTransform $$5 = getTransform($$2, $$3, ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
/* 75 */       if ($$5 == ItemTransform.NO_TRANSFORM) {
/* 76 */         $$5 = $$4;
/*    */       }
/* 78 */       ItemTransform $$6 = getTransform($$2, $$3, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND);
/* 79 */       ItemTransform $$7 = getTransform($$2, $$3, ItemDisplayContext.FIRST_PERSON_LEFT_HAND);
/* 80 */       if ($$7 == ItemTransform.NO_TRANSFORM) {
/* 81 */         $$7 = $$6;
/*    */       }
/* 83 */       ItemTransform $$8 = getTransform($$2, $$3, ItemDisplayContext.HEAD);
/* 84 */       ItemTransform $$9 = getTransform($$2, $$3, ItemDisplayContext.GUI);
/* 85 */       ItemTransform $$10 = getTransform($$2, $$3, ItemDisplayContext.GROUND);
/* 86 */       ItemTransform $$11 = getTransform($$2, $$3, ItemDisplayContext.FIXED);
/*    */       
/* 88 */       return new ItemTransforms($$5, $$4, $$7, $$6, $$8, $$9, $$10, $$11);
/*    */     }
/*    */     
/*    */     private ItemTransform getTransform(JsonDeserializationContext $$0, JsonObject $$1, ItemDisplayContext $$2) {
/* 92 */       String $$3 = $$2.getSerializedName();
/* 93 */       if ($$1.has($$3)) {
/* 94 */         return (ItemTransform)$$0.deserialize($$1.get($$3), ItemTransform.class);
/*    */       }
/* 96 */       return ItemTransform.NO_TRANSFORM;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemTransforms.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */