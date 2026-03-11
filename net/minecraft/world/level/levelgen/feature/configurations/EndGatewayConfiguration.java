/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class EndGatewayConfiguration implements FeatureConfiguration {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPos.CODEC.optionalFieldOf("exit").forGetter(()), (App)Codec.BOOL.fieldOf("exact").forGetter(())).apply((Applicative)$$0, EndGatewayConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<EndGatewayConfiguration> CODEC;
/*    */   private final Optional<BlockPos> exit;
/*    */   private final boolean exact;
/*    */   
/*    */   private EndGatewayConfiguration(Optional<BlockPos> $$0, boolean $$1) {
/* 19 */     this.exit = $$0;
/* 20 */     this.exact = $$1;
/*    */   }
/*    */   
/*    */   public static EndGatewayConfiguration knownExit(BlockPos $$0, boolean $$1) {
/* 24 */     return new EndGatewayConfiguration(Optional.of($$0), $$1);
/*    */   }
/*    */   
/*    */   public static EndGatewayConfiguration delayedExitSearch() {
/* 28 */     return new EndGatewayConfiguration(Optional.empty(), false);
/*    */   }
/*    */   
/*    */   public Optional<BlockPos> getExit() {
/* 32 */     return this.exit;
/*    */   }
/*    */   
/*    */   public boolean isExitExact() {
/* 36 */     return this.exact;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\EndGatewayConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */