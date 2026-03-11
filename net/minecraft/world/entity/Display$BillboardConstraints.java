/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.IntFunction;
/*     */ import net.minecraft.util.ByIdMap;
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
/*     */ public enum BillboardConstraints
/*     */   implements StringRepresentable
/*     */ {
/*     */   public static final Codec<BillboardConstraints> CODEC;
/*     */   public static final IntFunction<BillboardConstraints> BY_ID;
/*  97 */   FIXED((byte)0, "fixed"),
/*  98 */   VERTICAL((byte)1, "vertical"),
/*  99 */   HORIZONTAL((byte)2, "horizontal"),
/* 100 */   CENTER((byte)3, "center");
/*     */   
/*     */   static {
/* 103 */     CODEC = (Codec<BillboardConstraints>)StringRepresentable.fromEnum(BillboardConstraints::values);
/* 104 */     BY_ID = ByIdMap.continuous(BillboardConstraints::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */   }
/*     */   
/*     */   private final byte id;
/*     */   
/*     */   BillboardConstraints(byte $$0, String $$1) {
/* 110 */     this.name = $$1;
/* 111 */     this.id = $$0;
/*     */   }
/*     */   private final String name;
/*     */   
/*     */   public String getSerializedName() {
/* 116 */     return this.name;
/*     */   }
/*     */   
/*     */   byte getId() {
/* 120 */     return this.id;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Display$BillboardConstraints.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */