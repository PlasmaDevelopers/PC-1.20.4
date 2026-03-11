/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.world.level.dimension.DimensionType;
/*    */ 
/*    */ public final class NoiseSettings extends Record {
/*    */   private final int minY;
/*    */   private final int height;
/*    */   private final int noiseSizeHorizontal;
/*    */   private final int noiseSizeVertical;
/*    */   public static final Codec<NoiseSettings> CODEC;
/*    */   
/* 14 */   public NoiseSettings(int $$0, int $$1, int $$2, int $$3) { this.minY = $$0; this.height = $$1; this.noiseSizeHorizontal = $$2; this.noiseSizeVertical = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/NoiseSettings;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/NoiseSettings; } public int minY() { return this.minY; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/NoiseSettings;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/NoiseSettings; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/NoiseSettings;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/NoiseSettings;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public int height() { return this.height; } public int noiseSizeHorizontal() { return this.noiseSizeHorizontal; } public int noiseSizeVertical() { return this.noiseSizeVertical; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 25 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("min_y").forGetter(NoiseSettings::minY), (App)Codec.intRange(0, DimensionType.Y_SIZE).fieldOf("height").forGetter(NoiseSettings::height), (App)Codec.intRange(1, 4).fieldOf("size_horizontal").forGetter(NoiseSettings::noiseSizeHorizontal), (App)Codec.intRange(1, 4).fieldOf("size_vertical").forGetter(NoiseSettings::noiseSizeVertical)).apply((Applicative)$$0, NoiseSettings::new)).comapFlatMap(NoiseSettings::guardY, Function.identity());
/*    */   }
/* 27 */   protected static final NoiseSettings OVERWORLD_NOISE_SETTINGS = create(-64, 384, 1, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   protected static final NoiseSettings NETHER_NOISE_SETTINGS = create(0, 128, 1, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   protected static final NoiseSettings END_NOISE_SETTINGS = create(0, 128, 2, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   protected static final NoiseSettings CAVES_NOISE_SETTINGS = create(-64, 192, 1, 2);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   protected static final NoiseSettings FLOATING_ISLANDS_NOISE_SETTINGS = create(0, 256, 2, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static DataResult<NoiseSettings> guardY(NoiseSettings $$0) {
/* 63 */     if ($$0.minY() + $$0.height() > DimensionType.MAX_Y + 1) {
/* 64 */       return DataResult.error(() -> "min_y + height cannot be higher than: " + DimensionType.MAX_Y + 1);
/*    */     }
/*    */     
/* 67 */     if ($$0.height() % 16 != 0) {
/* 68 */       return DataResult.error(() -> "height has to be a multiple of 16");
/*    */     }
/*    */     
/* 71 */     if ($$0.minY() % 16 != 0) {
/* 72 */       return DataResult.error(() -> "min_y has to be a multiple of 16");
/*    */     }
/*    */     
/* 75 */     return DataResult.success($$0);
/*    */   }
/*    */   
/*    */   public static NoiseSettings create(int $$0, int $$1, int $$2, int $$3) {
/* 79 */     NoiseSettings $$4 = new NoiseSettings($$0, $$1, $$2, $$3);
/*    */     
/* 81 */     guardY($$4).error().ifPresent($$0 -> {
/*    */           throw new IllegalStateException($$0.message());
/*    */         });
/*    */     
/* 85 */     return $$4;
/*    */   }
/*    */   
/*    */   public int getCellHeight() {
/* 89 */     return QuartPos.toBlock(noiseSizeVertical());
/*    */   }
/*    */   
/*    */   public int getCellWidth() {
/* 93 */     return QuartPos.toBlock(noiseSizeHorizontal());
/*    */   }
/*    */   
/*    */   public NoiseSettings clampToHeightAccessor(LevelHeightAccessor $$0) {
/* 97 */     int $$1 = Math.max(this.minY, $$0.getMinBuildHeight());
/* 98 */     int $$2 = Math.min(this.minY + this.height, $$0.getMaxBuildHeight()) - $$1;
/* 99 */     return new NoiseSettings($$1, $$2, this.noiseSizeHorizontal, this.noiseSizeVertical);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */