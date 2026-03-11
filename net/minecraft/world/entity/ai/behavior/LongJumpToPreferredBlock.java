/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiPredicate;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class LongJumpToPreferredBlock<E extends Mob> extends LongJumpToRandomPos<E> {
/*    */   private final TagKey<Block> preferredBlockTag;
/*    */   private final float preferredBlocksChance;
/* 22 */   private final List<LongJumpToRandomPos.PossibleJump> notPrefferedJumpCandidates = new ArrayList<>();
/*    */   private boolean currentlyWantingPreferredOnes;
/*    */   
/*    */   public LongJumpToPreferredBlock(UniformInt $$0, int $$1, int $$2, float $$3, Function<E, SoundEvent> $$4, TagKey<Block> $$5, float $$6, BiPredicate<E, BlockPos> $$7) {
/* 26 */     super($$0, $$1, $$2, $$3, $$4, $$7);
/* 27 */     this.preferredBlockTag = $$5;
/* 28 */     this.preferredBlocksChance = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, E $$1, long $$2) {
/* 33 */     super.start($$0, $$1, $$2);
/*    */     
/* 35 */     this.notPrefferedJumpCandidates.clear();
/*    */     
/* 37 */     this.currentlyWantingPreferredOnes = ($$1.getRandom().nextFloat() < this.preferredBlocksChance);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Optional<LongJumpToRandomPos.PossibleJump> getJumpCandidate(ServerLevel $$0) {
/* 42 */     if (!this.currentlyWantingPreferredOnes) {
/* 43 */       return super.getJumpCandidate($$0);
/*    */     }
/*    */     
/* 46 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*    */     
/* 48 */     while (!this.jumpCandidates.isEmpty()) {
/* 49 */       Optional<LongJumpToRandomPos.PossibleJump> $$2 = super.getJumpCandidate($$0);
/*    */       
/* 51 */       if ($$2.isPresent()) {
/* 52 */         LongJumpToRandomPos.PossibleJump $$3 = $$2.get();
/*    */         
/* 54 */         if ($$0.getBlockState((BlockPos)$$1.setWithOffset((Vec3i)$$3.getJumpTarget(), Direction.DOWN)).is(this.preferredBlockTag)) {
/* 55 */           return $$2;
/*    */         }
/*    */         
/* 58 */         this.notPrefferedJumpCandidates.add($$3);
/*    */       } 
/*    */     } 
/*    */     
/* 62 */     if (!this.notPrefferedJumpCandidates.isEmpty()) {
/* 63 */       return Optional.of(this.notPrefferedJumpCandidates.remove(0));
/*    */     }
/*    */     
/* 66 */     return Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LongJumpToPreferredBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */