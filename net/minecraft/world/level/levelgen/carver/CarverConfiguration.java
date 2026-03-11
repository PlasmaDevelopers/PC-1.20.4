/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function6;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*    */ 
/*    */ public class CarverConfiguration extends ProbabilityFeatureConfiguration {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(()), (App)HeightProvider.CODEC.fieldOf("y").forGetter(()), (App)FloatProvider.CODEC.fieldOf("yScale").forGetter(()), (App)VerticalAnchor.CODEC.fieldOf("lava_level").forGetter(()), (App)CarverDebugSettings.CODEC.optionalFieldOf("debug_settings", CarverDebugSettings.DEFAULT).forGetter(()), (App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable").forGetter(())).apply((Applicative)$$0, CarverConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<CarverConfiguration> CODEC;
/*    */   
/*    */   public final HeightProvider y;
/*    */   
/*    */   public final FloatProvider yScale;
/*    */   
/*    */   public final VerticalAnchor lavaLevel;
/*    */   
/*    */   public final CarverDebugSettings debugSettings;
/*    */   
/*    */   public final HolderSet<Block> replaceable;
/*    */   
/*    */   public CarverConfiguration(float $$0, HeightProvider $$1, FloatProvider $$2, VerticalAnchor $$3, CarverDebugSettings $$4, HolderSet<Block> $$5) {
/* 33 */     super($$0);
/* 34 */     this.y = $$1;
/* 35 */     this.yScale = $$2;
/* 36 */     this.lavaLevel = $$3;
/* 37 */     this.debugSettings = $$4;
/* 38 */     this.replaceable = $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CarverConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */