/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.Passthrough;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
/*    */ 
/*    */ public class ProcessorRule
/*    */ {
/* 18 */   public static final Passthrough DEFAULT_BLOCK_ENTITY_MODIFIER = Passthrough.INSTANCE;
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RuleTest.CODEC.fieldOf("input_predicate").forGetter(()), (App)RuleTest.CODEC.fieldOf("location_predicate").forGetter(()), (App)PosRuleTest.CODEC.optionalFieldOf("position_predicate", PosAlwaysTrueTest.INSTANCE).forGetter(()), (App)BlockState.CODEC.fieldOf("output_state").forGetter(()), (App)RuleBlockEntityModifier.CODEC.optionalFieldOf("block_entity_modifier", DEFAULT_BLOCK_ENTITY_MODIFIER).forGetter(())).apply((Applicative)$$0, ProcessorRule::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Codec<ProcessorRule> CODEC;
/*    */ 
/*    */   
/*    */   private final RuleTest inputPredicate;
/*    */   
/*    */   private final RuleTest locPredicate;
/*    */   
/*    */   private final PosRuleTest posPredicate;
/*    */   
/*    */   private final BlockState outputState;
/*    */   
/*    */   private final RuleBlockEntityModifier blockEntityModifier;
/*    */ 
/*    */   
/*    */   public ProcessorRule(RuleTest $$0, RuleTest $$1, BlockState $$2) {
/* 40 */     this($$0, $$1, PosAlwaysTrueTest.INSTANCE, $$2);
/*    */   }
/*    */   
/*    */   public ProcessorRule(RuleTest $$0, RuleTest $$1, PosRuleTest $$2, BlockState $$3) {
/* 44 */     this($$0, $$1, $$2, $$3, (RuleBlockEntityModifier)DEFAULT_BLOCK_ENTITY_MODIFIER);
/*    */   }
/*    */   
/*    */   public ProcessorRule(RuleTest $$0, RuleTest $$1, PosRuleTest $$2, BlockState $$3, RuleBlockEntityModifier $$4) {
/* 48 */     this.inputPredicate = $$0;
/* 49 */     this.locPredicate = $$1;
/* 50 */     this.posPredicate = $$2;
/* 51 */     this.outputState = $$3;
/* 52 */     this.blockEntityModifier = $$4;
/*    */   }
/*    */   
/*    */   public boolean test(BlockState $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, BlockPos $$4, RandomSource $$5) {
/* 56 */     return (this.inputPredicate.test($$0, $$5) && this.locPredicate.test($$1, $$5) && this.posPredicate.test($$2, $$3, $$4, $$5));
/*    */   }
/*    */   
/*    */   public BlockState getOutputState() {
/* 60 */     return this.outputState;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public CompoundTag getOutputTag(RandomSource $$0, @Nullable CompoundTag $$1) {
/* 65 */     return this.blockEntityModifier.apply($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\ProcessorRule.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */