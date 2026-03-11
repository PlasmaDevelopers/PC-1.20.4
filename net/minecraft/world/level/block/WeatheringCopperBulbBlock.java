/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WeatheringCopperBulbBlock extends CopperBulbBlock implements WeatheringCopper {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopperBulbBlock::getAge), (App)propertiesCodec()).apply((Applicative)$$0, WeatheringCopperBulbBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<WeatheringCopperBulbBlock> CODEC;
/*    */   private final WeatheringCopper.WeatherState weatherState;
/*    */   
/*    */   protected MapCodec<WeatheringCopperBulbBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatheringCopperBulbBlock(WeatheringCopper.WeatherState $$0, BlockBehaviour.Properties $$1) {
/* 23 */     super($$1);
/* 24 */     this.weatherState = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 29 */     changeOverTime($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRandomlyTicking(BlockState $$0) {
/* 34 */     return WeatheringCopper.getNext($$0.getBlock()).isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatheringCopper.WeatherState getAge() {
/* 39 */     return this.weatherState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopperBulbBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */