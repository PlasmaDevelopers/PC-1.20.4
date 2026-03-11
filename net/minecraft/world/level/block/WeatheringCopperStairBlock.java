/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WeatheringCopperStairBlock extends StairBlock implements WeatheringCopper {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), (App)BlockState.CODEC.fieldOf("base_state").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, WeatheringCopperStairBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<WeatheringCopperStairBlock> CODEC;
/*    */   private final WeatheringCopper.WeatherState weatherState;
/*    */   
/*    */   public MapCodec<WeatheringCopperStairBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WeatheringCopperStairBlock(WeatheringCopper.WeatherState $$0, BlockState $$1, BlockBehaviour.Properties $$2) {
/* 25 */     super($$1, $$2);
/* 26 */     this.weatherState = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 31 */     changeOverTime($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRandomlyTicking(BlockState $$0) {
/* 36 */     return WeatheringCopper.getNext($$0.getBlock()).isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatheringCopper.WeatherState getAge() {
/* 41 */     return this.weatherState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopperStairBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */