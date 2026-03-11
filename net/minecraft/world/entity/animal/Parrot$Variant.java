/*     */ package net.minecraft.world.entity.animal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 138 */   RED_BLUE(0, "red_blue"),
/* 139 */   BLUE(1, "blue"),
/* 140 */   GREEN(2, "green"),
/* 141 */   YELLOW_BLUE(3, "yellow_blue"),
/* 142 */   GRAY(4, "gray"); public static final Codec<Variant> CODEC; private static final IntFunction<Variant> BY_ID;
/*     */   
/*     */   static {
/* 145 */     CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */     
/* 147 */     BY_ID = ByIdMap.continuous(Variant::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
/*     */   }
/*     */   final int id; private final String name;
/*     */   
/*     */   Variant(int $$0, String $$1) {
/* 152 */     this.id = $$0;
/* 153 */     this.name = $$1;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 157 */     return this.id;
/*     */   }
/*     */   
/*     */   public static Variant byId(int $$0) {
/* 161 */     return BY_ID.apply($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 166 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Parrot$Variant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */