/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class ChainedJsonException
/*    */   extends IOException
/*    */ {
/* 12 */   private final List<Entry> entries = Lists.newArrayList();
/*    */   private final String message;
/*    */   
/*    */   public ChainedJsonException(String $$0) {
/* 16 */     this.entries.add(new Entry());
/* 17 */     this.message = $$0;
/*    */   }
/*    */   
/*    */   public ChainedJsonException(String $$0, Throwable $$1) {
/* 21 */     super($$1);
/* 22 */     this.entries.add(new Entry());
/* 23 */     this.message = $$0;
/*    */   }
/*    */   
/*    */   public void prependJsonKey(String $$0) {
/* 27 */     ((Entry)this.entries.get(0)).addJsonKey($$0);
/*    */   }
/*    */   
/*    */   public void setFilenameAndFlush(String $$0) {
/* 31 */     ((Entry)this.entries.get(0)).filename = $$0;
/* 32 */     this.entries.add(0, new Entry());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 37 */     return "Invalid " + this.entries.get(this.entries.size() - 1) + ": " + this.message;
/*    */   }
/*    */   
/*    */   public static ChainedJsonException forException(Exception $$0) {
/* 41 */     if ($$0 instanceof ChainedJsonException) {
/* 42 */       return (ChainedJsonException)$$0;
/*    */     }
/* 44 */     String $$1 = $$0.getMessage();
/* 45 */     if ($$0 instanceof java.io.FileNotFoundException) {
/* 46 */       $$1 = "File not found";
/*    */     }
/* 48 */     return new ChainedJsonException($$1, $$0);
/*    */   }
/*    */   
/*    */   public static class Entry
/*    */   {
/*    */     @Nullable
/*    */     String filename;
/* 55 */     private final List<String> jsonKeys = Lists.newArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     void addJsonKey(String $$0) {
/* 61 */       this.jsonKeys.add(0, $$0);
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public String getFilename() {
/* 66 */       return this.filename;
/*    */     }
/*    */     
/*    */     public String getJsonKeys() {
/* 70 */       return StringUtils.join(this.jsonKeys, "->");
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 75 */       if (this.filename != null) {
/* 76 */         if (this.jsonKeys.isEmpty()) {
/* 77 */           return this.filename;
/*    */         }
/* 79 */         return this.filename + " " + this.filename;
/*    */       } 
/*    */       
/* 82 */       if (this.jsonKeys.isEmpty()) {
/* 83 */         return "(Unknown file)";
/*    */       }
/* 85 */       return "(Unknown file) " + getJsonKeys();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ChainedJsonException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */