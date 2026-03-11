/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DustParticlesDelta
/*     */   extends Record
/*     */ {
/*     */   private final double xd;
/*     */   private final double yd;
/*     */   private final double zd;
/*     */   private static final double ALONG_SIDE_DELTA = 1.0D;
/*     */   private static final double OUT_FROM_SIDE_DELTA = 0.1D;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #136	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #136	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;
/*     */   }
/*     */   
/*     */   private DustParticlesDelta(double $$0, double $$1, double $$2) {
/* 136 */     this.xd = $$0; this.yd = $$1; this.zd = $$2; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #136	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;
/* 136 */     //   0	8	1	$$0	Ljava/lang/Object; } public double xd() { return this.xd; } public double yd() { return this.yd; } public double zd() { return this.zd; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DustParticlesDelta fromDirection(Vec3 $$0, Direction $$1) {
/* 142 */     double $$2 = 0.0D;
/* 143 */     switch (BrushItem.null.$SwitchMap$net$minecraft$core$Direction[$$1.ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: case 4: case 5: case 6: break; }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       new DustParticlesDelta(0.1D, 0.0D, 1.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BrushItem$DustParticlesDelta.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */