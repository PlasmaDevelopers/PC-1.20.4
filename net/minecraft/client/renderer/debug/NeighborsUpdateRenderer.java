/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Ordering;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class NeighborsUpdateRenderer implements DebugRenderer.SimpleDebugRenderer {
/*    */   private final Minecraft minecraft;
/* 21 */   private final Map<Long, Map<BlockPos, Integer>> lastUpdate = Maps.newTreeMap((Comparator)Ordering.natural().reverse());
/*    */   
/*    */   NeighborsUpdateRenderer(Minecraft $$0) {
/* 24 */     this.minecraft = $$0;
/*    */   }
/*    */   
/*    */   public void addUpdate(long $$0, BlockPos $$1) {
/* 28 */     Map<BlockPos, Integer> $$2 = this.lastUpdate.computeIfAbsent(Long.valueOf($$0), $$0 -> Maps.newHashMap());
/*    */     
/* 30 */     int $$3 = ((Integer)$$2.getOrDefault($$1, Integer.valueOf(0))).intValue();
/* 31 */     $$2.put($$1, Integer.valueOf($$3 + 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 36 */     long $$5 = this.minecraft.level.getGameTime();
/*    */     
/* 38 */     int $$6 = 200;
/* 39 */     double $$7 = 0.0025D;
/*    */     
/* 41 */     Set<BlockPos> $$8 = Sets.newHashSet();
/* 42 */     Map<BlockPos, Integer> $$9 = Maps.newHashMap();
/* 43 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.lines());
/*    */     
/* 45 */     for ($$11 = this.lastUpdate.entrySet().iterator(); $$11.hasNext(); ) {
/* 46 */       Map.Entry<Long, Map<BlockPos, Integer>> $$12 = $$11.next();
/* 47 */       Long $$13 = $$12.getKey();
/* 48 */       Map<BlockPos, Integer> $$14 = $$12.getValue();
/* 49 */       long $$15 = $$5 - $$13.longValue();
/*    */       
/* 51 */       if ($$15 > 200L) {
/* 52 */         $$11.remove();
/*    */         continue;
/*    */       } 
/* 55 */       for (Map.Entry<BlockPos, Integer> $$16 : $$14.entrySet()) {
/* 56 */         BlockPos $$17 = $$16.getKey();
/* 57 */         Integer $$18 = $$16.getValue();
/*    */         
/* 59 */         if ($$8.add($$17)) {
/* 60 */           AABB $$19 = (new AABB(BlockPos.ZERO)).inflate(0.002D).deflate(0.0025D * $$15).move($$17.getX(), $$17.getY(), $$17.getZ()).move(-$$2, -$$3, -$$4);
/* 61 */           LevelRenderer.renderLineBox($$0, $$10, $$19.minX, $$19.minY, $$19.minZ, $$19.maxX, $$19.maxY, $$19.maxZ, 1.0F, 1.0F, 1.0F, 1.0F);
/* 62 */           $$9.put($$17, $$18);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     for (Map.Entry<BlockPos, Integer> $$20 : $$9.entrySet()) {
/* 68 */       BlockPos $$21 = $$20.getKey();
/* 69 */       Integer $$22 = $$20.getValue();
/*    */       
/* 71 */       DebugRenderer.renderFloatingText($$0, $$1, String.valueOf($$22), $$21.getX(), $$21.getY(), $$21.getZ(), -1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\NeighborsUpdateRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */