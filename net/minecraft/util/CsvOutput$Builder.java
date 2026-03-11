/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.List;
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
/*    */ public class Builder
/*    */ {
/* 46 */   private final List<String> headers = Lists.newArrayList();
/*    */   
/*    */   public Builder addColumn(String $$0) {
/* 49 */     this.headers.add($$0);
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   public CsvOutput build(Writer $$0) throws IOException {
/* 54 */     return new CsvOutput($$0, this.headers);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\CsvOutput$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */