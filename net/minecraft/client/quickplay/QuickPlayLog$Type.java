/*     */ package net.minecraft.client.quickplay;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.util.StringRepresentable;
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
/*     */ public enum Type
/*     */   implements StringRepresentable
/*     */ {
/*  87 */   SINGLEPLAYER("singleplayer"),
/*  88 */   MULTIPLAYER("multiplayer"),
/*  89 */   REALMS("realms"); static final Codec<Type> CODEC;
/*     */   static {
/*  91 */     CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values);
/*     */   }
/*     */   private final String name;
/*     */   
/*     */   Type(String $$0) {
/*  96 */     this.name = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 101 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\quickplay\QuickPlayLog$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */