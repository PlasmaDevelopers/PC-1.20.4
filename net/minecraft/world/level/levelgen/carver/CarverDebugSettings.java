/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class CarverDebugSettings {
/*  9 */   public static final CarverDebugSettings DEFAULT = new CarverDebugSettings(false, Blocks.ACACIA_BUTTON
/*    */       
/* 11 */       .defaultBlockState(), Blocks.CANDLE
/* 12 */       .defaultBlockState(), Blocks.ORANGE_STAINED_GLASS
/* 13 */       .defaultBlockState(), Blocks.GLASS
/* 14 */       .defaultBlockState()); public static final Codec<CarverDebugSettings> CODEC; private final boolean debugMode; private final BlockState airState;
/*    */   
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.BOOL.optionalFieldOf("debug_mode", Boolean.valueOf(false)).forGetter(CarverDebugSettings::isDebugMode), (App)BlockState.CODEC.optionalFieldOf("air_state", DEFAULT.getAirState()).forGetter(CarverDebugSettings::getAirState), (App)BlockState.CODEC.optionalFieldOf("water_state", DEFAULT.getAirState()).forGetter(CarverDebugSettings::getWaterState), (App)BlockState.CODEC.optionalFieldOf("lava_state", DEFAULT.getAirState()).forGetter(CarverDebugSettings::getLavaState), (App)BlockState.CODEC.optionalFieldOf("barrier_state", DEFAULT.getAirState()).forGetter(CarverDebugSettings::getBarrierState)).apply((Applicative)$$0, CarverDebugSettings::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private final BlockState waterState;
/*    */ 
/*    */   
/*    */   private final BlockState lavaState;
/*    */ 
/*    */   
/*    */   private final BlockState barrierState;
/*    */ 
/*    */   
/*    */   public static CarverDebugSettings of(boolean $$0, BlockState $$1, BlockState $$2, BlockState $$3, BlockState $$4) {
/* 32 */     return new CarverDebugSettings($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public static CarverDebugSettings of(BlockState $$0, BlockState $$1, BlockState $$2, BlockState $$3) {
/* 36 */     return new CarverDebugSettings(false, $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public static CarverDebugSettings of(boolean $$0, BlockState $$1) {
/* 40 */     return new CarverDebugSettings($$0, $$1, DEFAULT.getWaterState(), DEFAULT.getLavaState(), DEFAULT.getBarrierState());
/*    */   }
/*    */   
/*    */   private CarverDebugSettings(boolean $$0, BlockState $$1, BlockState $$2, BlockState $$3, BlockState $$4) {
/* 44 */     this.debugMode = $$0;
/* 45 */     this.airState = $$1;
/* 46 */     this.waterState = $$2;
/* 47 */     this.lavaState = $$3;
/* 48 */     this.barrierState = $$4;
/*    */   }
/*    */   
/*    */   public boolean isDebugMode() {
/* 52 */     return this.debugMode;
/*    */   }
/*    */   
/*    */   public BlockState getAirState() {
/* 56 */     return this.airState;
/*    */   }
/*    */   
/*    */   public BlockState getWaterState() {
/* 60 */     return this.waterState;
/*    */   }
/*    */   
/*    */   public BlockState getLavaState() {
/* 64 */     return this.lavaState;
/*    */   }
/*    */   
/*    */   public BlockState getBarrierState() {
/* 68 */     return this.barrierState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CarverDebugSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */