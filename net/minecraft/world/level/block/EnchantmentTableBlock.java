/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.Nameable;
/*     */ import net.minecraft.world.SimpleMenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ContainerLevelAccess;
/*     */ import net.minecraft.world.inventory.EnchantmentMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class EnchantmentTableBlock extends BaseEntityBlock {
/*  35 */   public static final MapCodec<EnchantmentTableBlock> CODEC = simpleCodec(EnchantmentTableBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<EnchantmentTableBlock> codec() {
/*  39 */     return CODEC;
/*     */   }
/*     */   
/*  42 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D); public static final List<BlockPos> BOOKSHELF_OFFSETS; static {
/*  43 */     BOOKSHELF_OFFSETS = BlockPos.betweenClosedStream(-2, 0, -2, 2, 1, 2).filter($$0 -> (Math.abs($$0.getX()) == 2 || Math.abs($$0.getZ()) == 2)).map(BlockPos::immutable).toList();
/*     */   }
/*     */   protected EnchantmentTableBlock(BlockBehaviour.Properties $$0) {
/*  46 */     super($$0);
/*     */   }
/*     */   
/*     */   public static boolean isValidBookShelf(Level $$0, BlockPos $$1, BlockPos $$2) {
/*  50 */     return ($$0.getBlockState($$1.offset((Vec3i)$$2)).is(BlockTags.ENCHANTMENT_POWER_PROVIDER) && $$0.getBlockState($$1.offset($$2.getX() / 2, $$2.getY(), $$2.getZ() / 2)).is(BlockTags.ENCHANTMENT_POWER_TRANSMITTER));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/*  55 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  60 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/*  65 */     super.animateTick($$0, $$1, $$2, $$3);
/*     */     
/*  67 */     for (BlockPos $$4 : BOOKSHELF_OFFSETS) {
/*  68 */       if ($$3.nextInt(16) == 0 && isValidBookShelf($$1, $$2, $$4)) {
/*  69 */         $$1.addParticle((ParticleOptions)ParticleTypes.ENCHANT, $$2.getX() + 0.5D, $$2.getY() + 2.0D, $$2.getZ() + 0.5D, ($$4.getX() + $$3.nextFloat()) - 0.5D, ($$4.getY() - $$3.nextFloat() - 1.0F), ($$4.getZ() + $$3.nextFloat()) - 0.5D);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  76 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  81 */     return (BlockEntity)new EnchantmentTableBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/*  87 */     return $$0.isClientSide ? createTickerHelper($$2, BlockEntityType.ENCHANTING_TABLE, EnchantmentTableBlockEntity::bookAnimationTick) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  92 */     if ($$1.isClientSide) {
/*  93 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  96 */     $$3.openMenu($$0.getMenuProvider($$1, $$2));
/*  97 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 103 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 104 */     if ($$3 instanceof EnchantmentTableBlockEntity) {
/* 105 */       Component $$4 = ((Nameable)$$3).getDisplayName();
/*     */       
/* 107 */       return (MenuProvider)new SimpleMenuProvider(($$2, $$3, $$4) -> new EnchantmentMenu($$2, $$3, ContainerLevelAccess.create($$0, $$1)), $$4);
/*     */     } 
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 114 */     if ($$4.hasCustomHoverName()) {
/* 115 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 116 */       if ($$5 instanceof EnchantmentTableBlockEntity) {
/* 117 */         ((EnchantmentTableBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 124 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EnchantmentTableBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */