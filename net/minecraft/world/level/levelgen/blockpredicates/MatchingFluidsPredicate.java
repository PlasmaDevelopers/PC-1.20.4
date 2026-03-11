/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.RegistryCodecs;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ 
/*    */ class MatchingFluidsPredicate extends StateTestingPredicate {
/*    */   private final HolderSet<Fluid> fluids;
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> stateTestingCodec($$0).and((App)RegistryCodecs.homogeneousList(Registries.FLUID).fieldOf("fluids").forGetter(())).apply((Applicative)$$0, MatchingFluidsPredicate::new));
/*    */   }
/*    */   public static final Codec<MatchingFluidsPredicate> CODEC;
/*    */   
/*    */   public MatchingFluidsPredicate(Vec3i $$0, HolderSet<Fluid> $$1) {
/* 20 */     super($$0);
/* 21 */     this.fluids = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean test(BlockState $$0) {
/* 26 */     return $$0.getFluidState().is(this.fluids);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 31 */     return BlockPredicateType.MATCHING_FLUIDS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\MatchingFluidsPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */