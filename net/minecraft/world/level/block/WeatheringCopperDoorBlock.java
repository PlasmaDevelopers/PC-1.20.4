/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*    */ 
/*    */ public class WeatheringCopperDoorBlock extends DoorBlock implements WeatheringCopper {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(DoorBlock::type), (App)WeatheringCopper.WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopperDoorBlock::getAge), (App)propertiesCodec()).apply((Applicative)$$0, WeatheringCopperDoorBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<WeatheringCopperDoorBlock> CODEC;
/*    */   private final WeatheringCopper.WeatherState weatherState;
/*    */   
/*    */   public MapCodec<WeatheringCopperDoorBlock> codec() {
/* 21 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected WeatheringCopperDoorBlock(BlockSetType $$0, WeatheringCopper.WeatherState $$1, BlockBehaviour.Properties $$2) {
/* 27 */     super($$0, $$2);
/* 28 */     this.weatherState = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 33 */     if ($$0.getValue((Property)DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
/* 34 */       changeOverTime($$0, $$1, $$2, $$3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRandomlyTicking(BlockState $$0) {
/* 40 */     return WeatheringCopper.getNext($$0.getBlock()).isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   public WeatheringCopper.WeatherState getAge() {
/* 45 */     return this.weatherState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeatheringCopperDoorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */