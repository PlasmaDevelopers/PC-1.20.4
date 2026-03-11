/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ public class ParticleGroup {
/*    */   private final int limit;
/*    */   
/*    */   public ParticleGroup(int $$0) {
/*  7 */     this.limit = $$0;
/*    */   }
/*    */   
/*    */   public int getLimit() {
/* 11 */     return this.limit;
/*    */   }
/*    */   
/* 14 */   public static final ParticleGroup SPORE_BLOSSOM = new ParticleGroup(1000);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ParticleGroup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */