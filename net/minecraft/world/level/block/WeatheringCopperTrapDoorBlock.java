/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*    */ 
/*    */ public class WeatheringCopperTrapDoorBlock extends TrapDoorBlock implements WeatheringCopper {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(TrapDoorBlock::getType), (App)WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopperTrapDoorBlock::getAge), (App)propertiesCodec()).apply((Applicative)$$0, WeatheringCopperTrapDoorBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<WeatheringCopperTrapDoorBlock> CODEC;
/*    */   private final WeatheringCopper.WeatherState weatherState;
/*    */   
/*    */   public MapCodec<WeatheringCopperTrapDoorBlock> codec() {
/* 20 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected WeatheringCopperTrapDoorBlock(BlockSetType $$0, WeatheringCopper.WeatherState $$1, BlockBehaviour.Properties $$2) {
/* 26 */     super($$0, $$2);
/* 27 */     this.weatherState = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 32 */     changeOverTime($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRandomlyTicking(BlockState $$0) {
/* 37 */     return WeatheringCopper.getNext($$0.getBlock()).isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatheringCopper.WeatherState getAge() {
/* 42 */     return this.weatherState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopperTrapDoorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */