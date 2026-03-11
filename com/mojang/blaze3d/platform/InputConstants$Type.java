/*     */ package com.mojang.blaze3d.platform;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import org.lwjgl.glfw.GLFW;
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
/*     */ public enum Type
/*     */ {
/*     */   MOUSE, SCANCODE, KEYSYM;
/*     */   final BiFunction<Integer, String, Component> displayTextSupplier;
/*     */   final String defaultPrefix;
/*     */   
/*     */   static {
/* 193 */     KEYSYM = new Type("KEYSYM", 0, "key.keyboard", ($$0, $$1) -> {
/*     */           if ("key.keyboard.unknown".equals($$1)) {
/*     */             return (Component)Component.translatable($$1);
/*     */           }
/*     */           String $$2 = GLFW.glfwGetKeyName($$0.intValue(), -1);
/*     */           return ($$2 != null) ? (Component)Component.literal($$2.toUpperCase(Locale.ROOT)) : (Component)Component.translatable($$1);
/*     */         });
/* 200 */     SCANCODE = new Type("SCANCODE", 1, "scancode", ($$0, $$1) -> {
/*     */           String $$2 = GLFW.glfwGetKeyName(-1, $$0.intValue());
/*     */           return ($$2 != null) ? (Component)Component.literal($$2) : (Component)Component.translatable($$1);
/*     */         });
/* 204 */     MOUSE = new Type("MOUSE", 2, "key.mouse", ($$0, $$1) -> Language.getInstance().has($$1) ? (Component)Component.translatable($$1) : (Component)Component.translatable("key.mouse", new Object[] { Integer.valueOf($$0.intValue() + 1) }));
/*     */   }
/*     */   private static void addKey(Type $$0, String $$1, int $$2) {
/* 207 */     InputConstants.Key $$3 = new InputConstants.Key($$1, $$0, $$2);
/* 208 */     $$0.map.put($$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 214 */     addKey(KEYSYM, "key.keyboard.unknown", -1);
/*     */     
/* 216 */     addKey(MOUSE, "key.mouse.left", 0);
/* 217 */     addKey(MOUSE, "key.mouse.right", 1);
/* 218 */     addKey(MOUSE, "key.mouse.middle", 2);
/* 219 */     addKey(MOUSE, "key.mouse.4", 3);
/* 220 */     addKey(MOUSE, "key.mouse.5", 4);
/* 221 */     addKey(MOUSE, "key.mouse.6", 5);
/* 222 */     addKey(MOUSE, "key.mouse.7", 6);
/* 223 */     addKey(MOUSE, "key.mouse.8", 7);
/*     */     
/* 225 */     addKey(KEYSYM, "key.keyboard.0", 48);
/* 226 */     addKey(KEYSYM, "key.keyboard.1", 49);
/* 227 */     addKey(KEYSYM, "key.keyboard.2", 50);
/* 228 */     addKey(KEYSYM, "key.keyboard.3", 51);
/* 229 */     addKey(KEYSYM, "key.keyboard.4", 52);
/* 230 */     addKey(KEYSYM, "key.keyboard.5", 53);
/* 231 */     addKey(KEYSYM, "key.keyboard.6", 54);
/* 232 */     addKey(KEYSYM, "key.keyboard.7", 55);
/* 233 */     addKey(KEYSYM, "key.keyboard.8", 56);
/* 234 */     addKey(KEYSYM, "key.keyboard.9", 57);
/*     */     
/* 236 */     addKey(KEYSYM, "key.keyboard.a", 65);
/* 237 */     addKey(KEYSYM, "key.keyboard.b", 66);
/* 238 */     addKey(KEYSYM, "key.keyboard.c", 67);
/* 239 */     addKey(KEYSYM, "key.keyboard.d", 68);
/* 240 */     addKey(KEYSYM, "key.keyboard.e", 69);
/* 241 */     addKey(KEYSYM, "key.keyboard.f", 70);
/* 242 */     addKey(KEYSYM, "key.keyboard.g", 71);
/* 243 */     addKey(KEYSYM, "key.keyboard.h", 72);
/* 244 */     addKey(KEYSYM, "key.keyboard.i", 73);
/* 245 */     addKey(KEYSYM, "key.keyboard.j", 74);
/* 246 */     addKey(KEYSYM, "key.keyboard.k", 75);
/* 247 */     addKey(KEYSYM, "key.keyboard.l", 76);
/* 248 */     addKey(KEYSYM, "key.keyboard.m", 77);
/* 249 */     addKey(KEYSYM, "key.keyboard.n", 78);
/* 250 */     addKey(KEYSYM, "key.keyboard.o", 79);
/* 251 */     addKey(KEYSYM, "key.keyboard.p", 80);
/* 252 */     addKey(KEYSYM, "key.keyboard.q", 81);
/* 253 */     addKey(KEYSYM, "key.keyboard.r", 82);
/* 254 */     addKey(KEYSYM, "key.keyboard.s", 83);
/* 255 */     addKey(KEYSYM, "key.keyboard.t", 84);
/* 256 */     addKey(KEYSYM, "key.keyboard.u", 85);
/* 257 */     addKey(KEYSYM, "key.keyboard.v", 86);
/* 258 */     addKey(KEYSYM, "key.keyboard.w", 87);
/* 259 */     addKey(KEYSYM, "key.keyboard.x", 88);
/* 260 */     addKey(KEYSYM, "key.keyboard.y", 89);
/* 261 */     addKey(KEYSYM, "key.keyboard.z", 90);
/*     */     
/* 263 */     addKey(KEYSYM, "key.keyboard.f1", 290);
/* 264 */     addKey(KEYSYM, "key.keyboard.f2", 291);
/* 265 */     addKey(KEYSYM, "key.keyboard.f3", 292);
/* 266 */     addKey(KEYSYM, "key.keyboard.f4", 293);
/* 267 */     addKey(KEYSYM, "key.keyboard.f5", 294);
/* 268 */     addKey(KEYSYM, "key.keyboard.f6", 295);
/* 269 */     addKey(KEYSYM, "key.keyboard.f7", 296);
/* 270 */     addKey(KEYSYM, "key.keyboard.f8", 297);
/* 271 */     addKey(KEYSYM, "key.keyboard.f9", 298);
/* 272 */     addKey(KEYSYM, "key.keyboard.f10", 299);
/* 273 */     addKey(KEYSYM, "key.keyboard.f11", 300);
/* 274 */     addKey(KEYSYM, "key.keyboard.f12", 301);
/* 275 */     addKey(KEYSYM, "key.keyboard.f13", 302);
/* 276 */     addKey(KEYSYM, "key.keyboard.f14", 303);
/* 277 */     addKey(KEYSYM, "key.keyboard.f15", 304);
/* 278 */     addKey(KEYSYM, "key.keyboard.f16", 305);
/* 279 */     addKey(KEYSYM, "key.keyboard.f17", 306);
/* 280 */     addKey(KEYSYM, "key.keyboard.f18", 307);
/* 281 */     addKey(KEYSYM, "key.keyboard.f19", 308);
/* 282 */     addKey(KEYSYM, "key.keyboard.f20", 309);
/* 283 */     addKey(KEYSYM, "key.keyboard.f21", 310);
/* 284 */     addKey(KEYSYM, "key.keyboard.f22", 311);
/* 285 */     addKey(KEYSYM, "key.keyboard.f23", 312);
/* 286 */     addKey(KEYSYM, "key.keyboard.f24", 313);
/* 287 */     addKey(KEYSYM, "key.keyboard.f25", 314);
/*     */     
/* 289 */     addKey(KEYSYM, "key.keyboard.num.lock", 282);
/* 290 */     addKey(KEYSYM, "key.keyboard.keypad.0", 320);
/* 291 */     addKey(KEYSYM, "key.keyboard.keypad.1", 321);
/* 292 */     addKey(KEYSYM, "key.keyboard.keypad.2", 322);
/* 293 */     addKey(KEYSYM, "key.keyboard.keypad.3", 323);
/* 294 */     addKey(KEYSYM, "key.keyboard.keypad.4", 324);
/* 295 */     addKey(KEYSYM, "key.keyboard.keypad.5", 325);
/* 296 */     addKey(KEYSYM, "key.keyboard.keypad.6", 326);
/* 297 */     addKey(KEYSYM, "key.keyboard.keypad.7", 327);
/* 298 */     addKey(KEYSYM, "key.keyboard.keypad.8", 328);
/* 299 */     addKey(KEYSYM, "key.keyboard.keypad.9", 329);
/* 300 */     addKey(KEYSYM, "key.keyboard.keypad.add", 334);
/* 301 */     addKey(KEYSYM, "key.keyboard.keypad.decimal", 330);
/* 302 */     addKey(KEYSYM, "key.keyboard.keypad.enter", 335);
/* 303 */     addKey(KEYSYM, "key.keyboard.keypad.equal", 336);
/* 304 */     addKey(KEYSYM, "key.keyboard.keypad.multiply", 332);
/* 305 */     addKey(KEYSYM, "key.keyboard.keypad.divide", 331);
/* 306 */     addKey(KEYSYM, "key.keyboard.keypad.subtract", 333);
/*     */     
/* 308 */     addKey(KEYSYM, "key.keyboard.down", 264);
/* 309 */     addKey(KEYSYM, "key.keyboard.left", 263);
/* 310 */     addKey(KEYSYM, "key.keyboard.right", 262);
/* 311 */     addKey(KEYSYM, "key.keyboard.up", 265);
/*     */     
/* 313 */     addKey(KEYSYM, "key.keyboard.apostrophe", 39);
/* 314 */     addKey(KEYSYM, "key.keyboard.backslash", 92);
/* 315 */     addKey(KEYSYM, "key.keyboard.comma", 44);
/* 316 */     addKey(KEYSYM, "key.keyboard.equal", 61);
/* 317 */     addKey(KEYSYM, "key.keyboard.grave.accent", 96);
/* 318 */     addKey(KEYSYM, "key.keyboard.left.bracket", 91);
/* 319 */     addKey(KEYSYM, "key.keyboard.minus", 45);
/* 320 */     addKey(KEYSYM, "key.keyboard.period", 46);
/* 321 */     addKey(KEYSYM, "key.keyboard.right.bracket", 93);
/* 322 */     addKey(KEYSYM, "key.keyboard.semicolon", 59);
/* 323 */     addKey(KEYSYM, "key.keyboard.slash", 47);
/* 324 */     addKey(KEYSYM, "key.keyboard.space", 32);
/* 325 */     addKey(KEYSYM, "key.keyboard.tab", 258);
/*     */     
/* 327 */     addKey(KEYSYM, "key.keyboard.left.alt", 342);
/* 328 */     addKey(KEYSYM, "key.keyboard.left.control", 341);
/* 329 */     addKey(KEYSYM, "key.keyboard.left.shift", 340);
/* 330 */     addKey(KEYSYM, "key.keyboard.left.win", 343);
/* 331 */     addKey(KEYSYM, "key.keyboard.right.alt", 346);
/* 332 */     addKey(KEYSYM, "key.keyboard.right.control", 345);
/* 333 */     addKey(KEYSYM, "key.keyboard.right.shift", 344);
/* 334 */     addKey(KEYSYM, "key.keyboard.right.win", 347);
/*     */     
/* 336 */     addKey(KEYSYM, "key.keyboard.enter", 257);
/* 337 */     addKey(KEYSYM, "key.keyboard.escape", 256);
/*     */     
/* 339 */     addKey(KEYSYM, "key.keyboard.backspace", 259);
/* 340 */     addKey(KEYSYM, "key.keyboard.delete", 261);
/* 341 */     addKey(KEYSYM, "key.keyboard.end", 269);
/* 342 */     addKey(KEYSYM, "key.keyboard.home", 268);
/* 343 */     addKey(KEYSYM, "key.keyboard.insert", 260);
/* 344 */     addKey(KEYSYM, "key.keyboard.page.down", 267);
/* 345 */     addKey(KEYSYM, "key.keyboard.page.up", 266);
/*     */     
/* 347 */     addKey(KEYSYM, "key.keyboard.caps.lock", 280);
/* 348 */     addKey(KEYSYM, "key.keyboard.pause", 284);
/* 349 */     addKey(KEYSYM, "key.keyboard.scroll.lock", 281);
/*     */     
/* 351 */     addKey(KEYSYM, "key.keyboard.menu", 348);
/* 352 */     addKey(KEYSYM, "key.keyboard.print.screen", 283);
/* 353 */     addKey(KEYSYM, "key.keyboard.world.1", 161);
/* 354 */     addKey(KEYSYM, "key.keyboard.world.2", 162);
/*     */   }
/*     */   
/* 357 */   private final Int2ObjectMap<InputConstants.Key> map = (Int2ObjectMap<InputConstants.Key>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */   
/*     */   private static final String KEY_KEYBOARD_UNKNOWN = "key.keyboard.unknown";
/*     */ 
/*     */   
/*     */   Type(String $$0, BiFunction<Integer, String, Component> $$1) {
/* 364 */     this.defaultPrefix = $$0;
/* 365 */     this.displayTextSupplier = $$1;
/*     */   }
/*     */   
/*     */   public InputConstants.Key getOrCreate(int $$0) {
/* 369 */     return (InputConstants.Key)this.map.computeIfAbsent($$0, $$0 -> {
/*     */           int $$1 = $$0;
/*     */           if (this == MOUSE)
/*     */             $$1++; 
/*     */           String $$2 = this.defaultPrefix + "." + this.defaultPrefix;
/*     */           return new InputConstants.Key($$2, this, $$0);
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\InputConstants$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */