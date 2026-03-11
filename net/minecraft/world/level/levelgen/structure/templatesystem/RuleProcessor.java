/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class RuleProcessor extends StructureProcessor {
/*    */   public static final Codec<RuleProcessor> CODEC;
/*    */   
/*    */   static {
/* 15 */     CODEC = ProcessorRule.CODEC.listOf().fieldOf("rules").xmap(RuleProcessor::new, $$0 -> $$0.rules).codec();
/*    */   }
/*    */   private final ImmutableList<ProcessorRule> rules;
/*    */   
/*    */   public RuleProcessor(List<? extends ProcessorRule> $$0) {
/* 20 */     this.rules = ImmutableList.copyOf($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 26 */     RandomSource $$6 = RandomSource.create(Mth.getSeed((Vec3i)$$4.pos()));
/* 27 */     BlockState $$7 = $$0.getBlockState($$4.pos());
/* 28 */     for (UnmodifiableIterator<ProcessorRule> unmodifiableIterator = this.rules.iterator(); unmodifiableIterator.hasNext(); ) { ProcessorRule $$8 = unmodifiableIterator.next();
/* 29 */       if ($$8.test($$4.state(), $$7, $$3.pos(), $$4.pos(), $$2, $$6)) {
/* 30 */         return new StructureTemplate.StructureBlockInfo($$4.pos(), $$8.getOutputState(), $$8.getOutputTag($$6, $$4.nbt()));
/*    */       } }
/*    */     
/* 33 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 38 */     return StructureProcessorType.RULE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\RuleProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */