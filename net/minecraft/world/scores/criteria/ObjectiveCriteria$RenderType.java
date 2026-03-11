/*     */ package net.minecraft.world.scores.criteria;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum RenderType
/*     */   implements StringRepresentable
/*     */ {
/* 110 */   INTEGER("integer"),
/* 111 */   HEARTS("hearts");
/*     */   
/*     */   private final String id;
/*     */   public static final StringRepresentable.EnumCodec<RenderType> CODEC;
/*     */   
/*     */   RenderType(String $$0) {
/* 117 */     this.id = $$0;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 121 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 126 */     return this.id;
/*     */   }
/*     */   static {
/* 129 */     CODEC = StringRepresentable.fromEnum(RenderType::values);
/*     */   }
/*     */   public static RenderType byId(String $$0) {
/* 132 */     return (RenderType)CODEC.byName($$0, INTEGER);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\criteria\ObjectiveCriteria$RenderType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */