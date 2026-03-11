/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class WitherRoseBlock extends FlowerBlock {
/*    */   static {
/* 25 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects), (App)propertiesCodec()).apply((Applicative)$$0, WitherRoseBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<WitherRoseBlock> CODEC;
/*    */   
/*    */   public MapCodec<WitherRoseBlock> codec() {
/* 32 */     return CODEC;
/*    */   }
/*    */   
/*    */   public WitherRoseBlock(MobEffect $$0, int $$1, BlockBehaviour.Properties $$2) {
/* 36 */     this(makeEffectList($$0, $$1), $$2);
/*    */   }
/*    */   
/*    */   public WitherRoseBlock(List<SuspiciousEffectHolder.EffectEntry> $$0, BlockBehaviour.Properties $$1) {
/* 40 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 45 */     return (super.mayPlaceOn($$0, $$1, $$2) || $$0.is(Blocks.NETHERRACK) || $$0.is(Blocks.SOUL_SAND) || $$0.is(Blocks.SOUL_SOIL));
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 50 */     VoxelShape $$4 = getShape($$0, (BlockGetter)$$1, $$2, CollisionContext.empty());
/* 51 */     Vec3 $$5 = $$4.bounds().getCenter();
/* 52 */     double $$6 = $$2.getX() + $$5.x;
/* 53 */     double $$7 = $$2.getZ() + $$5.z;
/* 54 */     for (int $$8 = 0; $$8 < 3; $$8++) {
/* 55 */       if ($$3.nextBoolean()) {
/* 56 */         $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$6 + $$3.nextDouble() / 5.0D, $$2.getY() + 0.5D - $$3.nextDouble(), $$7 + $$3.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 63 */     if ($$1.isClientSide || $$1.getDifficulty() == Difficulty.PEACEFUL) {
/*    */       return;
/*    */     }
/*    */     
/* 67 */     if ($$3 instanceof LivingEntity) { LivingEntity $$4 = (LivingEntity)$$3;
/* 68 */       if (!$$4.isInvulnerableTo($$1.damageSources().wither()))
/* 69 */         $$4.addEffect(new MobEffectInstance(MobEffects.WITHER, 40));  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WitherRoseBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */