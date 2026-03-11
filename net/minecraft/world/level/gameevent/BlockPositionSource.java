/*    */ package net.minecraft.world.level.gameevent;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BlockPositionSource implements PositionSource {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPos.CODEC.fieldOf("pos").forGetter(())).apply((Applicative)$$0, BlockPositionSource::new));
/*    */   }
/*    */   
/*    */   public static final Codec<BlockPositionSource> CODEC;
/*    */   final BlockPos pos;
/*    */   
/*    */   public BlockPositionSource(BlockPos $$0) {
/* 20 */     this.pos = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Vec3> getPosition(Level $$0) {
/* 25 */     return Optional.of(Vec3.atCenterOf((Vec3i)this.pos));
/*    */   }
/*    */ 
/*    */   
/*    */   public PositionSourceType<?> getType() {
/* 30 */     return PositionSourceType.BLOCK;
/*    */   }
/*    */   
/*    */   public static class Type
/*    */     implements PositionSourceType<BlockPositionSource> {
/*    */     public BlockPositionSource read(FriendlyByteBuf $$0) {
/* 36 */       return new BlockPositionSource($$0.readBlockPos());
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(FriendlyByteBuf $$0, BlockPositionSource $$1) {
/* 41 */       $$0.writeBlockPos($$1.pos);
/*    */     }
/*    */ 
/*    */     
/*    */     public Codec<BlockPositionSource> codec() {
/* 46 */       return BlockPositionSource.CODEC;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\BlockPositionSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */