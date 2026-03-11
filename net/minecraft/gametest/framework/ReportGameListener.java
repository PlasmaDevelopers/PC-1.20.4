/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import java.util.Arrays;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LecternBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ 
/*     */ class ReportGameListener implements GameTestListener {
/*     */   private final GameTestInfo originalTestInfo;
/*     */   private final GameTestTicker testTicker;
/*     */   
/*     */   public ReportGameListener(GameTestInfo $$0, GameTestTicker $$1, BlockPos $$2) {
/*  33 */     this.originalTestInfo = $$0;
/*  34 */     this.testTicker = $$1;
/*  35 */     this.structurePos = $$2;
/*  36 */     this.attempts = 0;
/*  37 */     this.successes = 0;
/*     */   }
/*     */   private final BlockPos structurePos; int attempts; int successes;
/*     */   
/*     */   public void testStructureLoaded(GameTestInfo $$0) {
/*  42 */     spawnBeacon(this.originalTestInfo, Blocks.LIGHT_GRAY_STAINED_GLASS);
/*  43 */     this.attempts++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void testPassed(GameTestInfo $$0) {
/*  48 */     this.successes++;
/*  49 */     if ($$0.rerunUntilFailed()) {
/*  50 */       reportPassed($$0, $$0.getTestName() + " passed! (" + $$0.getTestName() + "ms). Rerunning until failed.");
/*  51 */       rerunTest();
/*     */       return;
/*     */     } 
/*  54 */     if (!$$0.isFlaky()) {
/*  55 */       reportPassed($$0, $$0.getTestName() + " passed! (" + $$0.getTestName() + "ms)");
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     if (this.successes >= $$0.requiredSuccesses()) {
/*  60 */       reportPassed($$0, "" + $$0 + " passed " + $$0 + " times of " + this.successes + " attempts.");
/*     */     } else {
/*  62 */       say(this.originalTestInfo.getLevel(), ChatFormatting.GREEN, "Flaky test " + this.originalTestInfo + " succeeded, attempt: " + this.attempts + " successes: " + this.successes);
/*  63 */       rerunTest();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void testFailed(GameTestInfo $$0) {
/*  69 */     if (!$$0.isFlaky()) {
/*  70 */       reportFailure($$0, $$0.getError());
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     TestFunction $$1 = this.originalTestInfo.getTestFunction();
/*  75 */     String $$2 = "Flaky test " + this.originalTestInfo + " failed, attempt: " + this.attempts + "/" + $$1.getMaxAttempts();
/*  76 */     if ($$1.getRequiredSuccesses() > 1) {
/*  77 */       $$2 = $$2 + ", successes: " + $$2 + " (" + this.successes + " required)";
/*     */     }
/*  79 */     say(this.originalTestInfo.getLevel(), ChatFormatting.YELLOW, $$2);
/*  80 */     if ($$0.maxAttempts() - this.attempts + this.successes >= $$0.requiredSuccesses()) {
/*  81 */       rerunTest();
/*     */     } else {
/*  83 */       reportFailure($$0, new ExhaustedAttemptsException(this.attempts, this.successes, $$0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void reportPassed(GameTestInfo $$0, String $$1) {
/*  88 */     spawnBeacon($$0, Blocks.LIME_STAINED_GLASS);
/*  89 */     visualizePassedTest($$0, $$1);
/*     */   }
/*     */   
/*     */   private static void visualizePassedTest(GameTestInfo $$0, String $$1) {
/*  93 */     say($$0.getLevel(), ChatFormatting.GREEN, $$1);
/*     */     
/*  95 */     GlobalTestReporter.onTestSuccess($$0);
/*     */   }
/*     */   
/*     */   protected static void reportFailure(GameTestInfo $$0, Throwable $$1) {
/*  99 */     spawnBeacon($$0, $$0.isRequired() ? Blocks.RED_STAINED_GLASS : Blocks.ORANGE_STAINED_GLASS);
/* 100 */     spawnLectern($$0, Util.describeError($$1));
/* 101 */     visualizeFailedTest($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static void visualizeFailedTest(GameTestInfo $$0, Throwable $$1) {
/* 105 */     String $$2 = $$1.getMessage() + $$1.getMessage();
/* 106 */     String $$3 = ($$0.isRequired() ? "" : "(optional) ") + ($$0.isRequired() ? "" : "(optional) ") + " failed! " + $$0.getTestName();
/*     */     
/* 108 */     say($$0.getLevel(), $$0.isRequired() ? ChatFormatting.RED : ChatFormatting.YELLOW, $$3);
/*     */     
/* 110 */     Throwable $$4 = (Throwable)MoreObjects.firstNonNull(ExceptionUtils.getRootCause($$1), $$1);
/* 111 */     if ($$4 instanceof GameTestAssertPosException) { GameTestAssertPosException $$5 = (GameTestAssertPosException)$$4;
/* 112 */       showRedBox($$0.getLevel(), $$5.getAbsolutePos(), $$5.getMessageToShowAtBlock()); }
/*     */ 
/*     */     
/* 115 */     GlobalTestReporter.onTestFailed($$0);
/*     */   }
/*     */   
/*     */   private void rerunTest() {
/* 119 */     this.originalTestInfo.clearStructure();
/* 120 */     GameTestInfo $$0 = new GameTestInfo(this.originalTestInfo.getTestFunction(), this.originalTestInfo.getRotation(), this.originalTestInfo.getLevel());
/* 121 */     $$0.setRerunUntilFailed(this.originalTestInfo.rerunUntilFailed());
/*     */     
/* 123 */     this.testTicker.add($$0);
/* 124 */     $$0.addListener(this);
/* 125 */     $$0.prepareTestStructure(this.structurePos);
/*     */   }
/*     */   
/*     */   protected static void spawnBeacon(GameTestInfo $$0, Block $$1) {
/* 129 */     ServerLevel $$2 = $$0.getLevel();
/* 130 */     BlockPos $$3 = $$0.getStructureBlockPos();
/* 131 */     BlockPos $$4 = new BlockPos(-1, -1, -1);
/* 132 */     BlockPos $$5 = StructureTemplate.transform($$3.offset((Vec3i)$$4), Mirror.NONE, $$0.getRotation(), $$3);
/* 133 */     $$2.setBlockAndUpdate($$5, Blocks.BEACON.defaultBlockState().rotate($$0.getRotation()));
/*     */     
/* 135 */     BlockPos $$6 = $$5.offset(0, 1, 0);
/* 136 */     $$2.setBlockAndUpdate($$6, $$1.defaultBlockState());
/*     */     
/* 138 */     for (int $$7 = -1; $$7 <= 1; $$7++) {
/* 139 */       for (int $$8 = -1; $$8 <= 1; $$8++) {
/* 140 */         BlockPos $$9 = $$5.offset($$7, -1, $$8);
/* 141 */         $$2.setBlockAndUpdate($$9, Blocks.IRON_BLOCK.defaultBlockState());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void spawnLectern(GameTestInfo $$0, String $$1) {
/* 147 */     ServerLevel $$2 = $$0.getLevel();
/* 148 */     BlockPos $$3 = $$0.getStructureBlockPos();
/* 149 */     BlockPos $$4 = new BlockPos(-1, 1, -1);
/* 150 */     BlockPos $$5 = StructureTemplate.transform($$3.offset((Vec3i)$$4), Mirror.NONE, $$0.getRotation(), $$3);
/*     */     
/* 152 */     $$2.setBlockAndUpdate($$5, Blocks.LECTERN.defaultBlockState().rotate($$0.getRotation()));
/*     */     
/* 154 */     BlockState $$6 = $$2.getBlockState($$5);
/*     */     
/* 156 */     ItemStack $$7 = createBook($$0.getTestName(), $$0.isRequired(), $$1);
/*     */     
/* 158 */     LecternBlock.tryPlaceBook(null, (Level)$$2, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   private static ItemStack createBook(String $$0, boolean $$1, String $$2) {
/* 162 */     ItemStack $$3 = new ItemStack((ItemLike)Items.WRITABLE_BOOK);
/* 163 */     ListTag $$4 = new ListTag();
/*     */     
/* 165 */     StringBuffer $$5 = new StringBuffer();
/* 166 */     Arrays.<String>stream($$0.split("\\.")).forEach($$1 -> $$0.append($$1).append('\n'));
/*     */ 
/*     */     
/* 169 */     if (!$$1) {
/* 170 */       $$5.append("(optional)\n");
/*     */     }
/*     */     
/* 173 */     $$5.append("-------------------\n");
/*     */     
/* 175 */     $$4.add(StringTag.valueOf("" + $$5 + $$5));
/* 176 */     $$3.addTagElement("pages", (Tag)$$4);
/* 177 */     return $$3;
/*     */   }
/*     */   
/*     */   protected static void say(ServerLevel $$0, ChatFormatting $$1, String $$2) {
/* 181 */     $$0.getPlayers($$0 -> true).forEach($$2 -> $$2.sendSystemMessage((Component)Component.literal($$0).withStyle($$1)));
/*     */   }
/*     */   
/*     */   private static void showRedBox(ServerLevel $$0, BlockPos $$1, String $$2) {
/* 185 */     DebugPackets.sendGameTestAddMarker($$0, $$1, $$2, -2130771968, 2147483647);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\ReportGameListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */