/*    */ package net.minecraft.data.models.model;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class DelegatedModel
/*    */   implements Supplier<JsonElement> {
/*    */   private final ResourceLocation parent;
/*    */   
/*    */   public DelegatedModel(ResourceLocation $$0) {
/* 13 */     this.parent = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement get() {
/* 18 */     JsonObject $$0 = new JsonObject();
/* 19 */     $$0.addProperty("parent", this.parent.toString());
/* 20 */     return (JsonElement)$$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\DelegatedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */