/*    */ package net.minecraft.util.datafix;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComponentDataFixUtils
/*    */ {
/* 15 */   private static final String EMPTY_CONTENTS = createTextComponentJson("");
/*    */   
/*    */   public static <T> Dynamic<T> createPlainTextComponent(DynamicOps<T> $$0, String $$1) {
/* 18 */     String $$2 = createTextComponentJson($$1);
/* 19 */     return new Dynamic($$0, $$0.createString($$2));
/*    */   }
/*    */   
/*    */   public static <T> Dynamic<T> createEmptyComponent(DynamicOps<T> $$0) {
/* 23 */     return new Dynamic($$0, $$0.createString(EMPTY_CONTENTS));
/*    */   }
/*    */   
/*    */   private static String createTextComponentJson(String $$0) {
/* 27 */     JsonObject $$1 = new JsonObject();
/* 28 */     $$1.addProperty("text", $$0);
/* 29 */     return GsonHelper.toStableString((JsonElement)$$1);
/*    */   }
/*    */   
/*    */   public static <T> Dynamic<T> createTranslatableComponent(DynamicOps<T> $$0, String $$1) {
/* 33 */     JsonObject $$2 = new JsonObject();
/* 34 */     $$2.addProperty("translate", $$1);
/* 35 */     return new Dynamic($$0, $$0.createString(GsonHelper.toStableString((JsonElement)$$2)));
/*    */   }
/*    */   
/*    */   public static <T> Dynamic<T> wrapLiteralStringAsComponent(Dynamic<T> $$0) {
/* 39 */     return (Dynamic<T>)DataFixUtils.orElse($$0.asString().map($$1 -> createPlainTextComponent($$0.getOps(), $$1)).result(), $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\ComponentDataFixUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */