/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Context
/*    */ {
/*    */   private final LevelSimulatedReader level;
/*    */   private final BiConsumer<BlockPos, BlockState> decorationSetter;
/*    */   private final RandomSource random;
/*    */   private final ObjectArrayList<BlockPos> logs;
/*    */   private final ObjectArrayList<BlockPos> leaves;
/*    */   private final ObjectArrayList<BlockPos> roots;
/*    */   
/*    */   public Context(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, Set<BlockPos> $$3, Set<BlockPos> $$4, Set<BlockPos> $$5) {
/* 33 */     this.level = $$0;
/* 34 */     this.decorationSetter = $$1;
/* 35 */     this.random = $$2;
/*    */     
/* 37 */     this.roots = new ObjectArrayList($$5);
/* 38 */     this.logs = new ObjectArrayList($$3);
/* 39 */     this.leaves = new ObjectArrayList($$4);
/*    */     
/* 41 */     this.logs.sort(Comparator.comparingInt(Vec3i::getY));
/* 42 */     this.leaves.sort(Comparator.comparingInt(Vec3i::getY));
/* 43 */     this.roots.sort(Comparator.comparingInt(Vec3i::getY));
/*    */   }
/*    */   
/*    */   public void placeVine(BlockPos $$0, BooleanProperty $$1) {
/* 47 */     setBlock($$0, (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)$$1, Boolean.valueOf(true)));
/*    */   }
/*    */   
/*    */   public void setBlock(BlockPos $$0, BlockState $$1) {
/* 51 */     this.decorationSetter.accept($$0, $$1);
/*    */   }
/*    */   
/*    */   public boolean isAir(BlockPos $$0) {
/* 55 */     return this.level.isStateAtPosition($$0, BlockBehaviour.BlockStateBase::isAir);
/*    */   }
/*    */   
/*    */   public LevelSimulatedReader level() {
/* 59 */     return this.level;
/*    */   }
/*    */   
/*    */   public RandomSource random() {
/* 63 */     return this.random;
/*    */   }
/*    */   
/*    */   public ObjectArrayList<BlockPos> logs() {
/* 67 */     return this.logs;
/*    */   }
/*    */   
/*    */   public ObjectArrayList<BlockPos> leaves() {
/* 71 */     return this.leaves;
/*    */   }
/*    */   
/*    */   public ObjectArrayList<BlockPos> roots() {
/* 75 */     return this.roots;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\TreeDecorator$Context.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */