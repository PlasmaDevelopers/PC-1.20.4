/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.StringUtils;
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
/*    */ public class Entry
/*    */ {
/*    */   @Nullable
/*    */   String filename;
/* 55 */   private final List<String> jsonKeys = Lists.newArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void addJsonKey(String $$0) {
/* 61 */     this.jsonKeys.add(0, $$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getFilename() {
/* 66 */     return this.filename;
/*    */   }
/*    */   
/*    */   public String getJsonKeys() {
/* 70 */     return StringUtils.join(this.jsonKeys, "->");
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     if (this.filename != null) {
/* 76 */       if (this.jsonKeys.isEmpty()) {
/* 77 */         return this.filename;
/*    */       }
/* 79 */       return this.filename + " " + this.filename;
/*    */     } 
/*    */     
/* 82 */     if (this.jsonKeys.isEmpty()) {
/* 83 */       return "(Unknown file)";
/*    */     }
/* 85 */     return "(Unknown file) " + getJsonKeys();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ChainedJsonException$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */