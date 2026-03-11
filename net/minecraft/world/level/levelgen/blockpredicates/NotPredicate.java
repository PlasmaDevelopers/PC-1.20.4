/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ 
/*    */ class NotPredicate implements BlockPredicate {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPredicate.CODEC.fieldOf("predicate").forGetter(())).apply((Applicative)$$0, NotPredicate::new));
/*    */   }
/*    */   
/*    */   public static final Codec<NotPredicate> CODEC;
/*    */   private final BlockPredicate predicate;
/*    */   
/*    */   public NotPredicate(BlockPredicate $$0) {
/* 16 */     this.predicate = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 21 */     return !this.predicate.test($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 26 */     return BlockPredicateType.NOT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\NotPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */