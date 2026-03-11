/*    */ package net.minecraft.gametest.framework;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class GameTestTicker
/*    */ {
/*  8 */   public static final GameTestTicker SINGLETON = new GameTestTicker();
/*  9 */   private final Collection<GameTestInfo> testInfos = Lists.newCopyOnWriteArrayList();
/*    */   
/*    */   public void add(GameTestInfo $$0) {
/* 12 */     this.testInfos.add($$0);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 16 */     this.testInfos.clear();
/*    */   }
/*    */   
/*    */   public void tick() {
/* 20 */     this.testInfos.forEach(GameTestInfo::tick);
/* 21 */     this.testInfos.removeIf(GameTestInfo::isDone);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestTicker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */