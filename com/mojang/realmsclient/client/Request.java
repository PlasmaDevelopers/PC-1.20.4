/*     */ package com.mojang.realmsclient.client;
/*     */ 
/*     */ import com.mojang.realmsclient.exception.RealmsHttpException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Request<T extends Request<T>>
/*     */ {
/*     */   protected HttpURLConnection connection;
/*     */   private boolean connected;
/*     */   protected String url;
/*     */   private static final int DEFAULT_READ_TIMEOUT = 60000;
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
/*     */   private static final String IS_SNAPSHOT_KEY = "Is-Prerelease";
/*     */   private static final String COOKIE_KEY = "Cookie";
/*     */   
/*     */   public Request(String $$0, int $$1, int $$2) {
/*     */     try {
/*  30 */       this.url = $$0;
/*  31 */       Proxy $$3 = RealmsClientConfig.getProxy();
/*     */       
/*  33 */       if ($$3 != null) {
/*  34 */         this.connection = (HttpURLConnection)(new URL($$0)).openConnection($$3);
/*     */       } else {
/*  36 */         this.connection = (HttpURLConnection)(new URL($$0)).openConnection();
/*     */       } 
/*     */       
/*  39 */       this.connection.setConnectTimeout($$1);
/*  40 */       this.connection.setReadTimeout($$2);
/*  41 */     } catch (MalformedURLException $$4) {
/*  42 */       throw new RealmsHttpException($$4.getMessage(), $$4);
/*  43 */     } catch (IOException $$5) {
/*  44 */       throw new RealmsHttpException($$5.getMessage(), $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cookie(String $$0, String $$1) {
/*  49 */     cookie(this.connection, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static void cookie(HttpURLConnection $$0, String $$1, String $$2) {
/*  53 */     String $$3 = $$0.getRequestProperty("Cookie");
/*  54 */     if ($$3 == null) {
/*  55 */       $$0.setRequestProperty("Cookie", $$1 + "=" + $$1);
/*     */     } else {
/*  57 */       $$0.setRequestProperty("Cookie", $$3 + ";" + $$3 + "=" + $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSnapshotHeader(boolean $$0) {
/*  62 */     this.connection.addRequestProperty("Is-Prerelease", String.valueOf($$0));
/*     */   }
/*     */   
/*     */   public int getRetryAfterHeader() {
/*  66 */     return getRetryAfterHeader(this.connection);
/*     */   }
/*     */   
/*     */   public static int getRetryAfterHeader(HttpURLConnection $$0) {
/*  70 */     String $$1 = $$0.getHeaderField("Retry-After");
/*     */     try {
/*  72 */       return Integer.valueOf($$1).intValue();
/*  73 */     } catch (Exception $$2) {
/*  74 */       return 5;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int responseCode() {
/*     */     try {
/*  80 */       connect();
/*  81 */       return this.connection.getResponseCode();
/*  82 */     } catch (Exception $$0) {
/*  83 */       throw new RealmsHttpException($$0.getMessage(), $$0);
/*     */     } 
/*     */   }
/*     */   public String text() {
/*     */     try {
/*     */       String $$1;
/*  89 */       connect();
/*     */ 
/*     */       
/*  92 */       if (responseCode() >= 400) {
/*  93 */         String $$0 = read(this.connection.getErrorStream());
/*     */       } else {
/*  95 */         $$1 = read(this.connection.getInputStream());
/*     */       } 
/*     */       
/*  98 */       dispose();
/*  99 */       return $$1;
/* 100 */     } catch (IOException $$2) {
/* 101 */       throw new RealmsHttpException($$2.getMessage(), $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String read(@Nullable InputStream $$0) throws IOException {
/* 106 */     if ($$0 == null) {
/* 107 */       return "";
/*     */     }
/* 109 */     InputStreamReader $$1 = new InputStreamReader($$0, StandardCharsets.UTF_8);
/* 110 */     StringBuilder $$2 = new StringBuilder(); int $$3;
/* 111 */     for ($$3 = $$1.read(); $$3 != -1; $$3 = $$1.read()) {
/* 112 */       $$2.append((char)$$3);
/*     */     }
/*     */     
/* 115 */     return $$2.toString();
/*     */   }
/*     */   
/*     */   private void dispose() {
/* 119 */     byte[] $$0 = new byte[1024];
/*     */     try {
/* 121 */       InputStream $$1 = this.connection.getInputStream();
/* 122 */       while ($$1.read($$0) > 0);
/*     */ 
/*     */       
/* 125 */       $$1.close();
/* 126 */     } catch (Exception $$2) {
/*     */       try {
/* 128 */         InputStream $$3 = this.connection.getErrorStream();
/* 129 */         if ($$3 == null) {
/*     */           return;
/*     */         }
/*     */         
/* 133 */         while ($$3.read($$0) > 0);
/*     */ 
/*     */         
/* 136 */         $$3.close();
/* 137 */       } catch (IOException iOException) {}
/*     */     } finally {
/*     */       
/* 140 */       if (this.connection != null) {
/* 141 */         this.connection.disconnect();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected T connect() {
/* 148 */     if (this.connected) {
/* 149 */       return (T)this;
/*     */     }
/* 151 */     T $$0 = doConnect();
/* 152 */     this.connected = true;
/* 153 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract T doConnect();
/*     */   
/*     */   public static Request<?> get(String $$0) {
/* 160 */     return new Get($$0, 5000, 60000);
/*     */   }
/*     */   
/*     */   public static Request<?> get(String $$0, int $$1, int $$2) {
/* 164 */     return new Get($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static Request<?> post(String $$0, String $$1) {
/* 168 */     return new Post($$0, $$1, 5000, 60000);
/*     */   }
/*     */   
/*     */   public static Request<?> post(String $$0, String $$1, int $$2, int $$3) {
/* 172 */     return new Post($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static Request<?> delete(String $$0) {
/* 176 */     return new Delete($$0, 5000, 60000);
/*     */   }
/*     */   
/*     */   public static Request<?> put(String $$0, String $$1) {
/* 180 */     return new Put($$0, $$1, 5000, 60000);
/*     */   }
/*     */   
/*     */   public static Request<?> put(String $$0, String $$1, int $$2, int $$3) {
/* 184 */     return new Put($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public String getHeader(String $$0) {
/* 188 */     return getHeader(this.connection, $$0);
/*     */   }
/*     */   
/*     */   public static String getHeader(HttpURLConnection $$0, String $$1) {
/*     */     try {
/* 193 */       return $$0.getHeaderField($$1);
/* 194 */     } catch (Exception $$2) {
/* 195 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Delete extends Request<Delete> {
/*     */     public Delete(String $$0, int $$1, int $$2) {
/* 201 */       super($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Delete doConnect() {
/*     */       try {
/* 207 */         this.connection.setDoOutput(true);
/* 208 */         this.connection.setRequestMethod("DELETE");
/* 209 */         this.connection.connect();
/* 210 */         return this;
/* 211 */       } catch (Exception $$0) {
/* 212 */         throw new RealmsHttpException($$0.getMessage(), $$0);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Get extends Request<Get> {
/*     */     public Get(String $$0, int $$1, int $$2) {
/* 219 */       super($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Get doConnect() {
/*     */       try {
/* 225 */         this.connection.setDoInput(true);
/* 226 */         this.connection.setDoOutput(true);
/* 227 */         this.connection.setUseCaches(false);
/* 228 */         this.connection.setRequestMethod("GET");
/* 229 */         return this;
/* 230 */       } catch (Exception $$0) {
/* 231 */         throw new RealmsHttpException($$0.getMessage(), $$0);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Put extends Request<Put> {
/*     */     private final String content;
/*     */     
/*     */     public Put(String $$0, String $$1, int $$2, int $$3) {
/* 240 */       super($$0, $$2, $$3);
/* 241 */       this.content = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Put doConnect() {
/*     */       try {
/* 247 */         if (this.content != null) {
/* 248 */           this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/*     */         }
/*     */         
/* 251 */         this.connection.setDoOutput(true);
/* 252 */         this.connection.setDoInput(true);
/* 253 */         this.connection.setRequestMethod("PUT");
/* 254 */         OutputStream $$0 = this.connection.getOutputStream();
/* 255 */         OutputStreamWriter $$1 = new OutputStreamWriter($$0, "UTF-8");
/* 256 */         $$1.write(this.content);
/* 257 */         $$1.close();
/* 258 */         $$0.flush();
/* 259 */         return this;
/* 260 */       } catch (Exception $$2) {
/* 261 */         throw new RealmsHttpException($$2.getMessage(), $$2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Post extends Request<Post> {
/*     */     private final String content;
/*     */     
/*     */     public Post(String $$0, String $$1, int $$2, int $$3) {
/* 270 */       super($$0, $$2, $$3);
/* 271 */       this.content = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Post doConnect() {
/*     */       try {
/* 277 */         if (this.content != null) {
/* 278 */           this.connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/*     */         }
/*     */         
/* 281 */         this.connection.setDoInput(true);
/* 282 */         this.connection.setDoOutput(true);
/* 283 */         this.connection.setUseCaches(false);
/* 284 */         this.connection.setRequestMethod("POST");
/* 285 */         OutputStream $$0 = this.connection.getOutputStream();
/* 286 */         OutputStreamWriter $$1 = new OutputStreamWriter($$0, "UTF-8");
/* 287 */         $$1.write(this.content);
/* 288 */         $$1.close();
/* 289 */         $$0.flush();
/* 290 */         return this;
/* 291 */       } catch (Exception $$2) {
/* 292 */         throw new RealmsHttpException($$2.getMessage(), $$2);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\Request.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */