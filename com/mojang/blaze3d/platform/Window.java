/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.main.SilentInitException;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.resources.IoSupplier;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.glfw.Callbacks;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.lwjgl.glfw.GLFWErrorCallback;
/*     */ import org.lwjgl.glfw.GLFWErrorCallbackI;
/*     */ import org.lwjgl.glfw.GLFWImage;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public final class Window implements AutoCloseable {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  32 */   private final GLFWErrorCallback defaultErrorCallback = GLFWErrorCallback.create(this::defaultErrorCallback);
/*     */   
/*     */   private final WindowEventHandler eventHandler;
/*     */   private final ScreenManager screenManager;
/*     */   private final long window;
/*     */   private int windowedX;
/*     */   private int windowedY;
/*     */   private int windowedWidth;
/*     */   private int windowedHeight;
/*     */   private Optional<VideoMode> preferredFullscreenVideoMode;
/*     */   private boolean fullscreen;
/*     */   private boolean actuallyFullscreen;
/*     */   private int x;
/*     */   private int y;
/*     */   private int width;
/*     */   private int height;
/*     */   private int framebufferWidth;
/*     */   private int framebufferHeight;
/*     */   private int guiScaledWidth;
/*     */   private int guiScaledHeight;
/*     */   private double guiScale;
/*  53 */   private String errorSection = "";
/*     */   
/*     */   private boolean dirty;
/*     */   private int framerateLimit;
/*     */   private boolean vsync;
/*     */   
/*     */   public Window(WindowEventHandler $$0, ScreenManager $$1, DisplayData $$2, @Nullable String $$3, String $$4) {
/*  60 */     RenderSystem.assertInInitPhase();
/*  61 */     this.screenManager = $$1;
/*     */     
/*  63 */     setBootErrorCallback();
/*  64 */     setErrorSection("Pre startup");
/*     */     
/*  66 */     this.eventHandler = $$0;
/*     */     
/*  68 */     Optional<VideoMode> $$5 = VideoMode.read($$3);
/*  69 */     if ($$5.isPresent()) {
/*  70 */       this.preferredFullscreenVideoMode = $$5;
/*  71 */     } else if ($$2.fullscreenWidth.isPresent() && $$2.fullscreenHeight.isPresent()) {
/*  72 */       this.preferredFullscreenVideoMode = Optional.of(new VideoMode($$2.fullscreenWidth.getAsInt(), $$2.fullscreenHeight.getAsInt(), 8, 8, 8, 60));
/*     */     } else {
/*  74 */       this.preferredFullscreenVideoMode = Optional.empty();
/*     */     } 
/*  76 */     this.actuallyFullscreen = this.fullscreen = $$2.isFullscreen;
/*     */ 
/*     */     
/*  79 */     Monitor $$6 = $$1.getMonitor(GLFW.glfwGetPrimaryMonitor());
/*     */     
/*  81 */     this.windowedWidth = this.width = ($$2.width > 0) ? $$2.width : 1;
/*  82 */     this.windowedHeight = this.height = ($$2.height > 0) ? $$2.height : 1;
/*     */     
/*  84 */     GLFW.glfwDefaultWindowHints();
/*     */     
/*  86 */     GLFW.glfwWindowHint(139265, 196609);
/*  87 */     GLFW.glfwWindowHint(139275, 221185);
/*  88 */     GLFW.glfwWindowHint(139266, 3);
/*  89 */     GLFW.glfwWindowHint(139267, 2);
/*  90 */     GLFW.glfwWindowHint(139272, 204801);
/*  91 */     GLFW.glfwWindowHint(139270, 1);
/*     */     
/*  93 */     this.window = GLFW.glfwCreateWindow(this.width, this.height, $$4, (this.fullscreen && $$6 != null) ? $$6.getMonitor() : 0L, 0L);
/*     */     
/*  95 */     if ($$6 != null) {
/*  96 */       VideoMode $$7 = $$6.getPreferredVidMode(this.fullscreen ? this.preferredFullscreenVideoMode : Optional.<VideoMode>empty());
/*  97 */       this.windowedX = this.x = $$6.getX() + $$7.getWidth() / 2 - this.width / 2;
/*  98 */       this.windowedY = this.y = $$6.getY() + $$7.getHeight() / 2 - this.height / 2;
/*     */     } else {
/* 100 */       int[] $$8 = new int[1];
/* 101 */       int[] $$9 = new int[1];
/* 102 */       GLFW.glfwGetWindowPos(this.window, $$8, $$9);
/* 103 */       this.windowedX = this.x = $$8[0];
/* 104 */       this.windowedY = this.y = $$9[0];
/*     */     } 
/*     */     
/* 107 */     GLFW.glfwMakeContextCurrent(this.window);
/*     */     
/* 109 */     GL.createCapabilities();
/*     */     
/* 111 */     setMode();
/*     */     
/* 113 */     refreshFramebufferSize();
/*     */     
/* 115 */     GLFW.glfwSetFramebufferSizeCallback(this.window, this::onFramebufferResize);
/* 116 */     GLFW.glfwSetWindowPosCallback(this.window, this::onMove);
/* 117 */     GLFW.glfwSetWindowSizeCallback(this.window, this::onResize);
/* 118 */     GLFW.glfwSetWindowFocusCallback(this.window, this::onFocus);
/* 119 */     GLFW.glfwSetCursorEnterCallback(this.window, this::onEnter);
/*     */   }
/*     */   
/*     */   public int getRefreshRate() {
/* 123 */     RenderSystem.assertOnRenderThread();
/* 124 */     return GLX._getRefreshRate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldClose() {
/* 129 */     return GLX._shouldClose(this);
/*     */   }
/*     */   
/*     */   public static void checkGlfwError(BiConsumer<Integer, String> $$0) {
/* 133 */     RenderSystem.assertInInitPhase();
/* 134 */     MemoryStack $$1 = MemoryStack.stackPush(); 
/* 135 */     try { PointerBuffer $$2 = $$1.mallocPointer(1);
/* 136 */       int $$3 = GLFW.glfwGetError($$2);
/* 137 */       if ($$3 != 0) {
/* 138 */         long $$4 = $$2.get();
/* 139 */         String $$5 = ($$4 == 0L) ? "" : MemoryUtil.memUTF8($$4);
/* 140 */         $$0.accept(Integer.valueOf($$3), $$5);
/*     */       } 
/* 142 */       if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 146 */      } public void setIcon(PackResources $$0, IconSet $$1) throws IOException { List<IoSupplier<InputStream>> $$3; List<ByteBuffer> $$4; RenderSystem.assertInInitPhase();
/*     */     
/* 148 */     int $$2 = GLFW.glfwGetPlatform();
/* 149 */     switch ($$2) { case 393217:
/*     */       case 393220:
/* 151 */         $$3 = $$1.getStandardIcons($$0);
/* 152 */         $$4 = new ArrayList<>($$3.size()); 
/* 153 */         try { MemoryStack $$5 = MemoryStack.stackPush(); 
/* 154 */           try { GLFWImage.Buffer $$6 = GLFWImage.malloc($$3.size(), $$5);
/* 155 */             for (int $$7 = 0; $$7 < $$3.size(); $$7++) {
/* 156 */               NativeImage $$8 = NativeImage.read((InputStream)((IoSupplier)$$3.get($$7)).get()); 
/* 157 */               try { ByteBuffer $$9 = MemoryUtil.memAlloc($$8.getWidth() * $$8.getHeight() * 4);
/* 158 */                 $$4.add($$9);
/* 159 */                 $$9.asIntBuffer().put($$8.getPixelsRGBA());
/*     */                 
/* 161 */                 $$6.position($$7);
/* 162 */                 $$6.width($$8.getWidth());
/* 163 */                 $$6.height($$8.getHeight());
/* 164 */                 $$6.pixels($$9);
/* 165 */                 if ($$8 != null) $$8.close();  } catch (Throwable throwable) { if ($$8 != null)
/*     */                   try { $$8.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */             
/* 168 */             }  GLFW.glfwSetWindowIcon(this.window, (GLFWImage.Buffer)$$6.position(0));
/* 169 */             if ($$5 != null) $$5.close();  } catch (Throwable throwable) { if ($$5 != null)
/* 170 */               try { $$5.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } finally { $$4.forEach(MemoryUtil::memFree); }
/*     */       
/*     */       case 393218:
/* 173 */         MacosUtil.loadIcon($$1.getMacIcon($$0));
/*     */       case 393219:
/*     */       case 393221:
/* 176 */         return; }  LOGGER.warn("Not setting icon for unrecognized platform: {}", Integer.valueOf($$2)); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorSection(String $$0) {
/* 181 */     this.errorSection = $$0;
/*     */   }
/*     */   
/*     */   private void setBootErrorCallback() {
/* 185 */     RenderSystem.assertInInitPhase();
/*     */ 
/*     */     
/* 188 */     GLFW.glfwSetErrorCallback(Window::bootCrash);
/*     */   }
/*     */   
/*     */   private static void bootCrash(int $$0, long $$1) {
/* 192 */     RenderSystem.assertInInitPhase();
/* 193 */     String $$2 = "GLFW error " + $$0 + ": " + MemoryUtil.memUTF8($$1);
/* 194 */     TinyFileDialogs.tinyfd_messageBox("Minecraft", $$2 + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false);
/* 195 */     throw new WindowInitFailed($$2);
/*     */   }
/*     */   
/*     */   public void defaultErrorCallback(int $$0, long $$1) {
/* 199 */     RenderSystem.assertOnRenderThread();
/* 200 */     String $$2 = MemoryUtil.memUTF8($$1);
/* 201 */     LOGGER.error("########## GL ERROR ##########");
/* 202 */     LOGGER.error("@ {}", this.errorSection);
/* 203 */     LOGGER.error("{}: {}", Integer.valueOf($$0), $$2);
/*     */   }
/*     */   
/*     */   public void setDefaultErrorCallback() {
/* 207 */     GLFWErrorCallback $$0 = GLFW.glfwSetErrorCallback((GLFWErrorCallbackI)this.defaultErrorCallback);
/* 208 */     if ($$0 != null) {
/* 209 */       $$0.free();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateVsync(boolean $$0) {
/* 214 */     RenderSystem.assertOnRenderThreadOrInit();
/* 215 */     this.vsync = $$0;
/* 216 */     GLFW.glfwSwapInterval($$0 ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 221 */     RenderSystem.assertOnRenderThread();
/* 222 */     Callbacks.glfwFreeCallbacks(this.window);
/* 223 */     this.defaultErrorCallback.close();
/* 224 */     GLFW.glfwDestroyWindow(this.window);
/* 225 */     GLFW.glfwTerminate();
/*     */   }
/*     */   
/*     */   private void onMove(long $$0, int $$1, int $$2) {
/* 229 */     this.x = $$1;
/* 230 */     this.y = $$2;
/*     */   }
/*     */   
/*     */   private void onFramebufferResize(long $$0, int $$1, int $$2) {
/* 234 */     if ($$0 != this.window) {
/*     */       return;
/*     */     }
/* 237 */     int $$3 = getWidth();
/* 238 */     int $$4 = getHeight();
/*     */     
/* 240 */     if ($$1 == 0 || $$2 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     this.framebufferWidth = $$1;
/* 245 */     this.framebufferHeight = $$2;
/* 246 */     if (getWidth() != $$3 || getHeight() != $$4) {
/* 247 */       this.eventHandler.resizeDisplay();
/*     */     }
/*     */   }
/*     */   
/*     */   private void refreshFramebufferSize() {
/* 252 */     RenderSystem.assertInInitPhase();
/* 253 */     int[] $$0 = new int[1];
/* 254 */     int[] $$1 = new int[1];
/* 255 */     GLFW.glfwGetFramebufferSize(this.window, $$0, $$1);
/*     */     
/* 257 */     this.framebufferWidth = ($$0[0] > 0) ? $$0[0] : 1;
/* 258 */     this.framebufferHeight = ($$1[0] > 0) ? $$1[0] : 1;
/*     */   }
/*     */   
/*     */   private void onResize(long $$0, int $$1, int $$2) {
/* 262 */     this.width = $$1;
/* 263 */     this.height = $$2;
/*     */   }
/*     */   
/*     */   private void onFocus(long $$0, boolean $$1) {
/* 267 */     if ($$0 == this.window) {
/* 268 */       this.eventHandler.setWindowActive($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void onEnter(long $$0, boolean $$1) {
/* 273 */     if ($$1) {
/* 274 */       this.eventHandler.cursorEntered();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setFramerateLimit(int $$0) {
/* 279 */     this.framerateLimit = $$0;
/*     */   }
/*     */   
/*     */   public int getFramerateLimit() {
/* 283 */     return this.framerateLimit;
/*     */   }
/*     */   
/*     */   public void updateDisplay() {
/* 287 */     RenderSystem.flipFrame(this.window);
/* 288 */     if (this.fullscreen != this.actuallyFullscreen) {
/* 289 */       this.actuallyFullscreen = this.fullscreen;
/* 290 */       updateFullscreen(this.vsync);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Optional<VideoMode> getPreferredFullscreenVideoMode() {
/* 295 */     return this.preferredFullscreenVideoMode;
/*     */   }
/*     */   
/*     */   public void setPreferredFullscreenVideoMode(Optional<VideoMode> $$0) {
/* 299 */     boolean $$1 = !$$0.equals(this.preferredFullscreenVideoMode);
/* 300 */     this.preferredFullscreenVideoMode = $$0;
/* 301 */     if ($$1) {
/* 302 */       this.dirty = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void changeFullscreenVideoMode() {
/* 307 */     if (this.fullscreen && this.dirty) {
/* 308 */       this.dirty = false;
/* 309 */       setMode();
/* 310 */       this.eventHandler.resizeDisplay();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setMode() {
/* 315 */     RenderSystem.assertInInitPhase();
/*     */ 
/*     */     
/* 318 */     boolean $$0 = (GLFW.glfwGetWindowMonitor(this.window) != 0L);
/* 319 */     if (this.fullscreen) {
/* 320 */       Monitor $$1 = this.screenManager.findBestMonitor(this);
/* 321 */       if ($$1 == null) {
/* 322 */         LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
/* 323 */         this.fullscreen = false;
/*     */       } else {
/* 325 */         if (Minecraft.ON_OSX)
/*     */         {
/* 327 */           MacosUtil.exitNativeFullscreen(this.window);
/*     */         }
/* 329 */         VideoMode $$2 = $$1.getPreferredVidMode(this.preferredFullscreenVideoMode);
/* 330 */         if (!$$0) {
/* 331 */           this.windowedX = this.x;
/* 332 */           this.windowedY = this.y;
/* 333 */           this.windowedWidth = this.width;
/* 334 */           this.windowedHeight = this.height;
/*     */         } 
/* 336 */         this.x = 0;
/* 337 */         this.y = 0;
/* 338 */         this.width = $$2.getWidth();
/* 339 */         this.height = $$2.getHeight();
/* 340 */         GLFW.glfwSetWindowMonitor(this.window, $$1.getMonitor(), this.x, this.y, this.width, this.height, $$2.getRefreshRate());
/* 341 */         if (Minecraft.ON_OSX)
/*     */         {
/* 343 */           MacosUtil.clearResizableBit(this.window);
/*     */         }
/*     */       } 
/*     */     } else {
/* 347 */       this.x = this.windowedX;
/* 348 */       this.y = this.windowedY;
/* 349 */       this.width = this.windowedWidth;
/* 350 */       this.height = this.windowedHeight;
/* 351 */       GLFW.glfwSetWindowMonitor(this.window, 0L, this.x, this.y, this.width, this.height, -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleFullScreen() {
/* 356 */     this.fullscreen = !this.fullscreen;
/*     */   }
/*     */   
/*     */   public void setWindowed(int $$0, int $$1) {
/* 360 */     this.windowedWidth = $$0;
/* 361 */     this.windowedHeight = $$1;
/* 362 */     this.fullscreen = false;
/* 363 */     setMode();
/*     */   }
/*     */   
/*     */   private void updateFullscreen(boolean $$0) {
/* 367 */     RenderSystem.assertOnRenderThread();
/*     */     try {
/* 369 */       setMode();
/* 370 */       this.eventHandler.resizeDisplay();
/* 371 */       updateVsync($$0);
/* 372 */       updateDisplay();
/* 373 */     } catch (Exception $$1) {
/* 374 */       LOGGER.error("Couldn't toggle fullscreen", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int calculateScale(int $$0, boolean $$1) {
/* 379 */     int $$2 = 1;
/* 380 */     while ($$2 != $$0 && $$2 < this.framebufferWidth && $$2 < this.framebufferHeight && this.framebufferWidth / ($$2 + 1) >= 320 && this.framebufferHeight / ($$2 + 1) >= 240) {
/* 381 */       $$2++;
/*     */     }
/* 383 */     if ($$1 && $$2 % 2 != 0) {
/* 384 */       $$2++;
/*     */     }
/* 386 */     return $$2;
/*     */   }
/*     */   
/*     */   public void setGuiScale(double $$0) {
/* 390 */     this.guiScale = $$0;
/* 391 */     int $$1 = (int)(this.framebufferWidth / $$0);
/* 392 */     this.guiScaledWidth = (this.framebufferWidth / $$0 > $$1) ? ($$1 + 1) : $$1;
/* 393 */     int $$2 = (int)(this.framebufferHeight / $$0);
/* 394 */     this.guiScaledHeight = (this.framebufferHeight / $$0 > $$2) ? ($$2 + 1) : $$2;
/*     */   }
/*     */   
/*     */   public void setTitle(String $$0) {
/* 398 */     GLFW.glfwSetWindowTitle(this.window, $$0);
/*     */   }
/*     */   
/*     */   public long getWindow() {
/* 402 */     return this.window;
/*     */   }
/*     */   
/*     */   public boolean isFullscreen() {
/* 406 */     return this.fullscreen;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 410 */     return this.framebufferWidth;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 414 */     return this.framebufferHeight;
/*     */   }
/*     */   
/*     */   public void setWidth(int $$0) {
/* 418 */     this.framebufferWidth = $$0;
/*     */   }
/*     */   
/*     */   public void setHeight(int $$0) {
/* 422 */     this.framebufferHeight = $$0;
/*     */   }
/*     */   
/*     */   public int getScreenWidth() {
/* 426 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getScreenHeight() {
/* 430 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getGuiScaledWidth() {
/* 434 */     return this.guiScaledWidth;
/*     */   }
/*     */   
/*     */   public int getGuiScaledHeight() {
/* 438 */     return this.guiScaledHeight;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 442 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 446 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getGuiScale() {
/* 450 */     return this.guiScale;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Monitor findBestMonitor() {
/* 455 */     return this.screenManager.findBestMonitor(this);
/*     */   }
/*     */   
/*     */   public void updateRawMouseInput(boolean $$0) {
/* 459 */     InputConstants.updateRawMouseInput(this.window, $$0);
/*     */   }
/*     */   
/*     */   public static class WindowInitFailed extends SilentInitException {
/*     */     WindowInitFailed(String $$0) {
/* 464 */       super($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\Window.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */