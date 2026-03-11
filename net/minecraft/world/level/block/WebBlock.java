/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class WebBlock extends Block {
/* 11 */   public static final MapCodec<WebBlock> CODEC = simpleCodec(WebBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<WebBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */   
/*    */   public WebBlock(BlockBehaviour.Properties $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 24 */     $$3.makeStuckInBlock($$0, new Vec3(0.25D, 0.05000000074505806D, 0.25D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WebBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */