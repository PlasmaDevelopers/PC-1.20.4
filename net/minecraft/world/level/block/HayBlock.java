/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class HayBlock extends RotatedPillarBlock {
/* 11 */   public static final MapCodec<HayBlock> CODEC = simpleCodec(HayBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<HayBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */   
/*    */   public HayBlock(BlockBehaviour.Properties $$0) {
/* 19 */     super($$0);
/* 20 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AXIS, (Comparable)Direction.Axis.Y));
/*    */   }
/*    */ 
/*    */   
/*    */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/* 25 */     $$3.causeFallDamage($$4, 0.2F, $$0.damageSources().fall());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HayBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */