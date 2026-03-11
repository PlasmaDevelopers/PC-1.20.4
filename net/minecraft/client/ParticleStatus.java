/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ 
/*    */ public enum ParticleStatus implements OptionEnum {
/*    */   private static final IntFunction<ParticleStatus> BY_ID;
/*  9 */   ALL(0, "options.particles.all"),
/* 10 */   DECREASED(1, "options.particles.decreased"),
/* 11 */   MINIMAL(2, "options.particles.minimal");
/*    */   
/*    */   static {
/* 14 */     BY_ID = ByIdMap.continuous(ParticleStatus::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   
/*    */   private final int id;
/*    */   
/*    */   ParticleStatus(int $$0, String $$1) {
/* 20 */     this.id = $$0;
/* 21 */     this.key = $$1;
/*    */   }
/*    */   private final String key;
/*    */   
/*    */   public String getKey() {
/* 26 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 31 */     return this.id;
/*    */   }
/*    */   
/*    */   public static ParticleStatus byId(int $$0) {
/* 35 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ParticleStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */