/*     */ package com.mojang.blaze3d.shaders;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Program
/*     */ {
/*     */   private static final int MAX_LOG_LENGTH = 32768;
/*     */   private final Type type;
/*     */   private final String name;
/*     */   private int id;
/*     */   
/*     */   protected Program(Type $$0, int $$1, String $$2) {
/*  24 */     this.type = $$0;
/*  25 */     this.id = $$1;
/*  26 */     this.name = $$2;
/*     */   }
/*     */   
/*     */   public void attachToShader(Shader $$0) {
/*  30 */     RenderSystem.assertOnRenderThread();
/*  31 */     GlStateManager.glAttachShader($$0.getId(), getId());
/*     */   }
/*     */   
/*     */   public void close() {
/*  35 */     if (this.id == -1) {
/*     */       return;
/*     */     }
/*     */     
/*  39 */     RenderSystem.assertOnRenderThread();
/*  40 */     GlStateManager.glDeleteShader(this.id);
/*  41 */     this.id = -1;
/*  42 */     this.type.getPrograms().remove(this.name);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  46 */     return this.name;
/*     */   }
/*     */   
/*     */   public static Program compileShader(Type $$0, String $$1, InputStream $$2, String $$3, GlslPreprocessor $$4) throws IOException {
/*  50 */     RenderSystem.assertOnRenderThread();
/*  51 */     int $$5 = compileShaderInternal($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  53 */     Program $$6 = new Program($$0, $$5, $$1);
/*  54 */     $$0.getPrograms().put($$1, $$6);
/*  55 */     return $$6;
/*     */   }
/*     */   
/*     */   protected static int compileShaderInternal(Type $$0, String $$1, InputStream $$2, String $$3, GlslPreprocessor $$4) throws IOException {
/*  59 */     String $$5 = IOUtils.toString($$2, StandardCharsets.UTF_8);
/*  60 */     if ($$5 == null) {
/*  61 */       throw new IOException("Could not load program " + $$0.getName());
/*     */     }
/*  63 */     int $$6 = GlStateManager.glCreateShader($$0.getGlType());
/*  64 */     GlStateManager.glShaderSource($$6, $$4.process($$5));
/*  65 */     GlStateManager.glCompileShader($$6);
/*     */     
/*  67 */     if (GlStateManager.glGetShaderi($$6, 35713) == 0) {
/*  68 */       String $$7 = StringUtils.trim(GlStateManager.glGetShaderInfoLog($$6, 32768));
/*  69 */       throw new IOException("Couldn't compile " + $$0.getName() + " program (" + $$3 + ", " + $$1 + ") : " + $$7);
/*     */     } 
/*  71 */     return $$6;
/*     */   }
/*     */   
/*     */   protected int getId() {
/*  75 */     return this.id;
/*     */   }
/*     */   
/*     */   public enum Type {
/*  79 */     VERTEX("vertex", ".vsh", 35633),
/*  80 */     FRAGMENT("fragment", ".fsh", 35632);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     private final Map<String, Program> programs = Maps.newHashMap(); private final String name;
/*     */     
/*     */     Type(String $$0, String $$1, int $$2) {
/*  88 */       this.name = $$0;
/*  89 */       this.extension = $$1;
/*  90 */       this.glType = $$2;
/*     */     }
/*     */     private final String extension; private final int glType;
/*     */     public String getName() {
/*  94 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getExtension() {
/*  98 */       return this.extension;
/*     */     }
/*     */     
/*     */     int getGlType() {
/* 102 */       return this.glType;
/*     */     }
/*     */     
/*     */     public Map<String, Program> getPrograms() {
/* 106 */       return this.programs;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\Program.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */