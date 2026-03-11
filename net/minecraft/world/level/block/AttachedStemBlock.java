/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class AttachedStemBlock extends BushBlock {
/*     */   static {
/*  27 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceKey.codec(Registries.BLOCK).fieldOf("fruit").forGetter(()), (App)ResourceKey.codec(Registries.BLOCK).fieldOf("stem").forGetter(()), (App)ResourceKey.codec(Registries.ITEM).fieldOf("seed").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, AttachedStemBlock::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final MapCodec<AttachedStemBlock> CODEC;
/*     */ 
/*     */   
/*     */   public MapCodec<AttachedStemBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*     */   protected static final float AABB_OFFSET = 2.0F;
/*  42 */   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap((Map)ImmutableMap.of(Direction.SOUTH, 
/*  43 */         Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 16.0D), Direction.WEST, 
/*  44 */         Block.box(0.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D), Direction.NORTH, 
/*  45 */         Block.box(6.0D, 0.0D, 0.0D, 10.0D, 10.0D, 10.0D), Direction.EAST, 
/*  46 */         Block.box(6.0D, 0.0D, 6.0D, 16.0D, 10.0D, 10.0D)));
/*     */   
/*     */   private final ResourceKey<Block> fruit;
/*     */   
/*     */   private final ResourceKey<Block> stem;
/*     */   
/*     */   private final ResourceKey<Item> seed;
/*     */   
/*     */   protected AttachedStemBlock(ResourceKey<Block> $$0, ResourceKey<Block> $$1, ResourceKey<Item> $$2, BlockBehaviour.Properties $$3) {
/*  55 */     super($$3);
/*  56 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*  57 */     this.stem = $$0;
/*  58 */     this.fruit = $$1;
/*  59 */     this.seed = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  64 */     return AABBS.get($$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  69 */     if (!$$2.is(this.fruit) && $$1 == $$0.getValue((Property)FACING)) {
/*  70 */       Optional<Block> $$6 = $$3.registryAccess().registryOrThrow(Registries.BLOCK).getOptional(this.stem);
/*  71 */       if ($$6.isPresent()) {
/*  72 */         return (BlockState)((Block)$$6.get()).defaultBlockState().trySetValue((Property)StemBlock.AGE, Integer.valueOf(7));
/*     */       }
/*     */     } 
/*  75 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  80 */     return $$0.is(Blocks.FARMLAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  85 */     return new ItemStack((ItemLike)DataFixUtils.orElse($$0.registryAccess().registryOrThrow(Registries.ITEM).getOptional(this.seed), this));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  90 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  95 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 100 */     $$0.add(new Property[] { (Property)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AttachedStemBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */