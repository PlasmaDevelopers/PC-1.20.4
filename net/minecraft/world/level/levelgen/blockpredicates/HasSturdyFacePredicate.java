/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ 
/*    */ public class HasSturdyFacePredicate implements BlockPredicate {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Vec3i.offsetCodec(16).optionalFieldOf("offset", Vec3i.ZERO).forGetter(()), (App)Direction.CODEC.fieldOf("direction").forGetter(())).apply((Applicative)$$0, HasSturdyFacePredicate::new));
/*    */   }
/*    */   private final Vec3i offset; private final Direction direction;
/*    */   public static final Codec<HasSturdyFacePredicate> CODEC;
/*    */   
/*    */   public HasSturdyFacePredicate(Vec3i $$0, Direction $$1) {
/* 20 */     this.offset = $$0;
/* 21 */     this.direction = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 26 */     BlockPos $$2 = $$1.offset(this.offset);
/* 27 */     return $$0.getBlockState($$2).isFaceSturdy((BlockGetter)$$0, $$2, this.direction);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 32 */     return BlockPredicateType.HAS_STURDY_FACE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\HasSturdyFacePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */