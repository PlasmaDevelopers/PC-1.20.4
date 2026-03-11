/*     */ package net.minecraft.world.level.block;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum WeatherState
/*     */   implements StringRepresentable
/*     */ {
/*  97 */   UNAFFECTED("unaffected"),
/*  98 */   EXPOSED("exposed"),
/*  99 */   WEATHERED("weathered"),
/* 100 */   OXIDIZED("oxidized"); public static final Codec<WeatherState> CODEC;
/*     */   static {
/* 102 */     CODEC = (Codec<WeatherState>)StringRepresentable.fromEnum(WeatherState::values);
/*     */   }
/*     */   private final String name;
/*     */   
/*     */   WeatherState(String $$0) {
/* 107 */     this.name = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 112 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopper$WeatherState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */