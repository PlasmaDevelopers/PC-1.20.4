/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function6;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public class AttachedToLeavesDecorator extends TreeDecorator {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(()), (App)Codec.intRange(0, 16).fieldOf("exclusion_radius_xz").forGetter(()), (App)Codec.intRange(0, 16).fieldOf("exclusion_radius_y").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(()), (App)Codec.intRange(1, 16).fieldOf("required_empty_blocks").forGetter(()), (App)ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter(())).apply((Applicative)$$0, AttachedToLeavesDecorator::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<AttachedToLeavesDecorator> CODEC;
/*    */   
/*    */   protected final float probability;
/*    */   
/*    */   protected final int exclusionRadiusXZ;
/*    */   
/*    */   protected final int exclusionRadiusY;
/*    */   
/*    */   protected final BlockStateProvider blockProvider;
/*    */   protected final int requiredEmptyBlocks;
/*    */   protected final List<Direction> directions;
/*    */   
/*    */   public AttachedToLeavesDecorator(float $$0, int $$1, int $$2, BlockStateProvider $$3, int $$4, List<Direction> $$5) {
/* 34 */     this.probability = $$0;
/* 35 */     this.exclusionRadiusXZ = $$1;
/* 36 */     this.exclusionRadiusY = $$2;
/* 37 */     this.blockProvider = $$3;
/* 38 */     this.requiredEmptyBlocks = $$4;
/* 39 */     this.directions = $$5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(TreeDecorator.Context $$0) {
/* 44 */     Set<BlockPos> $$1 = new HashSet<>();
/*    */     
/* 46 */     RandomSource $$2 = $$0.random();
/* 47 */     for (BlockPos $$3 : Util.shuffledCopy($$0.leaves(), $$2)) {
/* 48 */       Direction $$4 = (Direction)Util.getRandom(this.directions, $$2);
/* 49 */       BlockPos $$5 = $$3.relative($$4);
/* 50 */       if ($$1.contains($$5)) {
/*    */         continue;
/*    */       }
/* 53 */       if ($$2.nextFloat() < this.probability && 
/* 54 */         hasRequiredEmptyBlocks($$0, $$3, $$4)) {
/*    */         
/* 56 */         BlockPos $$6 = $$5.offset(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
/* 57 */         BlockPos $$7 = $$5.offset(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);
/* 58 */         for (BlockPos $$8 : BlockPos.betweenClosed($$6, $$7)) {
/* 59 */           $$1.add($$8.immutable());
/*    */         }
/*    */         
/* 62 */         $$0.setBlock($$5, this.blockProvider.getState($$2, $$5));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean hasRequiredEmptyBlocks(TreeDecorator.Context $$0, BlockPos $$1, Direction $$2) {
/* 69 */     for (int $$3 = 1; $$3 <= this.requiredEmptyBlocks; $$3++) {
/* 70 */       BlockPos $$4 = $$1.relative($$2, $$3);
/* 71 */       if (!$$0.isAir($$4)) {
/* 72 */         return false;
/*    */       }
/*    */     } 
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TreeDecoratorType<?> type() {
/* 80 */     return TreeDecoratorType.ATTACHED_TO_LEAVES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\AttachedToLeavesDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */