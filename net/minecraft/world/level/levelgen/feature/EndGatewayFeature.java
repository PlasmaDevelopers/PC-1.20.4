/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelWriter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
/*    */ 
/*    */ public class EndGatewayFeature extends Feature<EndGatewayConfiguration> {
/*    */   public EndGatewayFeature(Codec<EndGatewayConfiguration> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<EndGatewayConfiguration> $$0) {
/* 18 */     BlockPos $$1 = $$0.origin();
/* 19 */     WorldGenLevel $$2 = $$0.level();
/* 20 */     EndGatewayConfiguration $$3 = $$0.config();
/* 21 */     for (Iterator<BlockPos> iterator = BlockPos.betweenClosed($$1.offset(-1, -2, -1), $$1.offset(1, 2, 1)).iterator(); iterator.hasNext(); ) { BlockPos $$4 = iterator.next();
/* 22 */       boolean $$5 = ($$4.getX() == $$1.getX());
/* 23 */       boolean $$6 = ($$4.getY() == $$1.getY());
/* 24 */       boolean $$7 = ($$4.getZ() == $$1.getZ());
/* 25 */       boolean $$8 = (Math.abs($$4.getY() - $$1.getY()) == 2);
/*    */       
/* 27 */       if ($$5 && $$6 && $$7) {
/* 28 */         BlockPos $$9 = $$4.immutable();
/* 29 */         setBlock((LevelWriter)$$2, $$9, Blocks.END_GATEWAY.defaultBlockState());
/* 30 */         $$3.getExit().ifPresent($$3 -> {
/*    */               BlockEntity $$4 = $$0.getBlockEntity($$1); if ($$4 instanceof TheEndGatewayBlockEntity) {
/*    */                 TheEndGatewayBlockEntity $$5 = (TheEndGatewayBlockEntity)$$4; $$5.setExitPosition($$3, $$2.isExitExact()); $$4.setChanged();
/*    */               } 
/*    */             });
/*    */         continue;
/*    */       } 
/* 37 */       if ($$6) {
/* 38 */         setBlock((LevelWriter)$$2, $$4, Blocks.AIR.defaultBlockState()); continue;
/* 39 */       }  if ($$8 && $$5 && $$7) {
/* 40 */         setBlock((LevelWriter)$$2, $$4, Blocks.BEDROCK.defaultBlockState()); continue;
/* 41 */       }  if ((!$$5 && !$$7) || $$8) {
/* 42 */         setBlock((LevelWriter)$$2, $$4, Blocks.AIR.defaultBlockState()); continue;
/*    */       } 
/* 44 */       setBlock((LevelWriter)$$2, $$4, Blocks.BEDROCK.defaultBlockState()); }
/*    */ 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\EndGatewayFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */