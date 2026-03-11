/*     */ package net.minecraft.world.entity.animal.horse;
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
/*     */ public enum Variant
/*     */   implements StringRepresentable
/*     */ {
/*  84 */   CREAMY(0, "creamy"),
/*  85 */   WHITE(1, "white"),
/*  86 */   BROWN(2, "brown"),
/*  87 */   GRAY(3, "gray"); public static final Codec<Variant> CODEC; private static final IntFunction<Variant> BY_ID;
/*     */   
/*     */   static {
/*  90 */     CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */     
/*  92 */     BY_ID = ByIdMap.continuous(Variant::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
/*     */   }
/*     */   final int id; private final String name;
/*     */   
/*     */   Variant(int $$0, String $$1) {
/*  97 */     this.id = $$0;
/*  98 */     this.name = $$1;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 102 */     return this.id;
/*     */   }
/*     */   
/*     */   public static Variant byId(int $$0) {
/* 106 */     return BY_ID.apply($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 111 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\Llama$Variant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */