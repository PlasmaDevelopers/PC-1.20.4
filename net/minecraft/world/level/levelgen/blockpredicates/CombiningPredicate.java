/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ abstract class CombiningPredicate
/*    */   implements BlockPredicate {
/*    */   protected CombiningPredicate(List<BlockPredicate> $$0) {
/* 13 */     this.predicates = $$0;
/*    */   }
/*    */   protected final List<BlockPredicate> predicates;
/*    */   public static <T extends CombiningPredicate> Codec<T> codec(Function<List<BlockPredicate>, T> $$0) {
/* 17 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)BlockPredicate.CODEC.listOf().fieldOf("predicates").forGetter(())).apply((Applicative)$$1, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\CombiningPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */