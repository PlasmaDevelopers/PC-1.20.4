/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.dispenser.BlockSource;
/*    */ import net.minecraft.core.dispenser.DispenseItemBehavior;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.DispenserBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.DropperBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.HopperBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DropperBlock extends DispenserBlock {
/* 22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 24 */   public static final MapCodec<DropperBlock> CODEC = simpleCodec(DropperBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<DropperBlock> codec() {
/* 28 */     return CODEC;
/*    */   }
/*    */   
/* 31 */   private static final DispenseItemBehavior DISPENSE_BEHAVIOUR = (DispenseItemBehavior)new DefaultDispenseItemBehavior();
/*    */   
/*    */   public DropperBlock(BlockBehaviour.Properties $$0) {
/* 34 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected DispenseItemBehavior getDispenseMethod(ItemStack $$0) {
/* 39 */     return DISPENSE_BEHAVIOUR;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 44 */     return (BlockEntity)new DropperBlockEntity($$0, $$1);
/*    */   }
/*    */   
/*    */   protected void dispenseFrom(ServerLevel $$0, BlockState $$1, BlockPos $$2) {
/*    */     ItemStack $$10;
/* 49 */     DispenserBlockEntity $$3 = $$0.getBlockEntity($$2, BlockEntityType.DROPPER).orElse(null);
/* 50 */     if ($$3 == null) {
/* 51 */       LOGGER.warn("Ignoring dispensing attempt for Dropper without matching block entity at {}", $$2);
/*    */       return;
/*    */     } 
/* 54 */     BlockSource $$4 = new BlockSource($$0, $$2, $$1, $$3);
/*    */     
/* 56 */     int $$5 = $$3.getRandomSlot($$0.random);
/* 57 */     if ($$5 < 0) {
/* 58 */       $$0.levelEvent(1001, $$2, 0);
/*    */       
/*    */       return;
/*    */     } 
/* 62 */     ItemStack $$6 = $$3.getItem($$5);
/* 63 */     if ($$6.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 67 */     Direction $$7 = (Direction)$$0.getBlockState($$2).getValue((Property)FACING);
/* 68 */     Container $$8 = HopperBlockEntity.getContainerAt((Level)$$0, $$2.relative($$7));
/*    */ 
/*    */     
/* 71 */     if ($$8 == null) {
/* 72 */       ItemStack $$9 = DISPENSE_BEHAVIOUR.dispense($$4, $$6);
/*    */     } else {
/* 74 */       $$10 = HopperBlockEntity.addItem((Container)$$3, $$8, $$6.copy().split(1), $$7.getOpposite());
/*    */       
/* 76 */       if ($$10.isEmpty()) {
/* 77 */         $$10 = $$6.copy();
/* 78 */         $$10.shrink(1);
/*    */       } else {
/*    */         
/* 81 */         $$10 = $$6.copy();
/*    */       } 
/*    */     } 
/*    */     
/* 85 */     $$3.setItem($$5, $$10);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DropperBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */