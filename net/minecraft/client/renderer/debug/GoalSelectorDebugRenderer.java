/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.network.protocol.common.custom.GoalDebugPayload;
/*    */ 
/*    */ public class GoalSelectorDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
/*    */   private static final int MAX_RENDER_DIST = 160;
/*    */   private final Minecraft minecraft;
/* 18 */   private final Int2ObjectMap<EntityGoalInfo> goalSelectors = (Int2ObjectMap<EntityGoalInfo>)new Int2ObjectOpenHashMap();
/*    */ 
/*    */   
/*    */   public void clear() {
/* 22 */     this.goalSelectors.clear();
/*    */   }
/*    */   
/*    */   public void addGoalSelector(int $$0, BlockPos $$1, List<GoalDebugPayload.DebugGoal> $$2) {
/* 26 */     this.goalSelectors.put($$0, new EntityGoalInfo($$1, $$2));
/*    */   }
/*    */   
/*    */   public void removeGoalSelector(int $$0) {
/* 30 */     this.goalSelectors.remove($$0);
/*    */   }
/*    */   
/*    */   public GoalSelectorDebugRenderer(Minecraft $$0) {
/* 34 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 39 */     Camera $$5 = this.minecraft.gameRenderer.getMainCamera();
/*    */     
/* 41 */     BlockPos $$6 = BlockPos.containing(($$5.getPosition()).x, 0.0D, ($$5.getPosition()).z);
/*    */     
/* 43 */     for (ObjectIterator<EntityGoalInfo> objectIterator = this.goalSelectors.values().iterator(); objectIterator.hasNext(); ) { EntityGoalInfo $$7 = objectIterator.next();
/* 44 */       BlockPos $$8 = $$7.entityPos;
/* 45 */       if ($$6.closerThan((Vec3i)$$8, 160.0D))
/* 46 */         for (int $$9 = 0; $$9 < $$7.goals.size(); $$9++) {
/* 47 */           GoalDebugPayload.DebugGoal $$10 = $$7.goals.get($$9);
/* 48 */           double $$11 = $$8.getX() + 0.5D;
/* 49 */           double $$12 = $$8.getY() + 2.0D + $$9 * 0.25D;
/* 50 */           double $$13 = $$8.getZ() + 0.5D;
/* 51 */           int $$14 = $$10.isRunning() ? -16711936 : -3355444;
/* 52 */           DebugRenderer.renderFloatingText($$0, $$1, $$10.name(), $$11, $$12, $$13, $$14);
/*    */         }   }
/*    */   
/*    */   }
/*    */   private static final class EntityGoalInfo extends Record { final BlockPos entityPos; final List<GoalDebugPayload.DebugGoal> goals;
/*    */     
/* 58 */     EntityGoalInfo(BlockPos $$0, List<GoalDebugPayload.DebugGoal> $$1) { this.entityPos = $$0; this.goals = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/debug/GoalSelectorDebugRenderer$EntityGoalInfo;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #58	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 58 */       //   0	7	0	this	Lnet/minecraft/client/renderer/debug/GoalSelectorDebugRenderer$EntityGoalInfo; } public BlockPos entityPos() { return this.entityPos; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/debug/GoalSelectorDebugRenderer$EntityGoalInfo;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #58	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/renderer/debug/GoalSelectorDebugRenderer$EntityGoalInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/debug/GoalSelectorDebugRenderer$EntityGoalInfo;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #58	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/renderer/debug/GoalSelectorDebugRenderer$EntityGoalInfo;
/* 58 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<GoalDebugPayload.DebugGoal> goals() { return this.goals; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\GoalSelectorDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */