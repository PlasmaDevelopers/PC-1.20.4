/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WouldSurvivePredicate implements BlockPredicate {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Vec3i.offsetCodec(16).optionalFieldOf("offset", Vec3i.ZERO).forGetter(()), (App)BlockState.CODEC.fieldOf("state").forGetter(())).apply((Applicative)$$0, WouldSurvivePredicate::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<WouldSurvivePredicate> CODEC;
/*    */   private final Vec3i offset;
/*    */   private final BlockState state;
/*    */   
/*    */   protected WouldSurvivePredicate(Vec3i $$0, BlockState $$1) {
/* 20 */     this.offset = $$0;
/* 21 */     this.state = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 26 */     return this.state.canSurvive((LevelReader)$$0, $$1.offset(this.offset));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 31 */     return BlockPredicateType.WOULD_SURVIVE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\WouldSurvivePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */