/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ 
/*    */ public class CappedProcessor extends StructureProcessor {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)StructureProcessorType.SINGLE_CODEC.fieldOf("delegate").forGetter(()), (App)IntProvider.POSITIVE_CODEC.fieldOf("limit").forGetter(())).apply((Applicative)$$0, CappedProcessor::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<CappedProcessor> CODEC;
/*    */   private final StructureProcessor delegate;
/*    */   private final IntProvider limit;
/*    */   
/*    */   public CappedProcessor(StructureProcessor $$0, IntProvider $$1) {
/* 26 */     this.delegate = $$0;
/* 27 */     this.limit = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 32 */     return StructureProcessorType.CAPPED;
/*    */   }
/*    */ 
/*    */   
/*    */   public final List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor $$0, BlockPos $$1, BlockPos $$2, List<StructureTemplate.StructureBlockInfo> $$3, List<StructureTemplate.StructureBlockInfo> $$4, StructurePlaceSettings $$5) {
/* 37 */     if (this.limit.getMaxValue() == 0 || $$4.isEmpty()) {
/* 38 */       return $$4;
/*    */     }
/*    */     
/* 41 */     if ($$3.size() != $$4.size()) {
/* 42 */       Util.logAndPauseIfInIde("Original block info list not in sync with processed list, skipping processing. Original size: " + $$3.size() + ", Processed size: " + $$4.size());
/* 43 */       return $$4;
/*    */     } 
/*    */     
/* 46 */     RandomSource $$6 = RandomSource.create($$0.getLevel().getSeed()).forkPositional().at($$1);
/*    */     
/* 48 */     int $$7 = Math.min(this.limit.sample($$6), $$4.size());
/*    */     
/* 50 */     if ($$7 < 1) {
/* 51 */       return $$4;
/*    */     }
/*    */     
/* 54 */     IntArrayList $$8 = Util.toShuffledList(IntStream.range(0, $$4.size()), $$6);
/*    */     
/* 56 */     IntIterator $$9 = $$8.intIterator();
/* 57 */     int $$10 = 0;
/*    */     
/* 59 */     while ($$9.hasNext() && $$10 < $$7) {
/* 60 */       int $$11 = $$9.nextInt();
/* 61 */       StructureTemplate.StructureBlockInfo $$12 = $$3.get($$11);
/* 62 */       StructureTemplate.StructureBlockInfo $$13 = $$4.get($$11);
/*    */       
/* 64 */       StructureTemplate.StructureBlockInfo $$14 = this.delegate.processBlock((LevelReader)$$0, $$1, $$2, $$12, $$13, $$5);
/*    */       
/* 66 */       if ($$14 != null && !$$13.equals($$14)) {
/* 67 */         $$10++;
/* 68 */         $$4.set($$11, $$14);
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\CappedProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */