/*    */ package net.minecraft.world.level.levelgen.structure.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function8;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
/*    */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*    */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*    */ 
/*    */ public class RandomSpreadStructurePlacement
/*    */   extends StructurePlacement
/*    */ {
/*    */   public static final Codec<RandomSpreadStructurePlacement> CODEC;
/*    */   private final int spacing;
/*    */   private final int separation;
/*    */   private final RandomSpreadType spreadType;
/*    */   
/*    */   static {
/* 27 */     CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec($$0 -> placementCodec($$0).and($$0.group((App)Codec.intRange(0, 4096).fieldOf("spacing").forGetter(RandomSpreadStructurePlacement::spacing), (App)Codec.intRange(0, 4096).fieldOf("separation").forGetter(RandomSpreadStructurePlacement::separation), (App)RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(RandomSpreadStructurePlacement::spreadType))).apply((Applicative)$$0, RandomSpreadStructurePlacement::new)), RandomSpreadStructurePlacement::validate).codec();
/*    */   }
/*    */   private static DataResult<RandomSpreadStructurePlacement> validate(RandomSpreadStructurePlacement $$0) {
/* 30 */     if ($$0.spacing <= $$0.separation) {
/* 31 */       return DataResult.error(() -> "Spacing has to be larger than separation");
/*    */     }
/* 33 */     return DataResult.success($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RandomSpreadStructurePlacement(Vec3i $$0, StructurePlacement.FrequencyReductionMethod $$1, float $$2, int $$3, Optional<StructurePlacement.ExclusionZone> $$4, int $$5, int $$6, RandomSpreadType $$7) {
/* 41 */     super($$0, $$1, $$2, $$3, $$4);
/* 42 */     this.spacing = $$5;
/* 43 */     this.separation = $$6;
/* 44 */     this.spreadType = $$7;
/*    */   }
/*    */   
/*    */   public RandomSpreadStructurePlacement(int $$0, int $$1, RandomSpreadType $$2, int $$3) {
/* 48 */     this(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, $$3, Optional.empty(), $$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public int spacing() {
/* 52 */     return this.spacing;
/*    */   }
/*    */   
/*    */   public int separation() {
/* 56 */     return this.separation;
/*    */   }
/*    */   
/*    */   public RandomSpreadType spreadType() {
/* 60 */     return this.spreadType;
/*    */   }
/*    */   
/*    */   public ChunkPos getPotentialStructureChunk(long $$0, int $$1, int $$2) {
/* 64 */     int $$3 = Math.floorDiv($$1, this.spacing);
/* 65 */     int $$4 = Math.floorDiv($$2, this.spacing);
/*    */     
/* 67 */     WorldgenRandom $$5 = new WorldgenRandom((RandomSource)new LegacyRandomSource(0L));
/* 68 */     $$5.setLargeFeatureWithSalt($$0, $$3, $$4, salt());
/*    */     
/* 70 */     int $$6 = this.spacing - this.separation;
/* 71 */     int $$7 = this.spreadType.evaluate((RandomSource)$$5, $$6);
/* 72 */     int $$8 = this.spreadType.evaluate((RandomSource)$$5, $$6);
/*    */     
/* 74 */     return new ChunkPos($$3 * this.spacing + $$7, $$4 * this.spacing + $$8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isPlacementChunk(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
/* 82 */     ChunkPos $$3 = getPotentialStructureChunk($$0.getLevelSeed(), $$1, $$2);
/* 83 */     return ($$3.x == $$1 && $$3.z == $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePlacementType<?> type() {
/* 88 */     return StructurePlacementType.RANDOM_SPREAD;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\placement\RandomSpreadStructurePlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */