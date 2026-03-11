/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ 
/*    */ public class InsideWorldBoundsPredicate implements BlockPredicate {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Vec3i.offsetCodec(16).optionalFieldOf("offset", BlockPos.ZERO).forGetter(())).apply((Applicative)$$0, InsideWorldBoundsPredicate::new));
/*    */   }
/*    */   
/*    */   public static final Codec<InsideWorldBoundsPredicate> CODEC;
/*    */   private final Vec3i offset;
/*    */   
/*    */   public InsideWorldBoundsPredicate(Vec3i $$0) {
/* 17 */     this.offset = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 22 */     return !$$0.isOutsideBuildHeight($$1.offset(this.offset));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 27 */     return BlockPredicateType.INSIDE_WORLD_BOUNDS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\InsideWorldBoundsPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */