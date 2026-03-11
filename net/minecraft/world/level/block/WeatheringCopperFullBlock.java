/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WeatheringCopperFullBlock extends Block implements WeatheringCopper {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), (App)propertiesCodec()).apply((Applicative)$$0, WeatheringCopperFullBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<WeatheringCopperFullBlock> CODEC;
/*    */   private final WeatheringCopper.WeatherState weatherState;
/*    */   
/*    */   public MapCodec<WeatheringCopperFullBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WeatheringCopperFullBlock(WeatheringCopper.WeatherState $$0, BlockBehaviour.Properties $$1) {
/* 24 */     super($$1);
/* 25 */     this.weatherState = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 30 */     changeOverTime($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRandomlyTicking(BlockState $$0) {
/* 35 */     return WeatheringCopper.getNext($$0.getBlock()).isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatheringCopper.WeatherState getAge() {
/* 40 */     return this.weatherState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopperFullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */