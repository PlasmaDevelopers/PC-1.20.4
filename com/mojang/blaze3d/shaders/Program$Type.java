/*     */ package com.mojang.blaze3d.shaders;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
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
/*     */ public enum Type
/*     */ {
/*  79 */   VERTEX("vertex", ".vsh", 35633),
/*  80 */   FRAGMENT("fragment", ".fsh", 35632);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private final Map<String, Program> programs = Maps.newHashMap(); private final String name;
/*     */   
/*     */   Type(String $$0, String $$1, int $$2) {
/*  88 */     this.name = $$0;
/*  89 */     this.extension = $$1;
/*  90 */     this.glType = $$2;
/*     */   }
/*     */   private final String extension; private final int glType;
/*     */   public String getName() {
/*  94 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getExtension() {
/*  98 */     return this.extension;
/*     */   }
/*     */   
/*     */   int getGlType() {
/* 102 */     return this.glType;
/*     */   }
/*     */   
/*     */   public Map<String, Program> getPrograms() {
/* 106 */     return this.programs;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\Program$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */