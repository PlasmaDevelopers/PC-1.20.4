/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.ChunkAccess;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class HeightMapRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer
/*    */ {
/*    */   private final Minecraft minecraft;
/*    */   private static final int CHUNK_DIST = 2;
/*    */   private static final float BOX_HEIGHT = 0.09375F;
/*    */   
/*    */   public HeightMapRenderer(Minecraft $$0) {
/* 26 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 31 */     ClientLevel clientLevel = this.minecraft.level;
/* 32 */     VertexConsumer $$6 = $$1.getBuffer(RenderType.debugFilledBox());
/*    */     
/* 34 */     BlockPos $$7 = BlockPos.containing($$2, 0.0D, $$4);
/*    */     
/* 36 */     for (int $$8 = -2; $$8 <= 2; $$8++) {
/* 37 */       for (int $$9 = -2; $$9 <= 2; $$9++) {
/* 38 */         ChunkAccess $$10 = clientLevel.getChunk($$7.offset($$8 * 16, 0, $$9 * 16));
/* 39 */         for (Map.Entry<Heightmap.Types, Heightmap> $$11 : (Iterable<Map.Entry<Heightmap.Types, Heightmap>>)$$10.getHeightmaps()) {
/* 40 */           Heightmap.Types $$12 = $$11.getKey();
/* 41 */           ChunkPos $$13 = $$10.getPos();
/* 42 */           Vector3f $$14 = getColor($$12);
/* 43 */           for (int $$15 = 0; $$15 < 16; $$15++) {
/* 44 */             for (int $$16 = 0; $$16 < 16; $$16++) {
/* 45 */               int $$17 = SectionPos.sectionToBlockCoord($$13.x, $$15);
/* 46 */               int $$18 = SectionPos.sectionToBlockCoord($$13.z, $$16);
/* 47 */               float $$19 = (float)((clientLevel.getHeight($$12, $$17, $$18) + $$12.ordinal() * 0.09375F) - $$3);
/* 48 */               LevelRenderer.addChainedFilledBoxVertices($$0, $$6, ($$17 + 0.25F) - $$2, $$19, ($$18 + 0.25F) - $$4, ($$17 + 0.75F) - $$2, ($$19 + 0.09375F), ($$18 + 0.75F) - $$4, $$14.x(), $$14.y(), $$14.z(), 1.0F);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private Vector3f getColor(Heightmap.Types $$0) {
/* 57 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case WORLD_SURFACE_WG: case OCEAN_FLOOR_WG: case WORLD_SURFACE: case OCEAN_FLOOR: case MOTION_BLOCKING: case MOTION_BLOCKING_NO_LEAVES: break; }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 63 */       new Vector3f(0.0F, 0.5F, 0.5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\HeightMapRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */