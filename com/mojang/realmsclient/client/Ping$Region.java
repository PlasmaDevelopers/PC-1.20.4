/*    */ package com.mojang.realmsclient.client;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum Region
/*    */ {
/* 61 */   US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
/* 62 */   US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
/* 63 */   US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
/* 64 */   EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
/* 65 */   AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
/* 66 */   AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
/* 67 */   AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
/* 68 */   SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com");
/*    */   
/*    */   Region(String $$0, String $$1) {
/* 71 */     this.name = $$0;
/* 72 */     this.endpoint = $$1;
/*    */   }
/*    */   
/*    */   final String name;
/*    */   final String endpoint;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\Ping$Region.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */