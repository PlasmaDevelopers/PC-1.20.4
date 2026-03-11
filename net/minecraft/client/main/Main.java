/*     */ package net.minecraft.client.main;
/*     */ import com.google.common.base.Stopwatch;
/*     */ import com.google.common.base.Ticker;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import com.mojang.blaze3d.platform.DisplayData;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferUploader;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.util.UndashedUuid;
/*     */ import java.io.File;
/*     */ import java.net.Authenticator;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.Proxy;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.NonOptionArgumentSpec;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import joptsimple.OptionSpecBuilder;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.User;
/*     */ import net.minecraft.client.server.IntegratedServer;
/*     */ import net.minecraft.client.telemetry.TelemetryProperty;
/*     */ import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.obfuscate.DontObfuscate;
/*     */ import net.minecraft.server.Bootstrap;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.NativeModuleLister;
/*     */ import net.minecraft.util.profiling.jfr.Environment;
/*     */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Main {
/*  49 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */   
/*     */   static {
/*  53 */     System.setProperty("java.awt.headless", "true");
/*     */   }
/*     */   @DontObfuscate
/*     */   public static void main(String[] $$0) {
/*     */     Thread $$77;
/*  58 */     Stopwatch $$1 = Stopwatch.createStarted(Ticker.systemTicker());
/*  59 */     Stopwatch $$2 = Stopwatch.createStarted(Ticker.systemTicker());
/*  60 */     GameLoadTimesEvent.INSTANCE.beginStep(TelemetryProperty.LOAD_TIME_TOTAL_TIME_MS, $$1);
/*  61 */     GameLoadTimesEvent.INSTANCE.beginStep(TelemetryProperty.LOAD_TIME_PRE_WINDOW_MS, $$2);
/*     */     
/*  63 */     SharedConstants.tryDetectVersion();
/*  64 */     SharedConstants.enableDataFixerOptimizations();
/*     */     
/*  66 */     OptionParser $$3 = new OptionParser();
/*  67 */     $$3.allowsUnrecognizedOptions();
/*     */     
/*  69 */     $$3.accepts("demo");
/*  70 */     $$3.accepts("disableMultiplayer");
/*  71 */     $$3.accepts("disableChat");
/*  72 */     $$3.accepts("fullscreen");
/*  73 */     $$3.accepts("checkGlErrors");
/*  74 */     OptionSpecBuilder optionSpecBuilder = $$3.accepts("jfrProfile");
/*  75 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec1 = $$3.accepts("quickPlayPath").withRequiredArg();
/*  76 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec2 = $$3.accepts("quickPlaySingleplayer").withRequiredArg();
/*  77 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec3 = $$3.accepts("quickPlayMultiplayer").withRequiredArg();
/*  78 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec4 = $$3.accepts("quickPlayRealms").withRequiredArg();
/*  79 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec5 = $$3.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), (Object[])new File[0]);
/*  80 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec6 = $$3.accepts("assetsDir").withRequiredArg().ofType(File.class);
/*  81 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec7 = $$3.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
/*  82 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec8 = $$3.accepts("proxyHost").withRequiredArg();
/*  83 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec9 = $$3.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (Object[])new String[0]).ofType(Integer.class);
/*  84 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec10 = $$3.accepts("proxyUser").withRequiredArg();
/*  85 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec11 = $$3.accepts("proxyPass").withRequiredArg();
/*  86 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec12 = $$3.accepts("username").withRequiredArg().defaultsTo("Player" + Util.getMillis() % 1000L, (Object[])new String[0]);
/*  87 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec13 = $$3.accepts("uuid").withRequiredArg();
/*  88 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec14 = $$3.accepts("xuid").withOptionalArg().defaultsTo("", (Object[])new String[0]);
/*  89 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec15 = $$3.accepts("clientId").withOptionalArg().defaultsTo("", (Object[])new String[0]);
/*  90 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec16 = $$3.accepts("accessToken").withRequiredArg().required();
/*  91 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec17 = $$3.accepts("version").withRequiredArg().required();
/*  92 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec18 = $$3.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(854), (Object[])new Integer[0]);
/*  93 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec19 = $$3.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(Integer.valueOf(480), (Object[])new Integer[0]);
/*  94 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec20 = $$3.accepts("fullscreenWidth").withRequiredArg().ofType(Integer.class);
/*  95 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec21 = $$3.accepts("fullscreenHeight").withRequiredArg().ofType(Integer.class);
/*  96 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec22 = $$3.accepts("userProperties").withRequiredArg().defaultsTo("{}", (Object[])new String[0]);
/*  97 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec23 = $$3.accepts("profileProperties").withRequiredArg().defaultsTo("{}", (Object[])new String[0]);
/*  98 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec24 = $$3.accepts("assetIndex").withRequiredArg();
/*  99 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec25 = $$3.accepts("userType").withRequiredArg().defaultsTo(User.Type.LEGACY.getName(), (Object[])new String[0]);
/* 100 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec26 = $$3.accepts("versionType").withRequiredArg().defaultsTo("release", (Object[])new String[0]);
/* 101 */     NonOptionArgumentSpec nonOptionArgumentSpec = $$3.nonOptions();
/*     */     
/* 103 */     OptionSet $$32 = $$3.parse($$0);
/* 104 */     List<String> $$33 = $$32.valuesOf((OptionSpec)nonOptionArgumentSpec);
/*     */     
/* 106 */     if (!$$33.isEmpty()) {
/* 107 */       LOGGER.info("Completely ignored arguments: {}", $$33);
/*     */     }
/*     */ 
/*     */     
/* 111 */     String $$34 = parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec8);
/* 112 */     Proxy $$35 = Proxy.NO_PROXY;
/* 113 */     if ($$34 != null) {
/*     */       try {
/* 115 */         $$35 = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress($$34, ((Integer)parseArgument($$32, (OptionSpec<Integer>)argumentAcceptingOptionSpec9)).intValue()));
/* 116 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     final String proxyUser = parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec10);
/* 122 */     final String proxyPass = parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec11);
/* 123 */     if (!$$35.equals(Proxy.NO_PROXY) && stringHasValue($$36) && stringHasValue($$37)) {
/* 124 */       Authenticator.setDefault(new Authenticator()
/*     */           {
/*     */             protected PasswordAuthentication getPasswordAuthentication() {
/* 127 */               return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 133 */     int $$38 = ((Integer)parseArgument($$32, (OptionSpec<Integer>)argumentAcceptingOptionSpec18)).intValue();
/* 134 */     int $$39 = ((Integer)parseArgument($$32, (OptionSpec<Integer>)argumentAcceptingOptionSpec19)).intValue();
/* 135 */     OptionalInt $$40 = ofNullable(parseArgument($$32, (OptionSpec<Integer>)argumentAcceptingOptionSpec20));
/* 136 */     OptionalInt $$41 = ofNullable(parseArgument($$32, (OptionSpec<Integer>)argumentAcceptingOptionSpec21));
/* 137 */     boolean $$42 = $$32.has("fullscreen");
/* 138 */     boolean $$43 = $$32.has("demo");
/* 139 */     boolean $$44 = $$32.has("disableMultiplayer");
/* 140 */     boolean $$45 = $$32.has("disableChat");
/* 141 */     String $$46 = parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec17);
/* 142 */     Gson $$47 = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create();
/* 143 */     PropertyMap $$48 = (PropertyMap)GsonHelper.fromJson($$47, parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec22), PropertyMap.class);
/* 144 */     PropertyMap $$49 = (PropertyMap)GsonHelper.fromJson($$47, parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec23), PropertyMap.class);
/* 145 */     String $$50 = parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec26);
/*     */ 
/*     */     
/* 148 */     File $$51 = parseArgument($$32, (OptionSpec<File>)argumentAcceptingOptionSpec5);
/* 149 */     File $$52 = $$32.has((OptionSpec)argumentAcceptingOptionSpec6) ? parseArgument($$32, (OptionSpec<File>)argumentAcceptingOptionSpec6) : new File($$51, "assets/");
/* 150 */     File $$53 = $$32.has((OptionSpec)argumentAcceptingOptionSpec7) ? parseArgument($$32, (OptionSpec<File>)argumentAcceptingOptionSpec7) : new File($$51, "resourcepacks/");
/* 151 */     UUID $$54 = $$32.has((OptionSpec)argumentAcceptingOptionSpec13) ? UndashedUuid.fromStringLenient((String)argumentAcceptingOptionSpec13.value($$32)) : UUIDUtil.createOfflinePlayerUUID((String)argumentAcceptingOptionSpec12.value($$32));
/* 152 */     String $$55 = $$32.has((OptionSpec)argumentAcceptingOptionSpec24) ? (String)argumentAcceptingOptionSpec24.value($$32) : null;
/* 153 */     String $$56 = (String)$$32.valueOf((OptionSpec)argumentAcceptingOptionSpec14);
/* 154 */     String $$57 = (String)$$32.valueOf((OptionSpec)argumentAcceptingOptionSpec15);
/*     */     
/* 156 */     String $$58 = parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec1);
/* 157 */     String $$59 = unescapeJavaArgument(parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec2));
/* 158 */     String $$60 = unescapeJavaArgument(parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec3));
/* 159 */     String $$61 = unescapeJavaArgument(parseArgument($$32, (OptionSpec<String>)argumentAcceptingOptionSpec4));
/*     */ 
/*     */     
/* 162 */     if ($$32.has((OptionSpec)optionSpecBuilder)) {
/* 163 */       JvmProfiler.INSTANCE.start(Environment.CLIENT);
/*     */     }
/*     */ 
/*     */     
/* 167 */     CrashReport.preload();
/*     */     
/*     */     try {
/* 170 */       Bootstrap.bootStrap();
/* 171 */       GameLoadTimesEvent.INSTANCE.setBootstrapTime(Bootstrap.bootstrapDuration.get());
/*     */       
/* 173 */       Bootstrap.validate();
/* 174 */     } catch (Throwable $$62) {
/* 175 */       CrashReport $$63 = CrashReport.forThrowable($$62, "Bootstrap");
/* 176 */       CrashReportCategory $$64 = $$63.addCategory("Initialization");
/* 177 */       NativeModuleLister.addCrashSection($$64);
/* 178 */       Minecraft.fillReport(null, null, $$46, null, $$63);
/* 179 */       Minecraft.crash(null, $$51, $$63);
/*     */       
/*     */       return;
/*     */     } 
/* 183 */     String $$65 = (String)argumentAcceptingOptionSpec25.value($$32);
/* 184 */     User.Type $$66 = User.Type.byName($$65);
/* 185 */     if ($$66 == null) {
/* 186 */       LOGGER.warn("Unrecognized user type: {}", $$65);
/*     */     }
/*     */     
/* 189 */     User $$67 = new User((String)argumentAcceptingOptionSpec12.value($$32), $$54, (String)argumentAcceptingOptionSpec16.value($$32), emptyStringToEmptyOptional($$56), emptyStringToEmptyOptional($$57), $$66);
/* 190 */     GameConfig $$68 = new GameConfig(new GameConfig.UserData($$67, $$48, $$49, $$35), new DisplayData($$38, $$39, $$40, $$41, $$42), new GameConfig.FolderData($$51, $$53, $$52, $$55), new GameConfig.GameData($$43, $$46, $$50, $$44, $$45), new GameConfig.QuickPlayData($$58, $$59, $$60, $$61));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     Util.startTimerHackThread();
/* 199 */     Thread $$69 = new Thread("Client Shutdown Thread")
/*     */       {
/*     */         public void run() {
/* 202 */           Minecraft $$0 = Minecraft.getInstance();
/* 203 */           if ($$0 == null) {
/*     */             return;
/*     */           }
/*     */           
/* 207 */           IntegratedServer $$1 = $$0.getSingleplayerServer();
/* 208 */           if ($$1 != null) {
/* 209 */             $$1.halt(true);
/*     */           }
/*     */         }
/*     */       };
/* 213 */     $$69.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/* 214 */     Runtime.getRuntime().addShutdownHook($$69);
/*     */     
/* 216 */     Minecraft $$70 = null;
/*     */     
/*     */     try {
/* 219 */       Thread.currentThread().setName("Render thread");
/* 220 */       RenderSystem.initRenderThread();
/*     */       
/* 222 */       RenderSystem.beginInitialization();
/* 223 */       $$70 = new Minecraft($$68);
/* 224 */       RenderSystem.finishInitialization();
/* 225 */     } catch (SilentInitException $$71) {
/* 226 */       Util.shutdownExecutors();
/* 227 */       LOGGER.warn("Failed to create window: ", $$71);
/*     */       return;
/* 229 */     } catch (Throwable $$72) {
/* 230 */       CrashReport $$73 = CrashReport.forThrowable($$72, "Initializing game");
/* 231 */       CrashReportCategory $$74 = $$73.addCategory("Initialization");
/* 232 */       NativeModuleLister.addCrashSection($$74);
/* 233 */       Minecraft.fillReport($$70, null, $$68.game.launchVersion, null, $$73);
/* 234 */       Minecraft.crash($$70, $$68.location.gameDirectory, $$73);
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     final Minecraft minecraft = $$70;
/*     */ 
/*     */     
/* 241 */     if ($$75.renderOnThread()) {
/* 242 */       Thread $$76 = new Thread("Game thread")
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 246 */               RenderSystem.initGameThread(true);
/* 247 */               minecraft.run();
/* 248 */             } catch (Throwable $$0) {
/* 249 */               Main.LOGGER.error("Exception in client thread", $$0);
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/* 254 */       $$76.start();
/*     */ 
/*     */ 
/*     */       
/* 258 */       while ($$75.isRunning());
/*     */     }
/*     */     else {
/*     */       
/* 262 */       $$77 = null;
/*     */       try {
/* 264 */         RenderSystem.initGameThread(false);
/* 265 */         $$75.run();
/* 266 */       } catch (Throwable $$78) {
/* 267 */         LOGGER.error("Unhandled game exception", $$78);
/*     */       } 
/*     */     } 
/*     */     
/* 271 */     BufferUploader.reset();
/*     */     
/*     */     try {
/* 274 */       $$75.stop();
/* 275 */       if ($$77 != null) {
/* 276 */         $$77.join();
/*     */       }
/* 278 */     } catch (InterruptedException $$79) {
/* 279 */       LOGGER.error("Exception during client thread shutdown", $$79);
/*     */     } finally {
/* 281 */       $$75.destroy();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String unescapeJavaArgument(@Nullable String $$0) {
/* 287 */     if ($$0 == null) {
/* 288 */       return null;
/*     */     }
/* 290 */     return StringEscapeUtils.unescapeJava($$0);
/*     */   }
/*     */   
/*     */   private static Optional<String> emptyStringToEmptyOptional(String $$0) {
/* 294 */     return $$0.isEmpty() ? Optional.<String>empty() : Optional.<String>of($$0);
/*     */   }
/*     */   
/*     */   private static OptionalInt ofNullable(@Nullable Integer $$0) {
/* 298 */     return ($$0 != null) ? OptionalInt.of($$0.intValue()) : OptionalInt.empty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static <T> T parseArgument(OptionSet $$0, OptionSpec<T> $$1) {
/*     */     try {
/* 304 */       return (T)$$0.valueOf($$1);
/* 305 */     } catch (Throwable $$2) {
/* 306 */       if ($$1 instanceof ArgumentAcceptingOptionSpec) { ArgumentAcceptingOptionSpec<T> $$3 = (ArgumentAcceptingOptionSpec<T>)$$1;
/* 307 */         List<T> $$4 = $$3.defaultValues();
/* 308 */         if (!$$4.isEmpty()) {
/* 309 */           return $$4.get(0);
/*     */         } }
/*     */       
/* 312 */       throw $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean stringHasValue(@Nullable String $$0) {
/* 317 */     return ($$0 != null && !$$0.isEmpty());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\main\Main.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */