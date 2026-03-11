/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleType;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ 
/*    */ public class TorchBlock extends BaseTorchBlock {
/*    */   static {
/* 20 */     PARTICLE_OPTIONS_FIELD = BuiltInRegistries.PARTICLE_TYPE.byNameCodec().comapFlatMap($$0 -> { SimpleParticleType $$1 = (SimpleParticleType)$$0; return (Function)(($$0 instanceof SimpleParticleType) ? DataResult.success($$1) : DataResult.error(())); }$$0 -> $$0).fieldOf("particle_options");
/*    */     
/* 22 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)PARTICLE_OPTIONS_FIELD.forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, TorchBlock::new));
/*    */   }
/*    */   protected static final MapCodec<SimpleParticleType> PARTICLE_OPTIONS_FIELD;
/*    */   public static final MapCodec<TorchBlock> CODEC;
/*    */   protected final SimpleParticleType flameParticle;
/*    */   
/*    */   public MapCodec<? extends TorchBlock> codec() {
/* 29 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected TorchBlock(SimpleParticleType $$0, BlockBehaviour.Properties $$1) {
/* 35 */     super($$1);
/* 36 */     this.flameParticle = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 41 */     double $$4 = $$2.getX() + 0.5D;
/* 42 */     double $$5 = $$2.getY() + 0.7D;
/* 43 */     double $$6 = $$2.getZ() + 0.5D;
/* 44 */     $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/* 45 */     $$1.addParticle((ParticleOptions)this.flameParticle, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TorchBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */