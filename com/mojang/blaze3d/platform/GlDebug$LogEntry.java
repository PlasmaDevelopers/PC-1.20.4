/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LogEntry
/*     */ {
/*     */   private final int id;
/*     */   private final int source;
/*     */   private final int type;
/*     */   private final int severity;
/*     */   private final String message;
/* 162 */   int count = 1;
/*     */   
/*     */   LogEntry(int $$0, int $$1, int $$2, int $$3, String $$4) {
/* 165 */     this.id = $$2;
/* 166 */     this.source = $$0;
/* 167 */     this.type = $$1;
/* 168 */     this.severity = $$3;
/* 169 */     this.message = $$4;
/*     */   }
/*     */   
/*     */   boolean isSame(int $$0, int $$1, int $$2, int $$3, String $$4) {
/* 173 */     return ($$1 == this.type && $$0 == this.source && $$2 == this.id && $$3 == this.severity && $$4
/*     */ 
/*     */ 
/*     */       
/* 177 */       .equals(this.message));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 182 */     return "id=" + this.id + ", source=" + 
/* 183 */       GlDebug.sourceToString(this.source) + ", type=" + 
/* 184 */       GlDebug.typeToString(this.type) + ", severity=" + 
/* 185 */       GlDebug.severityToString(this.severity) + ", message='" + this.message + "'";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\GlDebug$LogEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */