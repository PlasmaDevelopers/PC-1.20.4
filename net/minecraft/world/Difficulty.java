/*    */ package net.minecraft.world;
/*    */ import java.util.function.IntFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum Difficulty implements StringRepresentable {
/*    */   public static final StringRepresentable.EnumCodec<Difficulty> CODEC;
/*    */   private static final IntFunction<Difficulty> BY_ID;
/* 11 */   PEACEFUL(0, "peaceful"),
/* 12 */   EASY(1, "easy"),
/* 13 */   NORMAL(2, "normal"),
/* 14 */   HARD(3, "hard");
/*    */   
/*    */   static {
/* 17 */     CODEC = StringRepresentable.fromEnum(Difficulty::values);
/*    */     
/* 19 */     BY_ID = ByIdMap.continuous(Difficulty::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   private final int id;
/*    */   private final String key;
/*    */   
/*    */   Difficulty(int $$0, String $$1) {
/* 25 */     this.id = $$0;
/* 26 */     this.key = $$1;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 30 */     return this.id;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 34 */     return (Component)Component.translatable("options.difficulty." + this.key);
/*    */   }
/*    */   
/*    */   public Component getInfo() {
/* 38 */     return (Component)Component.translatable("options.difficulty." + this.key + ".info");
/*    */   }
/*    */   
/*    */   public static Difficulty byId(int $$0) {
/* 42 */     return BY_ID.apply($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Difficulty byName(String $$0) {
/* 47 */     return (Difficulty)CODEC.byName($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 54 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 59 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\Difficulty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */