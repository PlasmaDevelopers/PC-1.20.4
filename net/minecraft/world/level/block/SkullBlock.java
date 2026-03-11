/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SkullBlock extends AbstractSkullBlock {
/*     */   static {
/*  24 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Type.CODEC.fieldOf("kind").forGetter(AbstractSkullBlock::getType), (App)propertiesCodec()).apply((Applicative)$$0, SkullBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<SkullBlock> CODEC;
/*     */   
/*     */   public MapCodec<? extends SkullBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */   
/*     */   public static interface Type extends StringRepresentable {
/*  35 */     public static final Map<String, Type> TYPES = (Map<String, Type>)new Object2ObjectArrayMap();
/*     */     
/*  37 */     public static final Codec<Type> CODEC = ExtraCodecs.stringResolverCodec(StringRepresentable::getSerializedName, TYPES::get); static { Objects.requireNonNull(TYPES); }
/*     */      }
/*     */   
/*     */   public enum Types implements Type {
/*  41 */     SKELETON("skeleton"),
/*  42 */     WITHER_SKELETON("wither_skeleton"),
/*  43 */     PLAYER("player"),
/*  44 */     ZOMBIE("zombie"),
/*  45 */     CREEPER("creeper"),
/*  46 */     PIGLIN("piglin"),
/*  47 */     DRAGON("dragon");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     Types(String $$0) {
/*  53 */       this.name = $$0;
/*  54 */       TYPES.put($$0, this);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/*  59 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*  63 */   public static final int MAX = RotationSegment.getMaxSegmentIndex();
/*  64 */   private static final int ROTATIONS = MAX + 1;
/*     */   
/*  66 */   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
/*     */   
/*  68 */   protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
/*  69 */   protected static final VoxelShape PIGLIN_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
/*     */   
/*     */   protected SkullBlock(Type $$0, BlockBehaviour.Properties $$1) {
/*  72 */     super($$0, $$1);
/*  73 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)ROTATION, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  78 */     if (getType() == Types.PIGLIN) {
/*  79 */       return PIGLIN_SHAPE;
/*     */     }
/*  81 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  86 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  91 */     return (BlockState)super.getStateForPlacement($$0).setValue((Property)ROTATION, Integer.valueOf(RotationSegment.convertToSegment($$0.getRotation())));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  96 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.rotate(((Integer)$$0.getValue((Property)ROTATION)).intValue(), ROTATIONS)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 101 */     return (BlockState)$$0.setValue((Property)ROTATION, Integer.valueOf($$1.mirror(((Integer)$$0.getValue((Property)ROTATION)).intValue(), ROTATIONS)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 106 */     super.createBlockStateDefinition($$0);
/* 107 */     $$0.add(new Property[] { (Property)ROTATION });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SkullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */