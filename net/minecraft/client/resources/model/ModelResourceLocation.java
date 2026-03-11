/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.util.Locale;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelResourceLocation
/*    */   extends ResourceLocation
/*    */ {
/*    */   @VisibleForTesting
/*    */   static final char VARIANT_SEPARATOR = '#';
/*    */   private final String variant;
/*    */   
/*    */   private ModelResourceLocation(String $$0, String $$1, String $$2, @Nullable ResourceLocation.Dummy $$3) {
/* 19 */     super($$0, $$1, $$3);
/* 20 */     this.variant = $$2;
/*    */   }
/*    */   
/*    */   public ModelResourceLocation(String $$0, String $$1, String $$2) {
/* 24 */     super($$0, $$1);
/* 25 */     this.variant = lowercaseVariant($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelResourceLocation(ResourceLocation $$0, String $$1) {
/* 30 */     this($$0.getNamespace(), $$0.getPath(), lowercaseVariant($$1), null);
/*    */   }
/*    */   
/*    */   public static ModelResourceLocation vanilla(String $$0, String $$1) {
/* 34 */     return new ModelResourceLocation("minecraft", $$0, $$1);
/*    */   }
/*    */   
/*    */   private static String lowercaseVariant(String $$0) {
/* 38 */     return $$0.toLowerCase(Locale.ROOT);
/*    */   }
/*    */   
/*    */   public String getVariant() {
/* 42 */     return this.variant;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 47 */     if (this == $$0) {
/* 48 */       return true;
/*    */     }
/* 50 */     if ($$0 instanceof ModelResourceLocation && 
/* 51 */       super.equals($$0)) {
/* 52 */       ModelResourceLocation $$1 = (ModelResourceLocation)$$0;
/*    */       
/* 54 */       return this.variant.equals($$1.variant);
/*    */     } 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     return 31 * super.hashCode() + this.variant.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return super.toString() + "#" + super.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\ModelResourceLocation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */