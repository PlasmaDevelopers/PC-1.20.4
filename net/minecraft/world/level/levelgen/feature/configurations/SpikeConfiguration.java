/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.levelgen.feature.SpikeFeature;
/*    */ 
/*    */ public class SpikeConfiguration implements FeatureConfiguration {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.BOOL.fieldOf("crystal_invulnerable").orElse(Boolean.valueOf(false)).forGetter(()), (App)SpikeFeature.EndSpike.CODEC.listOf().fieldOf("spikes").forGetter(()), (App)BlockPos.CODEC.optionalFieldOf("crystal_beam_target").forGetter(())).apply((Applicative)$$0, SpikeConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SpikeConfiguration> CODEC;
/*    */   
/*    */   private final boolean crystalInvulnerable;
/*    */   private final List<SpikeFeature.EndSpike> spikes;
/*    */   @Nullable
/*    */   private final BlockPos crystalBeamTarget;
/*    */   
/*    */   public SpikeConfiguration(boolean $$0, List<SpikeFeature.EndSpike> $$1, @Nullable BlockPos $$2) {
/* 25 */     this($$0, $$1, Optional.ofNullable($$2));
/*    */   }
/*    */   
/*    */   private SpikeConfiguration(boolean $$0, List<SpikeFeature.EndSpike> $$1, Optional<BlockPos> $$2) {
/* 29 */     this.crystalInvulnerable = $$0;
/* 30 */     this.spikes = $$1;
/* 31 */     this.crystalBeamTarget = $$2.orElse(null);
/*    */   }
/*    */   
/*    */   public boolean isCrystalInvulnerable() {
/* 35 */     return this.crystalInvulnerable;
/*    */   }
/*    */   
/*    */   public List<SpikeFeature.EndSpike> getSpikes() {
/* 39 */     return this.spikes;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockPos getCrystalBeamTarget() {
/* 44 */     return this.crystalBeamTarget;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\SpikeConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */