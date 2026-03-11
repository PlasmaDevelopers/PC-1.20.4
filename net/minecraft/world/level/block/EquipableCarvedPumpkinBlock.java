/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ 
/*    */ public class EquipableCarvedPumpkinBlock extends CarvedPumpkinBlock implements Equipable {
/*  8 */   public static final MapCodec<EquipableCarvedPumpkinBlock> CODEC = simpleCodec(EquipableCarvedPumpkinBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<EquipableCarvedPumpkinBlock> codec() {
/* 12 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected EquipableCarvedPumpkinBlock(BlockBehaviour.Properties $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public EquipmentSlot getEquipmentSlot() {
/* 21 */     return EquipmentSlot.HEAD;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EquipableCarvedPumpkinBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */