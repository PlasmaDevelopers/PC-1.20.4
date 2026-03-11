/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.blaze3d.Blaze3D;
/*     */ import com.mojang.blaze3d.platform.ClipboardManager;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import java.nio.file.Path;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.components.ChatComponent;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.commands.arguments.blocks.BlockStateParser;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.NativeModuleLister;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KeyboardHandler
/*     */ {
/*     */   public static final int DEBUG_CRASH_TIME = 10000;
/*     */   private final Minecraft minecraft;
/*  56 */   private final ClipboardManager clipboardManager = new ClipboardManager(); private long debugCrashKeyTime;
/*     */   private long debugCrashKeyReportedTime;
/*     */   private long debugCrashKeyReportedCount;
/*     */   private boolean handledDebugKey;
/*     */   
/*     */   public KeyboardHandler(Minecraft $$0) {
/*  62 */     this.debugCrashKeyTime = -1L;
/*  63 */     this.debugCrashKeyReportedTime = -1L;
/*  64 */     this.debugCrashKeyReportedCount = -1L;
/*     */     this.minecraft = $$0;
/*     */   }
/*     */   private boolean handleChunkDebugKeys(int $$0) {
/*  68 */     switch ($$0) {
/*     */       case 69:
/*  70 */         this.minecraft.sectionPath = !this.minecraft.sectionPath;
/*  71 */         debugFeedback("SectionPath: {0}", new Object[] { this.minecraft.sectionPath ? "shown" : "hidden" });
/*  72 */         return true;
/*     */       case 76:
/*  74 */         this.minecraft.smartCull = !this.minecraft.smartCull;
/*  75 */         debugFeedback("SmartCull: {0}", new Object[] { this.minecraft.smartCull ? "enabled" : "disabled" });
/*  76 */         return true;
/*     */       case 85:
/*  78 */         if (Screen.hasShiftDown()) {
/*  79 */           this.minecraft.levelRenderer.killFrustum();
/*  80 */           debugFeedback("Killed frustum", new Object[0]);
/*     */         } else {
/*  82 */           this.minecraft.levelRenderer.captureFrustum();
/*  83 */           debugFeedback("Captured frustum", new Object[0]);
/*     */         } 
/*  85 */         return true;
/*     */       case 86:
/*  87 */         this.minecraft.sectionVisibility = !this.minecraft.sectionVisibility;
/*  88 */         debugFeedback("SectionVisibility: {0}", new Object[] { this.minecraft.sectionVisibility ? "enabled" : "disabled" });
/*  89 */         return true;
/*     */       case 87:
/*  91 */         this.minecraft.wireframe = !this.minecraft.wireframe;
/*  92 */         debugFeedback("WireFrame: {0}", new Object[] { this.minecraft.wireframe ? "enabled" : "disabled" });
/*  93 */         return true;
/*     */     } 
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void debugComponent(ChatFormatting $$0, Component $$1) {
/* 100 */     this.minecraft.gui.getChat().addMessage((Component)Component.empty().append((Component)Component.translatable("debug.prefix").withStyle(new ChatFormatting[] { $$0, ChatFormatting.BOLD })).append(CommonComponents.SPACE).append($$1));
/*     */   }
/*     */   
/*     */   private void debugFeedbackComponent(Component $$0) {
/* 104 */     debugComponent(ChatFormatting.YELLOW, $$0);
/*     */   }
/*     */   
/*     */   private void debugFeedbackTranslated(String $$0, Object... $$1) {
/* 108 */     debugFeedbackComponent((Component)Component.translatableEscape($$0, $$1));
/*     */   }
/*     */   
/*     */   private void debugWarningTranslated(String $$0, Object... $$1) {
/* 112 */     debugComponent(ChatFormatting.RED, (Component)Component.translatableEscape($$0, $$1));
/*     */   }
/*     */   
/*     */   private void debugFeedback(String $$0, Object... $$1) {
/* 116 */     debugFeedbackComponent((Component)Component.literal(MessageFormat.format($$0, $$1))); } private boolean handleDebugKeys(int $$0) { boolean $$1; boolean $$2; ChatComponent $$3; Path $$4;
/*     */     Path $$5;
/*     */     MutableComponent mutableComponent;
/*     */     ClientPacketListener $$7;
/* 120 */     if (this.debugCrashKeyTime > 0L && this.debugCrashKeyTime < Util.getMillis() - 100L) {
/* 121 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     switch ($$0) {
/*     */       case 65:
/* 143 */         this.minecraft.levelRenderer.allChanged();
/* 144 */         debugFeedbackTranslated("debug.reload_chunks.message", new Object[0]);
/* 145 */         return true;
/*     */       case 66:
/* 147 */         $$1 = !this.minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes();
/* 148 */         this.minecraft.getEntityRenderDispatcher().setRenderHitBoxes($$1);
/* 149 */         debugFeedbackTranslated($$1 ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off", new Object[0]);
/* 150 */         return true;
/*     */       case 68:
/* 152 */         if (this.minecraft.gui != null) {
/* 153 */           this.minecraft.gui.getChat().clearMessages(false);
/*     */         }
/* 155 */         return true;
/*     */       case 71:
/* 157 */         $$2 = this.minecraft.debugRenderer.switchRenderChunkborder();
/* 158 */         debugFeedbackTranslated($$2 ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off", new Object[0]);
/* 159 */         return true;
/*     */       case 72:
/* 161 */         this.minecraft.options.advancedItemTooltips = !this.minecraft.options.advancedItemTooltips;
/* 162 */         debugFeedbackTranslated(this.minecraft.options.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off", new Object[0]);
/* 163 */         this.minecraft.options.save();
/* 164 */         return true;
/*     */       case 73:
/* 166 */         if (!this.minecraft.player.isReducedDebugInfo()) {
/* 167 */           copyRecreateCommand(this.minecraft.player.hasPermissions(2), !Screen.hasShiftDown());
/*     */         }
/* 169 */         return true;
/*     */       case 78:
/* 171 */         if (!this.minecraft.player.hasPermissions(2)) {
/* 172 */           debugFeedbackTranslated("debug.creative_spectator.error", new Object[0]);
/* 173 */         } else if (!this.minecraft.player.isSpectator()) {
/* 174 */           this.minecraft.player.connection.sendUnsignedCommand("gamemode spectator");
/*     */         } else {
/* 176 */           this.minecraft.player.connection.sendUnsignedCommand("gamemode " + ((GameType)MoreObjects.firstNonNull(this.minecraft.gameMode.getPreviousPlayerMode(), GameType.CREATIVE)).getName());
/*     */         } 
/* 178 */         return true;
/*     */       case 293:
/* 180 */         if (!this.minecraft.player.hasPermissions(2)) {
/* 181 */           debugFeedbackTranslated("debug.gamemodes.error", new Object[0]);
/*     */         } else {
/* 183 */           this.minecraft.setScreen((Screen)new GameModeSwitcherScreen());
/*     */         } 
/* 185 */         return true;
/*     */       case 80:
/* 187 */         this.minecraft.options.pauseOnLostFocus = !this.minecraft.options.pauseOnLostFocus;
/* 188 */         this.minecraft.options.save();
/* 189 */         debugFeedbackTranslated(this.minecraft.options.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off", new Object[0]);
/* 190 */         return true;
/*     */       case 81:
/* 192 */         debugFeedbackTranslated("debug.help.message", new Object[0]);
/* 193 */         $$3 = this.minecraft.gui.getChat();
/*     */ 
/*     */         
/* 196 */         $$3.addMessage((Component)Component.translatable("debug.reload_chunks.help"));
/* 197 */         $$3.addMessage((Component)Component.translatable("debug.show_hitboxes.help"));
/* 198 */         $$3.addMessage((Component)Component.translatable("debug.copy_location.help"));
/* 199 */         $$3.addMessage((Component)Component.translatable("debug.clear_chat.help"));
/* 200 */         $$3.addMessage((Component)Component.translatable("debug.chunk_boundaries.help"));
/* 201 */         $$3.addMessage((Component)Component.translatable("debug.advanced_tooltips.help"));
/* 202 */         $$3.addMessage((Component)Component.translatable("debug.inspect.help"));
/* 203 */         $$3.addMessage((Component)Component.translatable("debug.profiling.help"));
/* 204 */         $$3.addMessage((Component)Component.translatable("debug.creative_spectator.help"));
/* 205 */         $$3.addMessage((Component)Component.translatable("debug.pause_focus.help"));
/* 206 */         $$3.addMessage((Component)Component.translatable("debug.help.help"));
/* 207 */         $$3.addMessage((Component)Component.translatable("debug.dump_dynamic_textures.help"));
/* 208 */         $$3.addMessage((Component)Component.translatable("debug.reload_resourcepacks.help"));
/* 209 */         $$3.addMessage((Component)Component.translatable("debug.pause.help"));
/* 210 */         $$3.addMessage((Component)Component.translatable("debug.gamemodes.help"));
/* 211 */         return true;
/*     */       case 83:
/* 213 */         $$4 = this.minecraft.gameDirectory.toPath().toAbsolutePath();
/* 214 */         $$5 = TextureUtil.getDebugTexturePath($$4);
/* 215 */         this.minecraft.getTextureManager().dumpAllSheets($$5);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 220 */         mutableComponent = Component.literal($$4.relativize($$5).toString()).withStyle(ChatFormatting.UNDERLINE).withStyle($$1 -> $$1.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, $$0.toFile().toString())));
/*     */         
/* 222 */         debugFeedbackTranslated("debug.dump_dynamic_textures", new Object[] { mutableComponent });
/* 223 */         return true;
/*     */       case 84:
/* 225 */         debugFeedbackTranslated("debug.reload_resourcepacks.message", new Object[0]);
/* 226 */         this.minecraft.reloadResourcePacks();
/* 227 */         return true;
/*     */       case 76:
/* 229 */         if (this.minecraft.debugClientMetricsStart(this::debugFeedbackComponent)) {
/* 230 */           debugFeedbackTranslated("debug.profiling.start", new Object[] { Integer.valueOf(10) });
/*     */         }
/* 232 */         return true;
/*     */       case 67:
/* 234 */         if (this.minecraft.player.isReducedDebugInfo()) {
/* 235 */           return false;
/*     */         }
/* 237 */         $$7 = this.minecraft.player.connection;
/* 238 */         if ($$7 == null) {
/* 239 */           return false;
/*     */         }
/* 241 */         debugFeedbackTranslated("debug.copy_location.message", new Object[0]);
/* 242 */         setClipboard(String.format(Locale.ROOT, "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f", new Object[] { this.minecraft.player.level().dimension().location(), Double.valueOf(this.minecraft.player.getX()), Double.valueOf(this.minecraft.player.getY()), Double.valueOf(this.minecraft.player.getZ()), Float.valueOf(this.minecraft.player.getYRot()), Float.valueOf(this.minecraft.player.getXRot()) }));
/* 243 */         return true;
/*     */       case 49:
/* 245 */         this.minecraft.getDebugOverlay().toggleProfilerChart();
/* 246 */         return true;
/*     */       case 50:
/* 248 */         this.minecraft.getDebugOverlay().toggleFpsCharts();
/* 249 */         return true;
/*     */       case 51:
/* 251 */         this.minecraft.getDebugOverlay().toggleNetworkCharts();
/* 252 */         return true;
/*     */     } 
/* 254 */     return false; } private void copyRecreateCommand(boolean $$0, boolean $$1) {
/*     */     BlockPos $$3;
/*     */     Entity $$7;
/*     */     BlockState $$4;
/*     */     ResourceLocation $$8;
/* 259 */     HitResult $$2 = this.minecraft.hitResult;
/* 260 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 264 */     switch ($$2.getType()) {
/*     */       case BLOCK:
/* 266 */         $$3 = ((BlockHitResult)$$2).getBlockPos();
/* 267 */         $$4 = this.minecraft.player.level().getBlockState($$3);
/*     */         
/* 269 */         if ($$0) {
/* 270 */           if ($$1) {
/* 271 */             this.minecraft.player.connection.getDebugQueryHandler().queryBlockEntityTag($$3, $$2 -> {
/*     */                   copyCreateBlockCommand($$0, $$1, $$2); debugFeedbackTranslated("debug.inspect.server.block", new Object[0]);
/*     */                 });
/*     */             break;
/*     */           } 
/* 276 */           BlockEntity $$5 = this.minecraft.player.level().getBlockEntity($$3);
/* 277 */           CompoundTag $$6 = ($$5 != null) ? $$5.saveWithoutMetadata() : null;
/* 278 */           copyCreateBlockCommand($$4, $$3, $$6);
/* 279 */           debugFeedbackTranslated("debug.inspect.client.block", new Object[0]);
/*     */           break;
/*     */         } 
/* 282 */         copyCreateBlockCommand($$4, $$3, null);
/* 283 */         debugFeedbackTranslated("debug.inspect.client.block", new Object[0]);
/*     */         break;
/*     */ 
/*     */       
/*     */       case ENTITY:
/* 288 */         $$7 = ((EntityHitResult)$$2).getEntity();
/* 289 */         $$8 = BuiltInRegistries.ENTITY_TYPE.getKey($$7.getType());
/* 290 */         if ($$0) {
/* 291 */           if ($$1) {
/* 292 */             this.minecraft.player.connection.getDebugQueryHandler().queryEntityTag($$7.getId(), $$2 -> {
/*     */                   copyCreateEntityCommand($$0, $$1.position(), $$2); debugFeedbackTranslated("debug.inspect.server.entity", new Object[0]);
/*     */                 });
/*     */             break;
/*     */           } 
/* 297 */           CompoundTag $$9 = $$7.saveWithoutId(new CompoundTag());
/* 298 */           copyCreateEntityCommand($$8, $$7.position(), $$9);
/* 299 */           debugFeedbackTranslated("debug.inspect.client.entity", new Object[0]);
/*     */           break;
/*     */         } 
/* 302 */         copyCreateEntityCommand($$8, $$7.position(), null);
/* 303 */         debugFeedbackTranslated("debug.inspect.client.entity", new Object[0]);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyCreateBlockCommand(BlockState $$0, BlockPos $$1, @Nullable CompoundTag $$2) {
/* 313 */     StringBuilder $$3 = new StringBuilder(BlockStateParser.serialize($$0));
/* 314 */     if ($$2 != null) {
/* 315 */       $$3.append($$2);
/*     */     }
/* 317 */     String $$4 = String.format(Locale.ROOT, "/setblock %d %d %d %s", new Object[] { Integer.valueOf($$1.getX()), Integer.valueOf($$1.getY()), Integer.valueOf($$1.getZ()), $$3 });
/* 318 */     setClipboard($$4);
/*     */   }
/*     */   
/*     */   private void copyCreateEntityCommand(ResourceLocation $$0, Vec3 $$1, @Nullable CompoundTag $$2) {
/*     */     String $$5;
/* 323 */     if ($$2 != null) {
/* 324 */       $$2.remove("UUID");
/* 325 */       $$2.remove("Pos");
/* 326 */       $$2.remove("Dimension");
/* 327 */       String $$3 = NbtUtils.toPrettyComponent((Tag)$$2).getString();
/* 328 */       String $$4 = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", new Object[] { $$0, Double.valueOf($$1.x), Double.valueOf($$1.y), Double.valueOf($$1.z), $$3 });
/*     */     } else {
/* 330 */       $$5 = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", new Object[] { $$0, Double.valueOf($$1.x), Double.valueOf($$1.y), Double.valueOf($$1.z) });
/*     */     } 
/* 332 */     setClipboard($$5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPress(long $$0, int $$1, int $$2, int $$3, int $$4) {
/*     */     // Byte code:
/*     */     //   0: lload_1
/*     */     //   1: aload_0
/*     */     //   2: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   5: invokevirtual getWindow : ()Lcom/mojang/blaze3d/platform/Window;
/*     */     //   8: invokevirtual getWindow : ()J
/*     */     //   11: lcmp
/*     */     //   12: ifeq -> 16
/*     */     //   15: return
/*     */     //   16: invokestatic getInstance : ()Lnet/minecraft/client/Minecraft;
/*     */     //   19: invokevirtual getWindow : ()Lcom/mojang/blaze3d/platform/Window;
/*     */     //   22: invokevirtual getWindow : ()J
/*     */     //   25: sipush #292
/*     */     //   28: invokestatic isKeyDown : (JI)Z
/*     */     //   31: istore #7
/*     */     //   33: aload_0
/*     */     //   34: getfield debugCrashKeyTime : J
/*     */     //   37: lconst_0
/*     */     //   38: lcmp
/*     */     //   39: ifle -> 74
/*     */     //   42: invokestatic getInstance : ()Lnet/minecraft/client/Minecraft;
/*     */     //   45: invokevirtual getWindow : ()Lcom/mojang/blaze3d/platform/Window;
/*     */     //   48: invokevirtual getWindow : ()J
/*     */     //   51: bipush #67
/*     */     //   53: invokestatic isKeyDown : (JI)Z
/*     */     //   56: ifeq -> 64
/*     */     //   59: iload #7
/*     */     //   61: ifne -> 120
/*     */     //   64: aload_0
/*     */     //   65: ldc2_w -1
/*     */     //   68: putfield debugCrashKeyTime : J
/*     */     //   71: goto -> 120
/*     */     //   74: invokestatic getInstance : ()Lnet/minecraft/client/Minecraft;
/*     */     //   77: invokevirtual getWindow : ()Lcom/mojang/blaze3d/platform/Window;
/*     */     //   80: invokevirtual getWindow : ()J
/*     */     //   83: bipush #67
/*     */     //   85: invokestatic isKeyDown : (JI)Z
/*     */     //   88: ifeq -> 120
/*     */     //   91: iload #7
/*     */     //   93: ifeq -> 120
/*     */     //   96: aload_0
/*     */     //   97: iconst_1
/*     */     //   98: putfield handledDebugKey : Z
/*     */     //   101: aload_0
/*     */     //   102: invokestatic getMillis : ()J
/*     */     //   105: putfield debugCrashKeyTime : J
/*     */     //   108: aload_0
/*     */     //   109: invokestatic getMillis : ()J
/*     */     //   112: putfield debugCrashKeyReportedTime : J
/*     */     //   115: aload_0
/*     */     //   116: lconst_0
/*     */     //   117: putfield debugCrashKeyReportedCount : J
/*     */     //   120: aload_0
/*     */     //   121: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   124: getfield screen : Lnet/minecraft/client/gui/screens/Screen;
/*     */     //   127: astore #8
/*     */     //   129: aload #8
/*     */     //   131: ifnull -> 203
/*     */     //   134: iload_3
/*     */     //   135: tableswitch default -> 203, 258 -> 193, 259 -> 203, 260 -> 203, 261 -> 203, 262 -> 180, 263 -> 180, 264 -> 180, 265 -> 180
/*     */     //   180: aload_0
/*     */     //   181: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   184: getstatic net/minecraft/client/InputType.KEYBOARD_ARROW : Lnet/minecraft/client/InputType;
/*     */     //   187: invokevirtual setLastInputType : (Lnet/minecraft/client/InputType;)V
/*     */     //   190: goto -> 203
/*     */     //   193: aload_0
/*     */     //   194: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   197: getstatic net/minecraft/client/InputType.KEYBOARD_TAB : Lnet/minecraft/client/InputType;
/*     */     //   200: invokevirtual setLastInputType : (Lnet/minecraft/client/InputType;)V
/*     */     //   203: iload #5
/*     */     //   205: iconst_1
/*     */     //   206: if_icmpne -> 346
/*     */     //   209: aload_0
/*     */     //   210: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   213: getfield screen : Lnet/minecraft/client/gui/screens/Screen;
/*     */     //   216: instanceof net/minecraft/client/gui/screens/controls/KeyBindsScreen
/*     */     //   219: ifeq -> 241
/*     */     //   222: aload #8
/*     */     //   224: checkcast net/minecraft/client/gui/screens/controls/KeyBindsScreen
/*     */     //   227: getfield lastKeySelection : J
/*     */     //   230: invokestatic getMillis : ()J
/*     */     //   233: ldc2_w 20
/*     */     //   236: lsub
/*     */     //   237: lcmp
/*     */     //   238: ifgt -> 346
/*     */     //   241: aload_0
/*     */     //   242: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   245: getfield options : Lnet/minecraft/client/Options;
/*     */     //   248: getfield keyFullscreen : Lnet/minecraft/client/KeyMapping;
/*     */     //   251: iload_3
/*     */     //   252: iload #4
/*     */     //   254: invokevirtual matches : (II)Z
/*     */     //   257: ifeq -> 297
/*     */     //   260: aload_0
/*     */     //   261: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   264: invokevirtual getWindow : ()Lcom/mojang/blaze3d/platform/Window;
/*     */     //   267: invokevirtual toggleFullScreen : ()V
/*     */     //   270: aload_0
/*     */     //   271: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   274: getfield options : Lnet/minecraft/client/Options;
/*     */     //   277: invokevirtual fullscreen : ()Lnet/minecraft/client/OptionInstance;
/*     */     //   280: aload_0
/*     */     //   281: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   284: invokevirtual getWindow : ()Lcom/mojang/blaze3d/platform/Window;
/*     */     //   287: invokevirtual isFullscreen : ()Z
/*     */     //   290: invokestatic valueOf : (Z)Ljava/lang/Boolean;
/*     */     //   293: invokevirtual set : (Ljava/lang/Object;)V
/*     */     //   296: return
/*     */     //   297: aload_0
/*     */     //   298: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   301: getfield options : Lnet/minecraft/client/Options;
/*     */     //   304: getfield keyScreenshot : Lnet/minecraft/client/KeyMapping;
/*     */     //   307: iload_3
/*     */     //   308: iload #4
/*     */     //   310: invokevirtual matches : (II)Z
/*     */     //   313: ifeq -> 346
/*     */     //   316: invokestatic hasControlDown : ()Z
/*     */     //   319: ifeq -> 322
/*     */     //   322: aload_0
/*     */     //   323: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   326: getfield gameDirectory : Ljava/io/File;
/*     */     //   329: aload_0
/*     */     //   330: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   333: invokevirtual getMainRenderTarget : ()Lcom/mojang/blaze3d/pipeline/RenderTarget;
/*     */     //   336: aload_0
/*     */     //   337: <illegal opcode> accept : (Lnet/minecraft/client/KeyboardHandler;)Ljava/util/function/Consumer;
/*     */     //   342: invokestatic grab : (Ljava/io/File;Lcom/mojang/blaze3d/pipeline/RenderTarget;Ljava/util/function/Consumer;)V
/*     */     //   345: return
/*     */     //   346: aload_0
/*     */     //   347: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   350: invokevirtual getNarrator : ()Lnet/minecraft/client/GameNarrator;
/*     */     //   353: invokevirtual isActive : ()Z
/*     */     //   356: ifeq -> 549
/*     */     //   359: aload_0
/*     */     //   360: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   363: getfield options : Lnet/minecraft/client/Options;
/*     */     //   366: invokevirtual narratorHotkey : ()Lnet/minecraft/client/OptionInstance;
/*     */     //   369: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   372: checkcast java/lang/Boolean
/*     */     //   375: invokevirtual booleanValue : ()Z
/*     */     //   378: ifeq -> 549
/*     */     //   381: aload #8
/*     */     //   383: ifnull -> 416
/*     */     //   386: aload #8
/*     */     //   388: invokevirtual getFocused : ()Lnet/minecraft/client/gui/components/events/GuiEventListener;
/*     */     //   391: astore #11
/*     */     //   393: aload #11
/*     */     //   395: instanceof net/minecraft/client/gui/components/EditBox
/*     */     //   398: ifeq -> 416
/*     */     //   401: aload #11
/*     */     //   403: checkcast net/minecraft/client/gui/components/EditBox
/*     */     //   406: astore #10
/*     */     //   408: aload #10
/*     */     //   410: invokevirtual canConsumeInput : ()Z
/*     */     //   413: ifne -> 420
/*     */     //   416: iconst_1
/*     */     //   417: goto -> 421
/*     */     //   420: iconst_0
/*     */     //   421: istore #9
/*     */     //   423: iload #5
/*     */     //   425: ifeq -> 549
/*     */     //   428: iload_3
/*     */     //   429: bipush #66
/*     */     //   431: if_icmpne -> 549
/*     */     //   434: invokestatic hasControlDown : ()Z
/*     */     //   437: ifeq -> 549
/*     */     //   440: iload #9
/*     */     //   442: ifeq -> 549
/*     */     //   445: aload_0
/*     */     //   446: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   449: getfield options : Lnet/minecraft/client/Options;
/*     */     //   452: invokevirtual narrator : ()Lnet/minecraft/client/OptionInstance;
/*     */     //   455: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   458: getstatic net/minecraft/client/NarratorStatus.OFF : Lnet/minecraft/client/NarratorStatus;
/*     */     //   461: if_acmpne -> 468
/*     */     //   464: iconst_1
/*     */     //   465: goto -> 469
/*     */     //   468: iconst_0
/*     */     //   469: istore #10
/*     */     //   471: aload_0
/*     */     //   472: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   475: getfield options : Lnet/minecraft/client/Options;
/*     */     //   478: invokevirtual narrator : ()Lnet/minecraft/client/OptionInstance;
/*     */     //   481: aload_0
/*     */     //   482: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   485: getfield options : Lnet/minecraft/client/Options;
/*     */     //   488: invokevirtual narrator : ()Lnet/minecraft/client/OptionInstance;
/*     */     //   491: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   494: checkcast net/minecraft/client/NarratorStatus
/*     */     //   497: invokevirtual getId : ()I
/*     */     //   500: iconst_1
/*     */     //   501: iadd
/*     */     //   502: invokestatic byId : (I)Lnet/minecraft/client/NarratorStatus;
/*     */     //   505: invokevirtual set : (Ljava/lang/Object;)V
/*     */     //   508: aload_0
/*     */     //   509: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   512: getfield options : Lnet/minecraft/client/Options;
/*     */     //   515: invokevirtual save : ()V
/*     */     //   518: aload #8
/*     */     //   520: instanceof net/minecraft/client/gui/screens/SimpleOptionsSubScreen
/*     */     //   523: ifeq -> 534
/*     */     //   526: aload #8
/*     */     //   528: checkcast net/minecraft/client/gui/screens/SimpleOptionsSubScreen
/*     */     //   531: invokevirtual updateNarratorButton : ()V
/*     */     //   534: iload #10
/*     */     //   536: ifeq -> 549
/*     */     //   539: aload #8
/*     */     //   541: ifnull -> 549
/*     */     //   544: aload #8
/*     */     //   546: invokevirtual narrationEnabled : ()V
/*     */     //   549: aload #8
/*     */     //   551: ifnull -> 601
/*     */     //   554: iconst_1
/*     */     //   555: newarray boolean
/*     */     //   557: dup
/*     */     //   558: iconst_0
/*     */     //   559: iconst_0
/*     */     //   560: bastore
/*     */     //   561: astore #9
/*     */     //   563: iload #5
/*     */     //   565: aload #8
/*     */     //   567: aload #9
/*     */     //   569: iload_3
/*     */     //   570: iload #4
/*     */     //   572: iload #6
/*     */     //   574: <illegal opcode> run : (ILnet/minecraft/client/gui/screens/Screen;[ZIII)Ljava/lang/Runnable;
/*     */     //   579: ldc_w 'keyPressed event handler'
/*     */     //   582: aload #8
/*     */     //   584: invokevirtual getClass : ()Ljava/lang/Class;
/*     */     //   587: invokevirtual getCanonicalName : ()Ljava/lang/String;
/*     */     //   590: invokestatic wrapScreenError : (Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V
/*     */     //   593: aload #9
/*     */     //   595: iconst_0
/*     */     //   596: baload
/*     */     //   597: ifeq -> 601
/*     */     //   600: return
/*     */     //   601: iload_3
/*     */     //   602: iload #4
/*     */     //   604: invokestatic getKey : (II)Lcom/mojang/blaze3d/platform/InputConstants$Key;
/*     */     //   607: astore #9
/*     */     //   609: aload_0
/*     */     //   610: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   613: getfield screen : Lnet/minecraft/client/gui/screens/Screen;
/*     */     //   616: ifnonnull -> 623
/*     */     //   619: iconst_1
/*     */     //   620: goto -> 624
/*     */     //   623: iconst_0
/*     */     //   624: istore #10
/*     */     //   626: iload #10
/*     */     //   628: ifne -> 663
/*     */     //   631: aload_0
/*     */     //   632: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   635: getfield screen : Lnet/minecraft/client/gui/screens/Screen;
/*     */     //   638: astore #13
/*     */     //   640: aload #13
/*     */     //   642: instanceof net/minecraft/client/gui/screens/PauseScreen
/*     */     //   645: ifeq -> 667
/*     */     //   648: aload #13
/*     */     //   650: checkcast net/minecraft/client/gui/screens/PauseScreen
/*     */     //   653: astore #12
/*     */     //   655: aload #12
/*     */     //   657: invokevirtual showsPauseMenu : ()Z
/*     */     //   660: ifne -> 667
/*     */     //   663: iconst_1
/*     */     //   664: goto -> 668
/*     */     //   667: iconst_0
/*     */     //   668: istore #11
/*     */     //   670: iload #5
/*     */     //   672: ifne -> 719
/*     */     //   675: aload #9
/*     */     //   677: iconst_0
/*     */     //   678: invokestatic set : (Lcom/mojang/blaze3d/platform/InputConstants$Key;Z)V
/*     */     //   681: iload #11
/*     */     //   683: ifeq -> 718
/*     */     //   686: iload_3
/*     */     //   687: sipush #292
/*     */     //   690: if_icmpne -> 718
/*     */     //   693: aload_0
/*     */     //   694: getfield handledDebugKey : Z
/*     */     //   697: ifeq -> 708
/*     */     //   700: aload_0
/*     */     //   701: iconst_0
/*     */     //   702: putfield handledDebugKey : Z
/*     */     //   705: goto -> 718
/*     */     //   708: aload_0
/*     */     //   709: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   712: invokevirtual getDebugOverlay : ()Lnet/minecraft/client/gui/components/DebugScreenOverlay;
/*     */     //   715: invokevirtual toggleOverlay : ()V
/*     */     //   718: return
/*     */     //   719: iconst_0
/*     */     //   720: istore #12
/*     */     //   722: iload #11
/*     */     //   724: ifeq -> 887
/*     */     //   727: iload_3
/*     */     //   728: sipush #293
/*     */     //   731: if_icmpne -> 754
/*     */     //   734: aload_0
/*     */     //   735: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   738: getfield gameRenderer : Lnet/minecraft/client/renderer/GameRenderer;
/*     */     //   741: ifnull -> 754
/*     */     //   744: aload_0
/*     */     //   745: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   748: getfield gameRenderer : Lnet/minecraft/client/renderer/GameRenderer;
/*     */     //   751: invokevirtual togglePostEffect : ()V
/*     */     //   754: iload_3
/*     */     //   755: sipush #256
/*     */     //   758: if_icmpne -> 777
/*     */     //   761: aload_0
/*     */     //   762: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   765: iload #7
/*     */     //   767: invokevirtual pauseGame : (Z)V
/*     */     //   770: iload #12
/*     */     //   772: iload #7
/*     */     //   774: ior
/*     */     //   775: istore #12
/*     */     //   777: iload #12
/*     */     //   779: iload #7
/*     */     //   781: ifeq -> 796
/*     */     //   784: aload_0
/*     */     //   785: iload_3
/*     */     //   786: invokevirtual handleDebugKeys : (I)Z
/*     */     //   789: ifeq -> 796
/*     */     //   792: iconst_1
/*     */     //   793: goto -> 797
/*     */     //   796: iconst_0
/*     */     //   797: ior
/*     */     //   798: istore #12
/*     */     //   800: aload_0
/*     */     //   801: dup
/*     */     //   802: getfield handledDebugKey : Z
/*     */     //   805: iload #12
/*     */     //   807: ior
/*     */     //   808: putfield handledDebugKey : Z
/*     */     //   811: iload_3
/*     */     //   812: sipush #290
/*     */     //   815: if_icmpne -> 846
/*     */     //   818: aload_0
/*     */     //   819: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   822: getfield options : Lnet/minecraft/client/Options;
/*     */     //   825: aload_0
/*     */     //   826: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   829: getfield options : Lnet/minecraft/client/Options;
/*     */     //   832: getfield hideGui : Z
/*     */     //   835: ifne -> 842
/*     */     //   838: iconst_1
/*     */     //   839: goto -> 843
/*     */     //   842: iconst_0
/*     */     //   843: putfield hideGui : Z
/*     */     //   846: aload_0
/*     */     //   847: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   850: invokevirtual getDebugOverlay : ()Lnet/minecraft/client/gui/components/DebugScreenOverlay;
/*     */     //   853: invokevirtual showProfilerChart : ()Z
/*     */     //   856: ifeq -> 887
/*     */     //   859: iload #7
/*     */     //   861: ifne -> 887
/*     */     //   864: iload_3
/*     */     //   865: bipush #48
/*     */     //   867: if_icmplt -> 887
/*     */     //   870: iload_3
/*     */     //   871: bipush #57
/*     */     //   873: if_icmpgt -> 887
/*     */     //   876: aload_0
/*     */     //   877: getfield minecraft : Lnet/minecraft/client/Minecraft;
/*     */     //   880: iload_3
/*     */     //   881: bipush #48
/*     */     //   883: isub
/*     */     //   884: invokevirtual debugFpsMeterKeyPress : (I)V
/*     */     //   887: iload #10
/*     */     //   889: ifeq -> 917
/*     */     //   892: iload #12
/*     */     //   894: ifeq -> 906
/*     */     //   897: aload #9
/*     */     //   899: iconst_0
/*     */     //   900: invokestatic set : (Lcom/mojang/blaze3d/platform/InputConstants$Key;Z)V
/*     */     //   903: goto -> 917
/*     */     //   906: aload #9
/*     */     //   908: iconst_1
/*     */     //   909: invokestatic set : (Lcom/mojang/blaze3d/platform/InputConstants$Key;Z)V
/*     */     //   912: aload #9
/*     */     //   914: invokestatic click : (Lcom/mojang/blaze3d/platform/InputConstants$Key;)V
/*     */     //   917: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #336	-> 0
/*     */     //   #337	-> 15
/*     */     //   #342	-> 16
/*     */     //   #343	-> 33
/*     */     //   #344	-> 42
/*     */     //   #345	-> 64
/*     */     //   #347	-> 74
/*     */     //   #348	-> 96
/*     */     //   #349	-> 101
/*     */     //   #350	-> 108
/*     */     //   #351	-> 115
/*     */     //   #358	-> 120
/*     */     //   #360	-> 129
/*     */     //   #361	-> 134
/*     */     //   #362	-> 180
/*     */     //   #363	-> 193
/*     */     //   #367	-> 203
/*     */     //   #368	-> 209
/*     */     //   #369	-> 241
/*     */     //   #370	-> 260
/*     */     //   #371	-> 270
/*     */     //   #372	-> 296
/*     */     //   #373	-> 297
/*     */     //   #374	-> 316
/*     */     //   #377	-> 322
/*     */     //   #379	-> 345
/*     */     //   #385	-> 346
/*     */     //   #386	-> 381
/*     */     //   #387	-> 423
/*     */     //   #388	-> 445
/*     */     //   #389	-> 471
/*     */     //   #390	-> 508
/*     */     //   #391	-> 518
/*     */     //   #392	-> 526
/*     */     //   #394	-> 534
/*     */     //   #395	-> 544
/*     */     //   #401	-> 549
/*     */     //   #402	-> 554
/*     */     //   #403	-> 563
/*     */     //   #411	-> 584
/*     */     //   #403	-> 590
/*     */     //   #412	-> 593
/*     */     //   #413	-> 600
/*     */     //   #418	-> 601
/*     */     //   #420	-> 609
/*     */     //   #421	-> 626
/*     */     //   #423	-> 670
/*     */     //   #424	-> 675
/*     */     //   #425	-> 681
/*     */     //   #426	-> 693
/*     */     //   #427	-> 700
/*     */     //   #429	-> 708
/*     */     //   #432	-> 718
/*     */     //   #435	-> 719
/*     */     //   #436	-> 722
/*     */     //   #437	-> 727
/*     */     //   #438	-> 734
/*     */     //   #439	-> 744
/*     */     //   #443	-> 754
/*     */     //   #444	-> 761
/*     */     //   #445	-> 770
/*     */     //   #447	-> 777
/*     */     //   #449	-> 800
/*     */     //   #451	-> 811
/*     */     //   #452	-> 818
/*     */     //   #455	-> 846
/*     */     //   #456	-> 864
/*     */     //   #457	-> 876
/*     */     //   #462	-> 887
/*     */     //   #464	-> 892
/*     */     //   #465	-> 897
/*     */     //   #467	-> 906
/*     */     //   #468	-> 912
/*     */     //   #471	-> 917
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	918	0	this	Lnet/minecraft/client/KeyboardHandler;
/*     */     //   0	918	1	$$0	J
/*     */     //   0	918	3	$$1	I
/*     */     //   0	918	4	$$2	I
/*     */     //   0	918	5	$$3	I
/*     */     //   0	918	6	$$4	I
/*     */     //   33	885	7	$$5	Z
/*     */     //   129	789	8	$$6	Lnet/minecraft/client/gui/screens/Screen;
/*     */     //   408	8	10	$$7	Lnet/minecraft/client/gui/components/EditBox;
/*     */     //   420	1	10	$$8	Lnet/minecraft/client/gui/components/EditBox;
/*     */     //   423	126	9	$$9	Z
/*     */     //   471	78	10	$$10	Z
/*     */     //   563	38	9	$$11	[Z
/*     */     //   609	309	9	$$12	Lcom/mojang/blaze3d/platform/InputConstants$Key;
/*     */     //   626	292	10	$$13	Z
/*     */     //   655	8	12	$$14	Lnet/minecraft/client/gui/screens/PauseScreen;
/*     */     //   670	248	11	$$15	Z
/*     */     //   722	196	12	$$16	Z
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void charTyped(long $$0, int $$1, int $$2) {
/* 474 */     if ($$0 != this.minecraft.getWindow().getWindow()) {
/*     */       return;
/*     */     }
/* 477 */     Screen screen = this.minecraft.screen;
/* 478 */     if (screen == null || this.minecraft.getOverlay() != null) {
/*     */       return;
/*     */     }
/* 481 */     if (Character.charCount($$1) == 1) {
/* 482 */       Screen.wrapScreenError(() -> $$0.charTyped((char)$$1, $$2), "charTyped event handler", screen.getClass().getCanonicalName());
/*     */     } else {
/* 484 */       for (char $$4 : Character.toChars($$1)) {
/* 485 */         Screen.wrapScreenError(() -> $$0.charTyped($$1, $$2), "charTyped event handler", screen.getClass().getCanonicalName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setup(long $$0) {
/* 491 */     InputConstants.setupKeyboardCallbacks($$0, ($$0, $$1, $$2, $$3, $$4) -> this.minecraft.execute(()), ($$0, $$1, $$2) -> this.minecraft.execute(()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClipboard() {
/* 497 */     return this.clipboardManager.getClipboard(this.minecraft.getWindow().getWindow(), ($$0, $$1) -> {
/*     */           if ($$0 != 65545) {
/*     */             this.minecraft.getWindow().defaultErrorCallback($$0, $$1);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void setClipboard(String $$0) {
/* 505 */     if (!$$0.isEmpty()) {
/* 506 */       this.clipboardManager.setClipboard(this.minecraft.getWindow().getWindow(), $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void tick() {
/* 511 */     if (this.debugCrashKeyTime > 0L) {
/* 512 */       long $$0 = Util.getMillis();
/* 513 */       long $$1 = 10000L - $$0 - this.debugCrashKeyTime;
/* 514 */       long $$2 = $$0 - this.debugCrashKeyReportedTime;
/* 515 */       if ($$1 < 0L) {
/* 516 */         if (Screen.hasControlDown()) {
/* 517 */           Blaze3D.youJustLostTheGame();
/*     */         }
/* 519 */         String $$3 = "Manually triggered debug crash";
/* 520 */         CrashReport $$4 = new CrashReport("Manually triggered debug crash", new Throwable("Manually triggered debug crash"));
/* 521 */         CrashReportCategory $$5 = $$4.addCategory("Manual crash details");
/* 522 */         NativeModuleLister.addCrashSection($$5);
/* 523 */         throw new ReportedException($$4);
/*     */       } 
/* 525 */       if ($$2 >= 1000L) {
/* 526 */         if (this.debugCrashKeyReportedCount == 0L) {
/* 527 */           debugFeedbackTranslated("debug.crash.message", new Object[0]);
/*     */         } else {
/* 529 */           debugWarningTranslated("debug.crash.warning", new Object[] { Integer.valueOf(Mth.ceil((float)$$1 / 1000.0F)) });
/*     */         } 
/* 531 */         this.debugCrashKeyReportedTime = $$0;
/* 532 */         this.debugCrashKeyReportedCount++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\KeyboardHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */