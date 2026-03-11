/*     */ package com.mojang.blaze3d.platform;
/*     */ import com.google.common.collect.Maps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.LazyLoadedValue;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import org.lwjgl.glfw.GLFWCharModsCallbackI;
/*     */ import org.lwjgl.glfw.GLFWCursorPosCallbackI;
/*     */ import org.lwjgl.glfw.GLFWDropCallbackI;
/*     */ import org.lwjgl.glfw.GLFWKeyCallbackI;
/*     */ import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
/*     */ import org.lwjgl.glfw.GLFWScrollCallbackI;
/*     */ 
/*     */ public class InputConstants {
/*     */   @Nullable
/*     */   private static final MethodHandle GLFW_RAW_MOUSE_MOTION_SUPPORTED;
/*     */   private static final int GLFW_RAW_MOUSE_MOTION;
/*     */   public static final int KEY_0 = 48;
/*     */   public static final int KEY_1 = 49;
/*     */   public static final int KEY_2 = 50;
/*     */   public static final int KEY_3 = 51;
/*     */   public static final int KEY_4 = 52;
/*     */   
/*     */   static {
/*  33 */     MethodHandles.Lookup $$0 = MethodHandles.lookup();
/*  34 */     MethodType $$1 = MethodType.methodType(boolean.class);
/*     */     
/*  36 */     MethodHandle $$2 = null;
/*  37 */     int $$3 = 0;
/*     */ 
/*     */     
/*  40 */     try { $$2 = $$0.findStatic(GLFW.class, "glfwRawMouseMotionSupported", $$1);
/*  41 */       MethodHandle $$4 = $$0.findStaticGetter(GLFW.class, "GLFW_RAW_MOUSE_MOTION", int.class);
/*  42 */       $$3 = $$4.invokeExact(); }
/*  43 */     catch (NoSuchMethodException|NoSuchFieldException noSuchMethodException) {  }
/*  44 */     catch (Throwable $$5)
/*  45 */     { throw new RuntimeException($$5); }
/*     */ 
/*     */     
/*  48 */     GLFW_RAW_MOUSE_MOTION_SUPPORTED = $$2;
/*  49 */     GLFW_RAW_MOUSE_MOTION = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int KEY_5 = 53;
/*     */   
/*     */   public static final int KEY_6 = 54;
/*     */   
/*     */   public static final int KEY_7 = 55;
/*     */   
/*     */   public static final int KEY_8 = 56;
/*     */   
/*     */   public static final int KEY_9 = 57;
/*     */   
/*     */   public static final int KEY_A = 65;
/*     */   
/*     */   public static final int KEY_B = 66;
/*     */   
/*     */   public static final int KEY_C = 67;
/*     */   
/*     */   public static final int KEY_D = 68;
/*     */   
/*     */   public static final int KEY_E = 69;
/*     */   
/*     */   public static final int KEY_F = 70;
/*     */   
/*     */   public static final int KEY_G = 71;
/*     */   
/*     */   public static final int KEY_H = 72;
/*     */   
/*     */   public static final int KEY_I = 73;
/*     */   
/*     */   public static final int KEY_J = 74;
/*     */   
/*     */   public static final int KEY_K = 75;
/*     */   
/*     */   public static final int KEY_L = 76;
/*     */   
/*     */   public static final int KEY_M = 77;
/*     */   public static final int KEY_N = 78;
/*     */   public static final int KEY_O = 79;
/*     */   public static final int KEY_P = 80;
/*     */   public static final int KEY_Q = 81;
/*     */   public static final int KEY_R = 82;
/*     */   public static final int KEY_S = 83;
/*     */   public static final int KEY_T = 84;
/*     */   public static final int KEY_U = 85;
/*     */   public static final int KEY_V = 86;
/*     */   public static final int KEY_W = 87;
/*     */   public static final int KEY_X = 88;
/*     */   public static final int KEY_Y = 89;
/*     */   public static final int KEY_Z = 90;
/*     */   public static final int KEY_F1 = 290;
/*     */   public static final int KEY_F2 = 291;
/*     */   public static final int KEY_F3 = 292;
/*     */   public static final int KEY_F4 = 293;
/*     */   public static final int KEY_F5 = 294;
/*     */   public static final int KEY_F6 = 295;
/*     */   public static final int KEY_F7 = 296;
/*     */   public static final int KEY_F8 = 297;
/*     */   public static final int KEY_F9 = 298;
/*     */   public static final int KEY_F10 = 299;
/*     */   public static final int KEY_F11 = 300;
/*     */   public static final int KEY_F12 = 301;
/*     */   public static final int KEY_F13 = 302;
/*     */   public static final int KEY_F14 = 303;
/*     */   public static final int KEY_F15 = 304;
/*     */   public static final int KEY_F16 = 305;
/*     */   public static final int KEY_F17 = 306;
/*     */   public static final int KEY_F18 = 307;
/*     */   public static final int KEY_F19 = 308;
/*     */   public static final int KEY_F20 = 309;
/*     */   public static final int KEY_F21 = 310;
/*     */   public static final int KEY_F22 = 311;
/*     */   public static final int KEY_F23 = 312;
/*     */   public static final int KEY_F24 = 313;
/*     */   public static final int KEY_F25 = 314;
/*     */   public static final int KEY_NUMLOCK = 282;
/*     */   public static final int KEY_NUMPAD0 = 320;
/*     */   public static final int KEY_NUMPAD1 = 321;
/*     */   public static final int KEY_NUMPAD2 = 322;
/*     */   public static final int KEY_NUMPAD3 = 323;
/*     */   public static final int KEY_NUMPAD4 = 324;
/*     */   public static final int KEY_NUMPAD5 = 325;
/*     */   public static final int KEY_NUMPAD6 = 326;
/*     */   public static final int KEY_NUMPAD7 = 327;
/*     */   public static final int KEY_NUMPAD8 = 328;
/*     */   public static final int KEY_NUMPAD9 = 329;
/*     */   public static final int KEY_NUMPADCOMMA = 330;
/*     */   public static final int KEY_NUMPADENTER = 335;
/*     */   public static final int KEY_NUMPADEQUALS = 336;
/*     */   public static final int KEY_DOWN = 264;
/*     */   public static final int KEY_LEFT = 263;
/*     */   public static final int KEY_RIGHT = 262;
/*     */   public static final int KEY_UP = 265;
/*     */   public static final int KEY_ADD = 334;
/*     */   public static final int KEY_APOSTROPHE = 39;
/*     */   public static final int KEY_BACKSLASH = 92;
/*     */   public static final int KEY_COMMA = 44;
/*     */   public static final int KEY_EQUALS = 61;
/*     */   public static final int KEY_GRAVE = 96;
/*     */   public static final int KEY_LBRACKET = 91;
/*     */   public static final int KEY_MINUS = 45;
/*     */   public static final int KEY_MULTIPLY = 332;
/*     */   public static final int KEY_PERIOD = 46;
/*     */   public static final int KEY_RBRACKET = 93;
/*     */   public static final int KEY_SEMICOLON = 59;
/*     */   public static final int KEY_SLASH = 47;
/*     */   public static final int KEY_SPACE = 32;
/*     */   public static final int KEY_TAB = 258;
/*     */   public static final int KEY_LALT = 342;
/*     */   public static final int KEY_LCONTROL = 341;
/*     */   public static final int KEY_LSHIFT = 340;
/*     */   public static final int KEY_LWIN = 343;
/*     */   public static final int KEY_RALT = 346;
/*     */   public static final int KEY_RCONTROL = 345;
/*     */   public static final int KEY_RSHIFT = 344;
/*     */   public static final int KEY_RWIN = 347;
/*     */   public static final int KEY_RETURN = 257;
/*     */   public static final int KEY_ESCAPE = 256;
/*     */   public static final int KEY_BACKSPACE = 259;
/*     */   public static final int KEY_DELETE = 261;
/*     */   public static final int KEY_END = 269;
/*     */   public static final int KEY_HOME = 268;
/*     */   public static final int KEY_INSERT = 260;
/*     */   public static final int KEY_PAGEDOWN = 267;
/*     */   public static final int KEY_PAGEUP = 266;
/*     */   public static final int KEY_CAPSLOCK = 280;
/*     */   public static final int KEY_PAUSE = 284;
/*     */   public static final int KEY_SCROLLLOCK = 281;
/*     */   public static final int KEY_PRINTSCREEN = 283;
/*     */   public static final int PRESS = 1;
/*     */   public static final int RELEASE = 0;
/*     */   public static final int REPEAT = 2;
/*     */   public static final int MOUSE_BUTTON_LEFT = 0;
/*     */   public static final int MOUSE_BUTTON_MIDDLE = 2;
/*     */   public static final int MOUSE_BUTTON_RIGHT = 1;
/*     */   public static final int MOD_CONTROL = 2;
/*     */   public static final int CURSOR = 208897;
/*     */   public static final int CURSOR_DISABLED = 212995;
/*     */   public static final int CURSOR_NORMAL = 212993;
/* 190 */   public static final Key UNKNOWN = Type.KEYSYM.getOrCreate(-1);
/*     */   public enum Type { MOUSE, SCANCODE, KEYSYM;
/*     */     static {
/* 193 */       KEYSYM = new Type("KEYSYM", 0, "key.keyboard", ($$0, $$1) -> {
/*     */             if ("key.keyboard.unknown".equals($$1)) {
/*     */               return (Component)Component.translatable($$1);
/*     */             }
/*     */             String $$2 = GLFW.glfwGetKeyName($$0.intValue(), -1);
/*     */             return ($$2 != null) ? (Component)Component.literal($$2.toUpperCase(Locale.ROOT)) : (Component)Component.translatable($$1);
/*     */           });
/* 200 */       SCANCODE = new Type("SCANCODE", 1, "scancode", ($$0, $$1) -> {
/*     */             String $$2 = GLFW.glfwGetKeyName(-1, $$0.intValue());
/*     */             return ($$2 != null) ? (Component)Component.literal($$2) : (Component)Component.translatable($$1);
/*     */           });
/* 204 */       MOUSE = new Type("MOUSE", 2, "key.mouse", ($$0, $$1) -> Language.getInstance().has($$1) ? (Component)Component.translatable($$1) : (Component)Component.translatable("key.mouse", new Object[] { Integer.valueOf($$0.intValue() + 1) }));
/*     */     } final BiFunction<Integer, String, Component> displayTextSupplier; final String defaultPrefix;
/*     */     private static void addKey(Type $$0, String $$1, int $$2) {
/* 207 */       InputConstants.Key $$3 = new InputConstants.Key($$1, $$0, $$2);
/* 208 */       $$0.map.put($$2, $$3);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 214 */       addKey(KEYSYM, "key.keyboard.unknown", -1);
/*     */       
/* 216 */       addKey(MOUSE, "key.mouse.left", 0);
/* 217 */       addKey(MOUSE, "key.mouse.right", 1);
/* 218 */       addKey(MOUSE, "key.mouse.middle", 2);
/* 219 */       addKey(MOUSE, "key.mouse.4", 3);
/* 220 */       addKey(MOUSE, "key.mouse.5", 4);
/* 221 */       addKey(MOUSE, "key.mouse.6", 5);
/* 222 */       addKey(MOUSE, "key.mouse.7", 6);
/* 223 */       addKey(MOUSE, "key.mouse.8", 7);
/*     */       
/* 225 */       addKey(KEYSYM, "key.keyboard.0", 48);
/* 226 */       addKey(KEYSYM, "key.keyboard.1", 49);
/* 227 */       addKey(KEYSYM, "key.keyboard.2", 50);
/* 228 */       addKey(KEYSYM, "key.keyboard.3", 51);
/* 229 */       addKey(KEYSYM, "key.keyboard.4", 52);
/* 230 */       addKey(KEYSYM, "key.keyboard.5", 53);
/* 231 */       addKey(KEYSYM, "key.keyboard.6", 54);
/* 232 */       addKey(KEYSYM, "key.keyboard.7", 55);
/* 233 */       addKey(KEYSYM, "key.keyboard.8", 56);
/* 234 */       addKey(KEYSYM, "key.keyboard.9", 57);
/*     */       
/* 236 */       addKey(KEYSYM, "key.keyboard.a", 65);
/* 237 */       addKey(KEYSYM, "key.keyboard.b", 66);
/* 238 */       addKey(KEYSYM, "key.keyboard.c", 67);
/* 239 */       addKey(KEYSYM, "key.keyboard.d", 68);
/* 240 */       addKey(KEYSYM, "key.keyboard.e", 69);
/* 241 */       addKey(KEYSYM, "key.keyboard.f", 70);
/* 242 */       addKey(KEYSYM, "key.keyboard.g", 71);
/* 243 */       addKey(KEYSYM, "key.keyboard.h", 72);
/* 244 */       addKey(KEYSYM, "key.keyboard.i", 73);
/* 245 */       addKey(KEYSYM, "key.keyboard.j", 74);
/* 246 */       addKey(KEYSYM, "key.keyboard.k", 75);
/* 247 */       addKey(KEYSYM, "key.keyboard.l", 76);
/* 248 */       addKey(KEYSYM, "key.keyboard.m", 77);
/* 249 */       addKey(KEYSYM, "key.keyboard.n", 78);
/* 250 */       addKey(KEYSYM, "key.keyboard.o", 79);
/* 251 */       addKey(KEYSYM, "key.keyboard.p", 80);
/* 252 */       addKey(KEYSYM, "key.keyboard.q", 81);
/* 253 */       addKey(KEYSYM, "key.keyboard.r", 82);
/* 254 */       addKey(KEYSYM, "key.keyboard.s", 83);
/* 255 */       addKey(KEYSYM, "key.keyboard.t", 84);
/* 256 */       addKey(KEYSYM, "key.keyboard.u", 85);
/* 257 */       addKey(KEYSYM, "key.keyboard.v", 86);
/* 258 */       addKey(KEYSYM, "key.keyboard.w", 87);
/* 259 */       addKey(KEYSYM, "key.keyboard.x", 88);
/* 260 */       addKey(KEYSYM, "key.keyboard.y", 89);
/* 261 */       addKey(KEYSYM, "key.keyboard.z", 90);
/*     */       
/* 263 */       addKey(KEYSYM, "key.keyboard.f1", 290);
/* 264 */       addKey(KEYSYM, "key.keyboard.f2", 291);
/* 265 */       addKey(KEYSYM, "key.keyboard.f3", 292);
/* 266 */       addKey(KEYSYM, "key.keyboard.f4", 293);
/* 267 */       addKey(KEYSYM, "key.keyboard.f5", 294);
/* 268 */       addKey(KEYSYM, "key.keyboard.f6", 295);
/* 269 */       addKey(KEYSYM, "key.keyboard.f7", 296);
/* 270 */       addKey(KEYSYM, "key.keyboard.f8", 297);
/* 271 */       addKey(KEYSYM, "key.keyboard.f9", 298);
/* 272 */       addKey(KEYSYM, "key.keyboard.f10", 299);
/* 273 */       addKey(KEYSYM, "key.keyboard.f11", 300);
/* 274 */       addKey(KEYSYM, "key.keyboard.f12", 301);
/* 275 */       addKey(KEYSYM, "key.keyboard.f13", 302);
/* 276 */       addKey(KEYSYM, "key.keyboard.f14", 303);
/* 277 */       addKey(KEYSYM, "key.keyboard.f15", 304);
/* 278 */       addKey(KEYSYM, "key.keyboard.f16", 305);
/* 279 */       addKey(KEYSYM, "key.keyboard.f17", 306);
/* 280 */       addKey(KEYSYM, "key.keyboard.f18", 307);
/* 281 */       addKey(KEYSYM, "key.keyboard.f19", 308);
/* 282 */       addKey(KEYSYM, "key.keyboard.f20", 309);
/* 283 */       addKey(KEYSYM, "key.keyboard.f21", 310);
/* 284 */       addKey(KEYSYM, "key.keyboard.f22", 311);
/* 285 */       addKey(KEYSYM, "key.keyboard.f23", 312);
/* 286 */       addKey(KEYSYM, "key.keyboard.f24", 313);
/* 287 */       addKey(KEYSYM, "key.keyboard.f25", 314);
/*     */       
/* 289 */       addKey(KEYSYM, "key.keyboard.num.lock", 282);
/* 290 */       addKey(KEYSYM, "key.keyboard.keypad.0", 320);
/* 291 */       addKey(KEYSYM, "key.keyboard.keypad.1", 321);
/* 292 */       addKey(KEYSYM, "key.keyboard.keypad.2", 322);
/* 293 */       addKey(KEYSYM, "key.keyboard.keypad.3", 323);
/* 294 */       addKey(KEYSYM, "key.keyboard.keypad.4", 324);
/* 295 */       addKey(KEYSYM, "key.keyboard.keypad.5", 325);
/* 296 */       addKey(KEYSYM, "key.keyboard.keypad.6", 326);
/* 297 */       addKey(KEYSYM, "key.keyboard.keypad.7", 327);
/* 298 */       addKey(KEYSYM, "key.keyboard.keypad.8", 328);
/* 299 */       addKey(KEYSYM, "key.keyboard.keypad.9", 329);
/* 300 */       addKey(KEYSYM, "key.keyboard.keypad.add", 334);
/* 301 */       addKey(KEYSYM, "key.keyboard.keypad.decimal", 330);
/* 302 */       addKey(KEYSYM, "key.keyboard.keypad.enter", 335);
/* 303 */       addKey(KEYSYM, "key.keyboard.keypad.equal", 336);
/* 304 */       addKey(KEYSYM, "key.keyboard.keypad.multiply", 332);
/* 305 */       addKey(KEYSYM, "key.keyboard.keypad.divide", 331);
/* 306 */       addKey(KEYSYM, "key.keyboard.keypad.subtract", 333);
/*     */       
/* 308 */       addKey(KEYSYM, "key.keyboard.down", 264);
/* 309 */       addKey(KEYSYM, "key.keyboard.left", 263);
/* 310 */       addKey(KEYSYM, "key.keyboard.right", 262);
/* 311 */       addKey(KEYSYM, "key.keyboard.up", 265);
/*     */       
/* 313 */       addKey(KEYSYM, "key.keyboard.apostrophe", 39);
/* 314 */       addKey(KEYSYM, "key.keyboard.backslash", 92);
/* 315 */       addKey(KEYSYM, "key.keyboard.comma", 44);
/* 316 */       addKey(KEYSYM, "key.keyboard.equal", 61);
/* 317 */       addKey(KEYSYM, "key.keyboard.grave.accent", 96);
/* 318 */       addKey(KEYSYM, "key.keyboard.left.bracket", 91);
/* 319 */       addKey(KEYSYM, "key.keyboard.minus", 45);
/* 320 */       addKey(KEYSYM, "key.keyboard.period", 46);
/* 321 */       addKey(KEYSYM, "key.keyboard.right.bracket", 93);
/* 322 */       addKey(KEYSYM, "key.keyboard.semicolon", 59);
/* 323 */       addKey(KEYSYM, "key.keyboard.slash", 47);
/* 324 */       addKey(KEYSYM, "key.keyboard.space", 32);
/* 325 */       addKey(KEYSYM, "key.keyboard.tab", 258);
/*     */       
/* 327 */       addKey(KEYSYM, "key.keyboard.left.alt", 342);
/* 328 */       addKey(KEYSYM, "key.keyboard.left.control", 341);
/* 329 */       addKey(KEYSYM, "key.keyboard.left.shift", 340);
/* 330 */       addKey(KEYSYM, "key.keyboard.left.win", 343);
/* 331 */       addKey(KEYSYM, "key.keyboard.right.alt", 346);
/* 332 */       addKey(KEYSYM, "key.keyboard.right.control", 345);
/* 333 */       addKey(KEYSYM, "key.keyboard.right.shift", 344);
/* 334 */       addKey(KEYSYM, "key.keyboard.right.win", 347);
/*     */       
/* 336 */       addKey(KEYSYM, "key.keyboard.enter", 257);
/* 337 */       addKey(KEYSYM, "key.keyboard.escape", 256);
/*     */       
/* 339 */       addKey(KEYSYM, "key.keyboard.backspace", 259);
/* 340 */       addKey(KEYSYM, "key.keyboard.delete", 261);
/* 341 */       addKey(KEYSYM, "key.keyboard.end", 269);
/* 342 */       addKey(KEYSYM, "key.keyboard.home", 268);
/* 343 */       addKey(KEYSYM, "key.keyboard.insert", 260);
/* 344 */       addKey(KEYSYM, "key.keyboard.page.down", 267);
/* 345 */       addKey(KEYSYM, "key.keyboard.page.up", 266);
/*     */       
/* 347 */       addKey(KEYSYM, "key.keyboard.caps.lock", 280);
/* 348 */       addKey(KEYSYM, "key.keyboard.pause", 284);
/* 349 */       addKey(KEYSYM, "key.keyboard.scroll.lock", 281);
/*     */       
/* 351 */       addKey(KEYSYM, "key.keyboard.menu", 348);
/* 352 */       addKey(KEYSYM, "key.keyboard.print.screen", 283);
/* 353 */       addKey(KEYSYM, "key.keyboard.world.1", 161);
/* 354 */       addKey(KEYSYM, "key.keyboard.world.2", 162);
/*     */     }
/*     */     
/* 357 */     private final Int2ObjectMap<InputConstants.Key> map = (Int2ObjectMap<InputConstants.Key>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */     
/*     */     private static final String KEY_KEYBOARD_UNKNOWN = "key.keyboard.unknown";
/*     */ 
/*     */     
/*     */     Type(String $$0, BiFunction<Integer, String, Component> $$1) {
/* 364 */       this.defaultPrefix = $$0;
/* 365 */       this.displayTextSupplier = $$1;
/*     */     }
/*     */     
/*     */     public InputConstants.Key getOrCreate(int $$0) {
/* 369 */       return (InputConstants.Key)this.map.computeIfAbsent($$0, $$0 -> {
/*     */             int $$1 = $$0;
/*     */             if (this == MOUSE) {
/*     */               $$1++;
/*     */             }
/*     */             String $$2 = this.defaultPrefix + "." + this.defaultPrefix;
/*     */             return new InputConstants.Key($$2, this, $$0);
/*     */           });
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Key
/*     */   {
/*     */     private final String name;
/*     */     private final InputConstants.Type type;
/*     */     private final int value;
/*     */     private final LazyLoadedValue<Component> displayName;
/* 387 */     static final Map<String, Key> NAME_MAP = Maps.newHashMap();
/*     */     
/*     */     Key(String $$0, InputConstants.Type $$1, int $$2) {
/* 390 */       this.name = $$0;
/* 391 */       this.type = $$1;
/* 392 */       this.value = $$2;
/*     */       
/* 394 */       this.displayName = new LazyLoadedValue(() -> (Component)$$0.displayTextSupplier.apply(Integer.valueOf($$1), $$2));
/* 395 */       NAME_MAP.put($$0, this);
/*     */     }
/*     */     
/*     */     public InputConstants.Type getType() {
/* 399 */       return this.type;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 403 */       return this.value;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 407 */       return this.name;
/*     */     }
/*     */     
/*     */     public Component getDisplayName() {
/* 411 */       return (Component)this.displayName.get();
/*     */     }
/*     */     
/*     */     public OptionalInt getNumericKeyValue() {
/* 415 */       if (this.value >= 48 && this.value <= 57) {
/* 416 */         return OptionalInt.of(this.value - 48);
/*     */       }
/* 418 */       if (this.value >= 320 && this.value <= 329) {
/* 419 */         return OptionalInt.of(this.value - 320);
/*     */       }
/* 421 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 426 */       if (this == $$0) {
/* 427 */         return true;
/*     */       }
/* 429 */       if ($$0 == null || getClass() != $$0.getClass()) {
/* 430 */         return false;
/*     */       }
/* 432 */       Key $$1 = (Key)$$0;
/* 433 */       return (this.value == $$1.value && this.type == $$1.type);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 438 */       return Objects.hash(new Object[] { this.type, Integer.valueOf(this.value) });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 443 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Key getKey(int $$0, int $$1) {
/* 448 */     if ($$0 == -1) {
/* 449 */       return Type.SCANCODE.getOrCreate($$1);
/*     */     }
/* 451 */     return Type.KEYSYM.getOrCreate($$0);
/*     */   }
/*     */   
/*     */   public static Key getKey(String $$0) {
/* 455 */     if (Key.NAME_MAP.containsKey($$0)) {
/* 456 */       return Key.NAME_MAP.get($$0);
/*     */     }
/*     */     
/* 459 */     for (Type $$1 : Type.values()) {
/* 460 */       if ($$0.startsWith($$1.defaultPrefix)) {
/* 461 */         String $$2 = $$0.substring($$1.defaultPrefix.length() + 1);
/* 462 */         int $$3 = Integer.parseInt($$2);
/* 463 */         if ($$1 == Type.MOUSE) {
/* 464 */           $$3--;
/*     */         }
/* 466 */         return $$1.getOrCreate($$3);
/*     */       } 
/*     */     } 
/* 469 */     throw new IllegalArgumentException("Unknown key name: " + $$0);
/*     */   }
/*     */   
/*     */   public static boolean isKeyDown(long $$0, int $$1) {
/* 473 */     return (GLFW.glfwGetKey($$0, $$1) == 1);
/*     */   }
/*     */   
/*     */   public static void setupKeyboardCallbacks(long $$0, GLFWKeyCallbackI $$1, GLFWCharModsCallbackI $$2) {
/* 477 */     GLFW.glfwSetKeyCallback($$0, $$1);
/* 478 */     GLFW.glfwSetCharModsCallback($$0, $$2);
/*     */   }
/*     */   
/*     */   public static void setupMouseCallbacks(long $$0, GLFWCursorPosCallbackI $$1, GLFWMouseButtonCallbackI $$2, GLFWScrollCallbackI $$3, GLFWDropCallbackI $$4) {
/* 482 */     GLFW.glfwSetCursorPosCallback($$0, $$1);
/* 483 */     GLFW.glfwSetMouseButtonCallback($$0, $$2);
/* 484 */     GLFW.glfwSetScrollCallback($$0, $$3);
/* 485 */     GLFW.glfwSetDropCallback($$0, $$4);
/*     */   }
/*     */   
/*     */   public static void grabOrReleaseMouse(long $$0, int $$1, double $$2, double $$3) {
/* 489 */     GLFW.glfwSetCursorPos($$0, $$2, $$3);
/* 490 */     GLFW.glfwSetInputMode($$0, 208897, $$1);
/*     */   }
/*     */   
/*     */   public static boolean isRawMouseInputSupported() {
/*     */     try {
/* 495 */       return (GLFW_RAW_MOUSE_MOTION_SUPPORTED != null && GLFW_RAW_MOUSE_MOTION_SUPPORTED.invokeExact());
/* 496 */     } catch (Throwable $$0) {
/* 497 */       throw new RuntimeException($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updateRawMouseInput(long $$0, boolean $$1) {
/* 502 */     if (isRawMouseInputSupported())
/* 503 */       GLFW.glfwSetInputMode($$0, GLFW_RAW_MOUSE_MOTION, $$1 ? 1 : 0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\InputConstants.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */