/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
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
/*    */ class null
/*    */   implements JsonDeserializer<Component>
/*    */ {
/*    */   public MutableComponent deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 31 */     if ($$0.isJsonPrimitive())
/*    */     {
/* 33 */       return Component.literal($$0.getAsString()); } 
/* 34 */     if ($$0.isJsonArray()) {
/*    */       
/* 36 */       JsonArray $$3 = $$0.getAsJsonArray();
/* 37 */       MutableComponent $$4 = null;
/*    */       
/* 39 */       for (JsonElement $$5 : $$3) {
/* 40 */         MutableComponent $$6 = deserialize($$5, $$5.getClass(), $$2);
/* 41 */         if ($$4 == null) {
/* 42 */           $$4 = $$6; continue;
/*    */         } 
/* 44 */         $$4.append((Component)$$6);
/*    */       } 
/*    */ 
/*    */       
/* 48 */       return $$4;
/*    */     } 
/* 50 */     throw new JsonParseException("Don't know how to turn " + $$0 + " into a Component");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\BlockEntitySignTextStrictJsonFix$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */