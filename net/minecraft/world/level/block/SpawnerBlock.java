/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.Spawner;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SpawnerBlock extends BaseEntityBlock {
/* 23 */   public static final MapCodec<SpawnerBlock> CODEC = simpleCodec(SpawnerBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SpawnerBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected SpawnerBlock(BlockBehaviour.Properties $$0) {
/* 31 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 36 */     return (BlockEntity)new SpawnerBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 42 */     return createTickerHelper($$2, BlockEntityType.MOB_SPAWNER, $$0.isClientSide ? SpawnerBlockEntity::clientTick : SpawnerBlockEntity::serverTick);
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/* 47 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 49 */     if ($$4) {
/* 50 */       int $$5 = 15 + $$1.random.nextInt(15) + $$1.random.nextInt(15);
/* 51 */       popExperience($$1, $$2, $$5);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 57 */     return RenderShape.MODEL;
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable BlockGetter $$1, List<Component> $$2, TooltipFlag $$3) {
/* 62 */     super.appendHoverText($$0, $$1, $$2, $$3);
/* 63 */     Spawner.appendHoverText($$0, $$2, "SpawnData");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SpawnerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */