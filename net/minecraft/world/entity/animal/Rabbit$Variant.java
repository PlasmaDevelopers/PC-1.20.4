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
/*     */ public enum Variant
/*     */   implements StringRepresentable
/*     */ {
/*     */   private static final IntFunction<Variant> BY_ID;
/*     */   public static final Codec<Variant> CODEC;
/*  84 */   BROWN(0, "brown"),
/*  85 */   WHITE(1, "white"),
/*  86 */   BLACK(2, "black"),
/*  87 */   WHITE_SPLOTCHED(3, "white_splotched"),
/*  88 */   GOLD(4, "gold"),
/*  89 */   SALT(5, "salt"),
/*  90 */   EVIL(99, "evil");
/*     */   
/*     */   static {
/*  93 */     BY_ID = ByIdMap.sparse(Variant::id, (Object[])values(), BROWN);
/*     */     
/*  95 */     CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */   }
/*     */   
/*     */   final int id;
/*     */   
/*     */   Variant(int $$0, String $$1) {
/* 101 */     this.id = $$0;
/* 102 */     this.name = $$1;
/*     */   }
/*     */   private final String name;
/*     */   
/*     */   public String getSerializedName() {
/* 107 */     return this.name;
/*     */   }
/*     */   
/*     */   public int id() {
/* 111 */     return this.id;
/*     */   }
/*     */   
/*     */   public static Variant byId(int $$0) {
/* 115 */     return BY_ID.apply($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Rabbit$Variant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */