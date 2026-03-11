/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class BaseCommandBlock
/*     */   implements CommandSource
/*     */ {
/*  24 */   private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
/*  25 */   private static final Component DEFAULT_NAME = (Component)Component.literal("@");
/*     */   
/*  27 */   private long lastExecution = -1L;
/*     */   private boolean updateLastExecution = true;
/*     */   private int successCount;
/*     */   private boolean trackOutput = true;
/*     */   @Nullable
/*     */   private Component lastOutput;
/*  33 */   private String command = "";
/*  34 */   private Component name = DEFAULT_NAME;
/*     */   
/*     */   public int getSuccessCount() {
/*  37 */     return this.successCount;
/*     */   }
/*     */   
/*     */   public void setSuccessCount(int $$0) {
/*  41 */     this.successCount = $$0;
/*     */   }
/*     */   
/*     */   public Component getLastOutput() {
/*  45 */     return (this.lastOutput == null) ? CommonComponents.EMPTY : this.lastOutput;
/*     */   }
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/*  49 */     $$0.putString("Command", this.command);
/*  50 */     $$0.putInt("SuccessCount", this.successCount);
/*  51 */     $$0.putString("CustomName", Component.Serializer.toJson(this.name));
/*  52 */     $$0.putBoolean("TrackOutput", this.trackOutput);
/*  53 */     if (this.lastOutput != null && this.trackOutput) {
/*  54 */       $$0.putString("LastOutput", Component.Serializer.toJson(this.lastOutput));
/*     */     }
/*  56 */     $$0.putBoolean("UpdateLastExecution", this.updateLastExecution);
/*  57 */     if (this.updateLastExecution && this.lastExecution > 0L) {
/*  58 */       $$0.putLong("LastExecution", this.lastExecution);
/*     */     }
/*     */     
/*  61 */     return $$0;
/*     */   }
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  65 */     this.command = $$0.getString("Command");
/*  66 */     this.successCount = $$0.getInt("SuccessCount");
/*  67 */     if ($$0.contains("CustomName", 8)) {
/*  68 */       setName((Component)Component.Serializer.fromJson($$0.getString("CustomName")));
/*     */     }
/*  70 */     if ($$0.contains("TrackOutput", 1)) {
/*  71 */       this.trackOutput = $$0.getBoolean("TrackOutput");
/*     */     }
/*  73 */     if ($$0.contains("LastOutput", 8) && this.trackOutput) {
/*     */       try {
/*  75 */         this.lastOutput = (Component)Component.Serializer.fromJson($$0.getString("LastOutput"));
/*  76 */       } catch (Throwable $$1) {
/*  77 */         this.lastOutput = (Component)Component.literal($$1.getMessage());
/*     */       } 
/*     */     } else {
/*  80 */       this.lastOutput = null;
/*     */     } 
/*  82 */     if ($$0.contains("UpdateLastExecution")) {
/*  83 */       this.updateLastExecution = $$0.getBoolean("UpdateLastExecution");
/*     */     }
/*  85 */     if (this.updateLastExecution && $$0.contains("LastExecution")) {
/*  86 */       this.lastExecution = $$0.getLong("LastExecution");
/*     */     } else {
/*  88 */       this.lastExecution = -1L;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCommand(String $$0) {
/*  93 */     this.command = $$0;
/*  94 */     this.successCount = 0;
/*     */   }
/*     */   
/*     */   public String getCommand() {
/*  98 */     return this.command;
/*     */   }
/*     */   
/*     */   public boolean performCommand(Level $$0) {
/* 102 */     if ($$0.isClientSide || $$0.getGameTime() == this.lastExecution) {
/* 103 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 107 */     if ("Searge".equalsIgnoreCase(this.command)) {
/* 108 */       this.lastOutput = (Component)Component.literal("#itzlipofutzli");
/* 109 */       this.successCount = 1;
/* 110 */       return true;
/*     */     } 
/*     */     
/* 113 */     this.successCount = 0;
/*     */     
/* 115 */     MinecraftServer $$1 = getLevel().getServer();
/* 116 */     if ($$1.isCommandBlockEnabled() && !StringUtil.isNullOrEmpty(this.command)) {
/*     */       try {
/* 118 */         this.lastOutput = null;
/* 119 */         CommandSourceStack $$2 = createCommandSourceStack().withCallback(($$0, $$1) -> {
/*     */               if ($$0) {
/*     */                 this.successCount++;
/*     */               }
/*     */             });
/* 124 */         $$1.getCommands().performPrefixedCommand($$2, this.command);
/* 125 */       } catch (Throwable $$3) {
/* 126 */         CrashReport $$4 = CrashReport.forThrowable($$3, "Executing command block");
/* 127 */         CrashReportCategory $$5 = $$4.addCategory("Command to be executed");
/*     */         
/* 129 */         $$5.setDetail("Command", this::getCommand);
/*     */         
/* 131 */         $$5.setDetail("Name", () -> getName().getString());
/*     */         
/* 133 */         throw new ReportedException($$4);
/*     */       } 
/*     */     }
/*     */     
/* 137 */     if (this.updateLastExecution) {
/* 138 */       this.lastExecution = $$0.getGameTime();
/*     */     } else {
/* 140 */       this.lastExecution = -1L;
/*     */     } 
/*     */     
/* 143 */     return true;
/*     */   }
/*     */   
/*     */   public Component getName() {
/* 147 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(@Nullable Component $$0) {
/* 151 */     if ($$0 != null) {
/* 152 */       this.name = $$0;
/*     */     } else {
/* 154 */       this.name = DEFAULT_NAME;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendSystemMessage(Component $$0) {
/* 160 */     if (this.trackOutput) {
/* 161 */       this.lastOutput = (Component)Component.literal("[" + TIME_FORMAT.format(new Date()) + "] ").append($$0);
/* 162 */       onUpdated();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastOutput(@Nullable Component $$0) {
/* 171 */     this.lastOutput = $$0;
/*     */   }
/*     */   
/*     */   public void setTrackOutput(boolean $$0) {
/* 175 */     this.trackOutput = $$0;
/*     */   }
/*     */   
/*     */   public boolean isTrackOutput() {
/* 179 */     return this.trackOutput;
/*     */   }
/*     */   
/*     */   public InteractionResult usedBy(Player $$0) {
/* 183 */     if (!$$0.canUseGameMasterBlocks()) {
/* 184 */       return InteractionResult.PASS;
/*     */     }
/* 186 */     if (($$0.getCommandSenderWorld()).isClientSide) {
/* 187 */       $$0.openMinecartCommandBlock(this);
/*     */     }
/* 189 */     return InteractionResult.sidedSuccess(($$0.level()).isClientSide);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsSuccess() {
/* 198 */     return (getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK) && this.trackOutput);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsFailure() {
/* 203 */     return this.trackOutput;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldInformAdmins() {
/* 208 */     return getLevel().getGameRules().getBoolean(GameRules.RULE_COMMANDBLOCKOUTPUT);
/*     */   }
/*     */   
/*     */   public abstract ServerLevel getLevel();
/*     */   
/*     */   public abstract void onUpdated();
/*     */   
/*     */   public abstract Vec3 getPosition();
/*     */   
/*     */   public abstract CommandSourceStack createCommandSourceStack();
/*     */   
/*     */   public abstract boolean isValid();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\BaseCommandBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */