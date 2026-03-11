/*    */ package net.minecraft.util.datafix.schemas;
/*    */ import com.mojang.datafixers.DSL;
/*    */ import com.mojang.datafixers.schemas.Schema;
/*    */ import com.mojang.datafixers.types.Type;
/*    */ import com.mojang.datafixers.types.templates.Const;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.codecs.PrimitiveCodec;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class NamespacedSchema extends Schema {
/*    */   public NamespacedSchema(int $$0, Schema $$1) {
/* 14 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public static String ensureNamespaced(String $$0) {
/* 18 */     ResourceLocation $$1 = ResourceLocation.tryParse($$0);
/* 19 */     if ($$1 != null) {
/* 20 */       return $$1.toString();
/*    */     }
/* 22 */     return $$0;
/*    */   }
/*    */   
/* 25 */   public static final PrimitiveCodec<String> NAMESPACED_STRING_CODEC = new PrimitiveCodec<String>()
/*    */     {
/*    */       public <T> DataResult<String> read(DynamicOps<T> $$0, T $$1) {
/* 28 */         return $$0
/* 29 */           .getStringValue($$1)
/* 30 */           .map(NamespacedSchema::ensureNamespaced);
/*    */       }
/*    */ 
/*    */       
/*    */       public <T> T write(DynamicOps<T> $$0, String $$1) {
/* 35 */         return (T)$$0.createString($$1);
/*    */       }
/*    */ 
/*    */       
/*    */       public String toString() {
/* 40 */         return "NamespacedString";
/*    */       }
/*    */     };
/*    */   
/* 44 */   private static final Type<String> NAMESPACED_STRING = (Type<String>)new Const.PrimitiveType((Codec)NAMESPACED_STRING_CODEC);
/*    */   
/*    */   public static Type<String> namespacedString() {
/* 47 */     return NAMESPACED_STRING;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type<?> getChoiceType(DSL.TypeReference $$0, String $$1) {
/* 52 */     return super.getChoiceType($$0, ensureNamespaced($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\NamespacedSchema.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */