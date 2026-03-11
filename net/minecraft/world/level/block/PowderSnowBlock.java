/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.EntityCollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PowderSnowBlock extends Block implements BucketPickup {
/*  35 */   public static final MapCodec<PowderSnowBlock> CODEC = simpleCodec(PowderSnowBlock::new); private static final float HORIZONTAL_PARTICLE_MOMENTUM_FACTOR = 0.083333336F;
/*     */   private static final float IN_BLOCK_HORIZONTAL_SPEED_MULTIPLIER = 0.9F;
/*     */   
/*     */   public MapCodec<PowderSnowBlock> codec() {
/*  39 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final float IN_BLOCK_VERTICAL_SPEED_MULTIPLIER = 1.5F;
/*     */   
/*     */   private static final float NUM_BLOCKS_TO_FALL_INTO_BLOCK = 2.5F;
/*     */   
/*  47 */   private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.8999999761581421D, 1.0D);
/*     */   private static final double MINIMUM_FALL_DISTANCE_FOR_SOUND = 4.0D;
/*     */   private static final double MINIMUM_FALL_DISTANCE_FOR_BIG_SOUND = 7.0D;
/*     */   
/*     */   public PowderSnowBlock(BlockBehaviour.Properties $$0) {
/*  52 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean skipRendering(BlockState $$0, BlockState $$1, Direction $$2) {
/*  57 */     if ($$1.is(this)) {
/*  58 */       return true;
/*     */     }
/*  60 */     return super.skipRendering($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  65 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  70 */     if (!($$3 instanceof LivingEntity) || $$3.getFeetBlockState().is(this)) {
/*  71 */       $$3.makeStuckInBlock($$0, new Vec3(0.8999999761581421D, 1.5D, 0.8999999761581421D));
/*     */       
/*  73 */       if ($$1.isClientSide) {
/*  74 */         RandomSource $$4 = $$1.getRandom();
/*  75 */         boolean $$5 = ($$3.xOld != $$3.getX() || $$3.zOld != $$3.getZ());
/*     */         
/*  77 */         if ($$5 && $$4.nextBoolean()) {
/*  78 */           $$1.addParticle((ParticleOptions)ParticleTypes.SNOWFLAKE, $$3.getX(), ($$2.getY() + 1), $$3.getZ(), (Mth.randomBetween($$4, -1.0F, 1.0F) * 0.083333336F), 0.05000000074505806D, (Mth.randomBetween($$4, -1.0F, 1.0F) * 0.083333336F));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     $$3.setIsInPowderSnow(true);
/*     */     
/*  85 */     if (!$$1.isClientSide) {
/*  86 */       if ($$3.isOnFire() && ($$1.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || $$3 instanceof Player) && $$3.mayInteract($$1, $$2)) {
/*  87 */         $$1.destroyBlock($$2, false);
/*     */       }
/*  89 */       $$3.setSharedFlagOnFire(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/*     */     LivingEntity $$5;
/*  96 */     if ($$4 >= 4.0D && $$3 instanceof LivingEntity) { $$5 = (LivingEntity)$$3; }
/*     */     else
/*     */     { return; }
/*     */     
/* 100 */     LivingEntity.Fallsounds $$7 = $$5.getFallSounds();
/* 101 */     SoundEvent $$8 = ($$4 < 7.0D) ? $$7.small() : $$7.big();
/*     */     
/* 103 */     $$3.playSound($$8, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 108 */     if ($$3 instanceof EntityCollisionContext) { EntityCollisionContext $$4 = (EntityCollisionContext)$$3;
/* 109 */       Entity $$5 = $$4.getEntity();
/* 110 */       if ($$5 != null) {
/* 111 */         if ($$5.fallDistance > 2.5F) {
/* 112 */           return FALLING_COLLISION_SHAPE;
/*     */         }
/*     */         
/* 115 */         boolean $$6 = $$5 instanceof net.minecraft.world.entity.item.FallingBlockEntity;
/* 116 */         if ($$6 || (canEntityWalkOnPowderSnow($$5) && $$3.isAbove(Shapes.block(), $$2, false) && !$$3.isDescending())) {
/* 117 */           return super.getCollisionShape($$0, $$1, $$2, $$3);
/*     */         }
/*     */       }  }
/*     */     
/* 121 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 126 */     return Shapes.empty();
/*     */   }
/*     */   
/*     */   public static boolean canEntityWalkOnPowderSnow(Entity $$0) {
/* 130 */     if ($$0.getType().is(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) {
/* 131 */       return true;
/*     */     }
/*     */     
/* 134 */     if ($$0 instanceof LivingEntity) {
/* 135 */       return ((LivingEntity)$$0).getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS);
/*     */     }
/*     */     
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack pickupBlock(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2, BlockState $$3) {
/* 143 */     $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 11);
/* 144 */     if (!$$1.isClientSide()) {
/* 145 */       $$1.levelEvent(2001, $$2, Block.getId($$3));
/*     */     }
/* 147 */     return new ItemStack((ItemLike)Items.POWDER_SNOW_BUCKET);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<SoundEvent> getPickupSound() {
/* 152 */     return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 157 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PowderSnowBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */