/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ 
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public abstract class StateTestingPredicate
/*    */   implements BlockPredicate {
/*    */   protected static <P extends StateTestingPredicate> Products.P1<RecordCodecBuilder.Mu<P>, Vec3i> stateTestingCodec(RecordCodecBuilder.Instance<P> $$0) {
/* 14 */     return $$0.group(
/* 15 */         (App)Vec3i.offsetCodec(16).optionalFieldOf("offset", Vec3i.ZERO).forGetter($$0 -> $$0.offset));
/*    */   }
/*    */   protected final Vec3i offset;
/*    */   
/*    */   protected StateTestingPredicate(Vec3i $$0) {
/* 20 */     this.offset = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 25 */     return test($$0.getBlockState($$1.offset(this.offset)));
/*    */   }
/*    */   
/*    */   protected abstract boolean test(BlockState paramBlockState);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\StateTestingPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */